package org.anacletogames.modes

import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.math.GridPoint2
import org.anacletogames.battle.BattleMap
import org.anacletogames.behaviour.ReachPointBehaviour
import org.anacletogames.entities.{DoNothingByDefault, RectMutableEntity, WithEntityMovement, WithStackable}
import org.anacletogames.gui.{BattleMapGUIControl, WithBattleMapGUI}
import org.anacletogames.maps.MapGenerator
import render.{EntityWithAnimation, MaleAnimatedTexture}

import scala.concurrent.duration._
import scala.collection.JavaConversions._
import scala.concurrent.{Await, Future}

/**
  * Created by simone on 05.11.16.
  */
class BattleScreen(mapWidth: Int = 32, mapHeight: Int = 32)
    extends BaseScreen
    with MovementControllers
    with WithBattleMapGUI
    with BattleMapGUIControl {
  override lazy val tiledMap =
    MapGenerator.generateDebugMap(mapWidth, mapHeight)
  lazy val battleMap: BattleMap = new BattleMap(mapWidth, mapHeight, tiledMap)
  override val inputProcessor = zoom orElse arrowMovMap(64) orElse battleMapGUIKeyprocessor

  override def renderContent(): Unit = {

    if (isTimeToAct && !isPaused) {
      accumulatedRender = 0
      battleMap.doStep()
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

  def createDummy(x: Int) = {
    val myChar = new RectMutableEntity(1, battleMap, Some("Entity " + x), this)
    with MaleAnimatedTexture with WithStackable with EntityWithAnimation
    with WithEntityMovement with DoNothingByDefault
    myChar.setBehaviour(ReachPointBehaviour(myChar, new GridPoint2(3, 22)))
    battleMap
      .addEntity(myChar, new GridPoint2(x, x))
    stage.addActor(myChar)
  }

    initGUI()

    (0 until 5).foreach(x => createDummy(x))

  def initGUI(): Unit = {
    guiStage.addActor(battleMapGUIBar)
  }

  override def resize(width: Int, height: Int): Unit = {

    stage.getViewport.update(width, height, true)

    guiStage.getViewport.update(width, height, true)
  }

}
