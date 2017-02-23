package org.anacletogames.modes

import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.math.GridPoint2
import org.anacletogames.battle.GameGrid
import org.anacletogames.entities._
import org.anacletogames.events.GameEvent
import org.anacletogames.game.skills.SkillSet
import org.anacletogames.game.world.{CharacterProfile, Inhabitant, Party, Settlement}
import org.anacletogames.maps.world.WithWorldMap

import scala.collection.JavaConversions._
import scala.collection.mutable
import scala.concurrent._
import org.anacletogames.maps._

/**
  * Created by simone on 05.11.16.
  */
class WorldGrid(gridWidth: Int,
                gridHeight: Int,
                tiledMap: TiledMap,
                settlements: mutable.MutableList[Settlement])
    extends GameGrid(gridWidth,
                     gridHeight,
                     impassableCells = tiledMap.getImpassableTiles.keySet) {

  val simulationManager = new WorldSimulationManager(
    new WorldState(settlements))

  def addEntity(e: Entity, position: GridPoint2) = {}

  def doImmutableStep(movedCountToday: Int) = {

    simulationManager.doImmutableStep(this, movedCountToday)
  }
}



class WorldMapScreen(val party: Party, var worldState: WorldState)
    extends BaseScreen
    with WithWorldMap
    with MovementControllers
    with GameLoopCommons {

  var worldGrid = new GameGrid(mapWidth, mapHeight,impassableCells = Set())
  val userGeneratedEvents= new mutable.Queue[GameEvent]()

  def createDummySettlement(x: Int, pos: GridPoint2) = {
    val inhab = (0 until 10).map(y =>
      Inhabitant(CharacterProfile(s"$x-$y", 0, Nil, new SkillSet()), Nil, 0))

    Settlement("abacuc", pos, inhab.toList, Nil)
  }

  val mlist = mutable.MutableList[Settlement]()
  val settlements =
    (0 until 200)
      .map(x => createDummySettlement(x, new GridPoint2(x, x + 3)))
      .foreach(x => mlist += x)

  val partyEntity = PartyEntity.createParty(new GridPoint2(1,1),this)

   worldGrid.placeEntity(partyEntity)
  stage.addActor(partyEntity)
  override val inputProcessor = zoom orElse arrowMovMap(64) orElse entityControl(
      partyEntity, userGeneratedEvents)

  var lastMovedCount = 0
  override def renderContent(): Unit = {
    updateDelta()
    if (isTimeToAct && !isPaused) {

      val (newWorldGrid, newEvents) = worldGrid.doStep(eventsFromLastFrame)
      worldGrid = newWorldGrid
      eventsFromLastFrame = newEvents ++ userGeneratedEvents
      accumulatedRender = 0
    }

    if (isTimeToRender) {
      accumulatedRender += 1
      //to draw actors ordered by position
      val actorsToPosition =
      worldGrid.getReversedIndex
      stage.clear()
      val actors =
        actorsToPosition.toList
          .sortBy(_._2.y)(Ordering[Int])
          .reverse

      actors.map(_._1).foreach(stage.addActor)

      stage.draw()
    }

  }


  initGUI()
  def initGUI(): Unit = {}

  def addSettlement(s: Settlement) = {
    val settlementEntity = new SettlementEntity(s, worldGrid, this)

    worldGrid.placeEntity(settlementEntity)
  }

  def initWorldMap() = {}

  override def resize(width: Int, height: Int): Unit = {

    stage.getViewport.update(width, height, true)

    guiStage.getViewport.update(width, height, true)
  }

}

object WorldMapScreen {
  def debugWorldMapScreen = {

    new WorldMapScreen(Party("test party", Seq()),
                       new WorldState(mutable.MutableList()))
  }

}
