package org.anacletogames

import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.math.{Intersector, Shape2D}
import org.anacletogames.actions.GameAction.ActionContext
import org.anacletogames.entities.{Entity, RectEntity}
import org.anacletogames.maps._

import scala.collection.mutable.ArrayBuffer
class BattleMap(sizeX: Int, sizeY: Int, tiledMap: TiledMap) {

  private val entities = scala.collection.mutable.ArrayBuffer[Entity]()
  private val impassableTiles:ArrayBuffer[Shape2D]= scala.collection.mutable.ArrayBuffer[Shape2D]()

  def addEntity(entity:Entity):BattleMap={entities += entity;this}

  def addCollidableShapes(s:Iterable[Shape2D])=impassableTiles.insertAll(0,s)

  def getCollidableShapes=impassableTiles++entities.map(_.stageBounds)

  lazy val getActionContext:ActionContext=ActionContext(entities,getCollidableShapes)

}
