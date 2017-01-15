package org.anacletogames.modes

import com.badlogic.gdx.Screen
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.math.GridPoint2
import org.anacletogames.battle.{GameGrid, GameMap}
import org.anacletogames.entities._
import org.anacletogames.game.skills.SkillSet
import org.anacletogames.game.world.{
  CharacterProfile,
  Inhabitant,
  Party,
  Settlement
}
import org.anacletogames.maps.world.WithWorldMap
import render.{EntityWithAnimation, MaleAnimatedTexture}

import scala.concurrent.duration._
import scala.collection.JavaConversions._
import scala.concurrent._
import ExecutionContext.Implicits.global

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
  var worldGridResultFuture: Option[Future[Unit]] = None
  def createDummySettlement(pos: GridPoint2) = {
    val inhab = (0 to 1000).map(x =>
      Inhabitant(CharacterProfile("aaa", 0, Nil, new SkillSet()), Nil, 0))

    Settlement("abacuc", pos, inhab.toList, Nil)
  }

  /*(0 to 10).foreach(x =>
    addSettlement(createDummySettlement(new GridPoint2(x, x + 3))))
*/
  val party = Party("test party", Seq())
  val partyEntity = new PartyEntity(party, this.worldGrid, this)

  worldGrid.addEntity(partyEntity,new GridPoint2(1,1))
  stage.addActor(partyEntity)
  override val inputProcessor = zoom orElse arrowMovMap(64) orElse entityControl(
      partyEntity)

  override def renderContent(): Unit = {

    worldGridResultFuture = worldGridResultFuture match {
      case Some(f) if f.isCompleted && isTimeToAct =>
        Some(worldGrid.doStep())
      case None => Some(worldGrid.doStep())
      case x => x
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

  override def resize(width: Int, height: Int): Unit = {

    stage.getViewport.update(width, height, true)

    guiStage.getViewport.update(width, height, true)
  }

}
