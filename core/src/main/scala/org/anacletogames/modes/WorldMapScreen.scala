package org.anacletogames.modes

import akka.actor.{ActorRef, ActorSystem, Props}
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.math.GridPoint2
import org.anacletogames.actors.{AddSettlement, UpdateCompleted, UpdateSimulation, WorldSimulationActor}
import org.anacletogames.battle.GameGrid
import org.anacletogames.entities._
import org.anacletogames.events.GameEvent
import org.anacletogames.game.skills.SkillSet
import org.anacletogames.game.world.{CharacterProfile, Inhabitant, Party, Settlement}
import org.anacletogames.maps.world.WithWorldMap
import akka.pattern._
import akka.util.Timeout
import scala.collection.mutable
import scala.concurrent._
import org.anacletogames.maps._
import scala.concurrent.duration._

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


  def addEntity(e: Entity, position: GridPoint2) = {}

  def doImmutableStep(movedCountToday: Int) = ???

  override def equals(that: Any) = ???
}

class WorldMapScreen(val party: Party)(implicit actorSystem:ActorSystem)
    extends BaseScreen
    with WithWorldMap
    with MovementControllers
    with GameLoopCommons {
  implicit val timeout = Timeout(1 second)
  val worldSimulation = actorSystem.actorOf(Props[WorldSimulationActor])

  var worldGrid = new GameGrid(mapWidth, mapHeight, impassableCells = Set())
  val userGeneratedEvents = new mutable.Queue[GameEvent]()
  (0 to 500).foreach {i=>
    worldSimulation ! AddSettlement(s"test$i")
  }



  val partyEntity = PartyEntity.createParty(new GridPoint2(1, 1), this)

  worldGrid=worldGrid.placeEntity(partyEntity).get
  stage.addActor(partyEntity)
  override val inputProcessor = zoom orElse arrowMovMap(64) orElse entityControl(
      partyEntity,
      userGeneratedEvents)

  var lastMovedCount = 0
  override def renderContent(): Unit = {
    updateDelta()
    if (isTimeToAct && !isPaused) {
      val x = ask(worldSimulation,UpdateSimulation(worldGrid,0)).mapTo[UpdateCompleted.type]
      val res=Await.result(x,Duration(1000,"seconds"))

      val (newWorldGrid, newEvents) = worldGrid.doStep(eventsFromLastFrame)
      worldGrid = newWorldGrid

      val userGeneratedEvent= if (userGeneratedEvents.nonEmpty) Seq(userGeneratedEvents.dequeue()) else Seq()
      eventsFromLastFrame =   userGeneratedEvent ++ newEvents
      accumulatedRender = 0
    }

    if (isTimeToRender) {

      accumulatedRender += 1
      //to draw actors ordered by position
      val actorsToPosition = worldGrid.getReversedIndex
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


  def initWorldMap() = {}

  override def resize(width: Int, height: Int): Unit = {

    stage.getViewport.update(width, height, true)

    guiStage.getViewport.update(width, height, true)
  }

}

object WorldMapScreen {

  def debugWorldMapScreen()(implicit actorSystem:ActorSystem) = {
    new WorldMapScreen(Party("test party", Seq()))
  }

}
