package org.anacletogames.modes

import com.badlogic.gdx.graphics.FPSLogger
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.math.GridPoint2
import com.badlogic.gdx.utils.viewport.FitViewport
import org.anacletogames.battle.BattleMap
import org.anacletogames.behaviour.ReachPointBehaviour
import org.anacletogames.entities.{
  DoNothingByDefault,
  RectEntity,
  WithEntityMovement,
  WithStackable
}
import org.anacletogames.gui.{BattleMapDebugMenu, BattleMapGUIControl}
import org.anacletogames.maps.MapGenerator
import render.{Constants, EntityWithAnimation, WithDelta}

import scala.collection.JavaConversions._

/**
  * Created by simone on 05.11.16.
  */
class BattleMapRenderer
    extends BaseRenderer
    with MovementControllers
    with BattleMapDebugMenu
    with BattleMapGUIControl
    with WithDelta {
  val mapWidth = 100
  val mapHeight = 100
  lazy val battleMap: BattleMap = new BattleMap(mapWidth, mapHeight, tiledMap)
  var isPaused = false
  override val inputProcessor = zoom orElse arrowMovMap(64) orElse battleMapGUIKeyprocessor

  override def render(): Unit = {

    updateDelta()
    if (isTimeToAct && !isPaused) {
      accumulatedRender = 0
      battleMap.getAllEntities.foreach(_.act())
    }

    if (isTimeToRender) {
      accumulatedRender += 1

      super.render()
      //to draw actors ordered by position
      val actors =
        stage.getActors.toList.sortBy(_.getY)(Ordering[Float].reverse)
      stage.clear()
      actors.foreach(stage.addActor)

      stage.draw()
      guiStage.draw()
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

    (0 until 25).foreach(x => createDummy(x))
  }
  override def resize(width: Int, height: Int): Unit = {

    stage.getViewport().update(width, height, true)

    guiStage.getViewport().update(width, height, true)
  }

  override val mapGenerator: (Int, Int) => TiledMap =
    MapGenerator.generateDebugMap
}
