package org.anacletogames.modes

import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.math.GridPoint2
import org.anacletogames.battle.BattleMap
import org.anacletogames.behaviour.ReachPointBehaviour
import org.anacletogames.entities.{
  DoNothingByDefault,
  RectEntity,
  WithEntityMovement,
  WithStackable
}
import org.anacletogames.gui.{BattleMapGUIControl, WithBattleMapGUI}
import org.anacletogames.maps.MapGenerator
import render.EntityWithAnimation

import scala.collection.JavaConversions._

/**
  * Created by simone on 05.11.16.
  */
class BattleMapRenderer
    extends BaseRenderer
    with MovementControllers
    with WithBattleMapGUI
    with BattleMapGUIControl {
  val mapWidth = 32
  val mapHeight = 32
  lazy val battleMap: BattleMap = new BattleMap(mapWidth, mapHeight, tiledMap)
  override val inputProcessor = zoom orElse arrowMovMap(64) orElse battleMapGUIKeyprocessor

  override def renderContent(): Unit = {

    if (isTimeToAct && !isPaused) {
      accumulatedRender = 0
      battleMap.getAllEntities.foreach(_.act())
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
    val myChar = new RectEntity(1, battleMap, Some("Entity " + x), this)
    with WithStackable with EntityWithAnimation with WithEntityMovement
    with DoNothingByDefault
    myChar.setBehaviour(ReachPointBehaviour(myChar, new GridPoint2(3, 22)))
    battleMap
      .addEntity(myChar, new GridPoint2(x, x))
    stage.addActor(myChar)
  }

  override def create(): Unit = {

    super.create()
    initGUI()

    (0 until 5).foreach(x => createDummy(x))
  }
  def initGUI(): Unit = {
    guiStage.addActor(battleMapGUIBar)
  }

  override def resize(width: Int, height: Int): Unit = {

    stage.getViewport.update(width, height, true)

    guiStage.getViewport.update(width, height, true)
  }

  override val mapGenerator: (Int, Int) => TiledMap =
    MapGenerator.generateDebugMap
}
