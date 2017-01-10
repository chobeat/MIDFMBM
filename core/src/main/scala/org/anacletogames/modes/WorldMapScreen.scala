package org.anacletogames.modes

import com.badlogic.gdx.Screen
import org.anacletogames.battle.GameGrid
import org.anacletogames.entities.{Entity, RectEntity, SingleTileMovingEntity}
import org.anacletogames.maps.world.WithWorldMap
import render.{EntityWithAnimation, MaleAnimatedTexture}

import scala.collection.JavaConversions._

/**
  * Created by simone on 05.11.16.
  */

class WorldGrid(gridWidth:Int,gridHeight:Int) extends GameGrid(gridWidth,gridHeight)

class WorldMapScreen
    extends BaseScreen
    with WithWorldMap

    with MovementControllers{

  lazy val worldGrid = new WorldGrid(mapWidth,mapHeight)
  //val partyLocator: Entity= new RectEntity with SingleTileMovingEntity with EntityWithAnimation with MaleAnimatedTexture{

  //}

  override val inputProcessor = zoom orElse arrowMovMap(64)
  override def renderContent(): Unit = {

    if (isTimeToRender) {

      //to draw actors ordered by position
      val actors =
        stage.getActors.toList.sortBy(_.getY)(Ordering[Float].reverse)
      stage.clear()
      actors.foreach(stage.addActor)

      stage.draw()
    }

  }

  initGUI()
  def initGUI(): Unit = {
  }

  override def resize(width: Int, height: Int): Unit = {

    stage.getViewport.update(width, height, true)

    guiStage.getViewport.update(width, height, true)
  }

}
