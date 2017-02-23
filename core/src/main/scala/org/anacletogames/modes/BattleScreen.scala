package org.anacletogames.modes

import com.badlogic.gdx.math.GridPoint2
import org.anacletogames.battle.GameGrid
import org.anacletogames.behaviour.ReachPointBehaviour
import org.anacletogames.entities.assets.MaleBaseCharacterTexture
import org.anacletogames.entities._
import org.anacletogames.gui.{BattleMapGUIControl, WithBattleMapGUI}
import org.anacletogames.maps.MapGenerator
import render.{MaleAnimatedTexture, RestAnimation}
import org.anacletogames.maps._
import scala.collection.JavaConversions._

/**
  * Created by simone on 05.11.16.
  */
class BattleScreen(mapWidth: Int = 32, mapHeight: Int = 32)
    extends BaseScreen
    with MovementControllers
    with WithBattleMapGUI
    with BattleMapGUIControl
    with GameLoopCommons {
  override lazy val tiledMap =
    MapGenerator.generateDebugMap(mapWidth, mapHeight)
  var gameGrid: GameGrid =
    GameGrid.empty(mapWidth, mapHeight, tiledMap.getImpassableTiles.keySet)
  override val inputProcessor = zoom orElse arrowMovMap(64) orElse battleMapGUIKeyprocessor

  override def renderContent(): Unit = {
    updateDelta()
    if (isTimeToAct && !isPaused) {

      val (newGameGrid, newEvents) = gameGrid.doStep(eventsFromLastFrame)
      gameGrid = newGameGrid
      eventsFromLastFrame = newEvents
      accumulatedRender = 0
    }

    if (isTimeToRender) {
      accumulatedRender += 1
      //to draw actors ordered by position
      val actorsToPosition =
        gameGrid.getReversedIndex
      stage.clear()
      val actors =
        actorsToPosition.toList
          .sortBy(_._2.y)(Ordering[Int])
          .reverse

      actors.map(_._1).foreach(stage.addActor)

      stage.draw()
    }

  }

  def createDummy(x: Int) = {
    val renderer = EntityRenderer(
      this,
      MaleBaseCharacterTexture
    )
    val myChar =
      Entity(Some(new GridPoint2(x, x)),
             1,
             Some("Entity " + x),
             false,
             renderer,
             ReachPointBehaviour(new GridPoint2(5, 5), None),id = x.toString)
    gameGrid = gameGrid.placeEntity(myChar).get
    stage.addActor(myChar)
  }

  initGUI()

  (2 until 5).foreach(x => createDummy(x))

  def initGUI(): Unit = {
    guiStage.addActor(battleMapGUIBar)
  }

  override def resize(width: Int, height: Int): Unit = {

    stage.getViewport.update(width, height, true)

    guiStage.getViewport.update(width, height, true)
  }

}
