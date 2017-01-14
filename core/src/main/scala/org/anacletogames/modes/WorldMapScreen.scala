package org.anacletogames.modes

import com.badlogic.gdx.Screen
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.math.GridPoint2
import org.anacletogames.battle.{GameGrid, GameMap}
import org.anacletogames.entities.{
  MutableEntity,
  RectMutableEntity,
  SettlementEntity,
  SingleTileMovingEntity
}
import org.anacletogames.game.skills.SkillSet
import org.anacletogames.game.world.{CharacterProfile, Inhabitant, Settlement}
import org.anacletogames.maps.world.WithWorldMap
import render.{EntityWithAnimation, MaleAnimatedTexture}

import scala.collection.JavaConversions._

/**
  * Created by simone on 05.11.16.
  */
class WorldGrid(gridWidth: Int, gridHeight: Int, tiledMap: TiledMap)
    extends GameMap(gridWidth, gridHeight, tiledMap)

class WorldMapScreen
    extends BaseScreen
    with WithWorldMap
    with MovementControllers {

  lazy val worldGrid = new WorldGrid(mapWidth, mapHeight, tiledMap)

  def createDummySettlement(pos: GridPoint2) = {
    val inhab =
      Inhabitant(CharacterProfile("aaa", 0, Nil, new SkillSet()), Nil, 0)
    val inhab2 =
      Inhabitant(CharacterProfile("aaa", 0, Nil, new SkillSet()), Nil, 3)

    Settlement("abacuc", pos, List(inhab, inhab2), Nil)
  }

  addSettlement(createDummySettlement(new GridPoint2(3, 3)))

  override val inputProcessor = zoom orElse arrowMovMap(64)
  override def renderContent(): Unit = {

    if (isTimeToAct) {
      worldGrid
    }

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
  def initGUI(): Unit = {}

  def addSettlement(s: Settlement) = {
    val settlementEntity = new SettlementEntity(s, worldGrid, this)
    worldGrid.placeEntity(settlementEntity, s.position)
  }

  override def resize(width: Int, height: Int): Unit = {

    stage.getViewport.update(width, height, true)

    guiStage.getViewport.update(width, height, true)
  }

}
