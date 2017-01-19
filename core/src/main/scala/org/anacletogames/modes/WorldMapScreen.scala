package org.anacletogames.modes

import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.math.GridPoint2
import org.anacletogames.battle.GameMap
import org.anacletogames.entities._
import org.anacletogames.game.skills.SkillSet
import org.anacletogames.game.world.{
  CharacterProfile,
  Inhabitant,
  Party,
  Settlement
}
import org.anacletogames.maps.world.WithWorldMap

import scala.collection.JavaConversions._
import scala.collection.mutable
import scala.concurrent._

/**
  * Created by simone on 05.11.16.
  */
class WorldGrid(gridWidth: Int,
                gridHeight: Int,
                tiledMap: TiledMap,
                settlements: mutable.MutableList[Settlement])
    extends GameMap(gridWidth, gridHeight, tiledMap) {

  val simulationManager = new WorldSimulationManager(
    new WorldState(settlements))

  override def addEntity(e: Entity, position: GridPoint2): Unit = {
    e match {
      case e: MutableEntity => super.addEntity(e, position)
      case e: ImmutableEntity => simulationManager.addEntity(e)
    }
  }

  def doImmutableStep(movedCountToday: Int) = {

    simulationManager.doImmutableStep(this, movedCountToday)
  }
}

class WorldMapScreen(val party: Party, var worldState: WorldState)
    extends BaseScreen
    with WithWorldMap
    with MovementControllers {

  lazy val worldGrid = new WorldGrid(mapWidth, mapHeight, tiledMap, mlist)
  def createDummySettlement(x: Int, pos: GridPoint2) = {
    val inhab = (0 until 5000).map(y =>
      Inhabitant(CharacterProfile(s"$x-$y", 0, Nil, new SkillSet()), Nil, 0))

    Settlement("abacuc", pos, inhab.toList, Nil)
  }

  val mlist = mutable.MutableList[Settlement]()
  val settlements =
    (0 until 200)
      .map(x => createDummySettlement(x, new GridPoint2(x, x + 3)))
      .foreach(x => mlist += x)

  val partyEntity = new PartyEntity(party, this.worldGrid, this)

  worldGrid.addEntity(partyEntity, new GridPoint2(1, 1))
  stage.addActor(partyEntity)
  override val inputProcessor = zoom orElse arrowMovMap(64) orElse entityControl(
      partyEntity)

  var lastMovedCount = 0

  override def renderContent(): Unit = {

    if (isTimeToAct && partyEntity.isMovingAnimationCompleted()) {
      worldGrid.doStep()
    }

    if (partyEntity.hasEverMoved && lastMovedCount != partyEntity.getMovedCountToday) {
      worldGrid.doImmutableStep(partyEntity.getMovedCountToday)
      lastMovedCount = partyEntity.getMovedCountToday
    }

    if (isTimeToRender) {

      accumulatedRender += 1
      //to draw actors ordered by position
      val actors =
        stage.getActors.toList.sortBy(_.getY)(Ordering[Float].reverse)
      stage.clear()
      actors.foreach(stage.addActor)

      stage.draw()
    }

  }

  initGUI()
  def initGUI(): Unit = {}

  def addSettlement(s: Settlement) = {
    val settlementEntity = new SettlementEntity(s, worldGrid, this)

    worldGrid.addEntity(settlementEntity, s.position)
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
