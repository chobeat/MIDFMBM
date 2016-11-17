package org.anacletogames.battle

import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.math.{GridPoint2, Shape2D, Vector2}
import com.sun.net.httpserver.Authenticator.Failure
import org.anacletogames.actions.GameAction.ActionContext
import org.anacletogames.entities.Entity

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer
import scala.util.{Failure, Try}

class GameGrid(x: Int, y: Int) {

  private val positionToEntities = mutable.HashMap[GridPoint2, Seq[Entity]]()
  private val entitiesToPosition = mutable.HashMap[Entity, GridPoint2]()

  def getEntitiesAtPosition(p: GridPoint2): Seq[Entity] =
    positionToEntities.getOrElse(p, Seq())

  private def addToEntityToTileContent(
      entity: Entity,
      tileContent: Seq[Entity]): Either[Seq[Entity], Seq[Entity]] = {
    if (!entity.stackable && tileContent.nonEmpty)
      Left(tileContent)
    else if (isContentAccessible(tileContent))
      Left(tileContent)
    else
      Right(tileContent :+ entity)
  }

  def placeEntity(entity: Entity, position: GridPoint2): Boolean = {

    val content = getEntitiesAtPosition(position)
    val newContent = addToEntityToTileContent(entity, content)

    val alreadyPresent= entitiesToPosition.get(entity)
    if(alreadyPresent.nonEmpty) {
      val currentPosition = alreadyPresent.get
      entitiesToPosition -= entity
      val oldContent = positionToEntities(currentPosition)
      val contentWithEntityRemoved = oldContent.filter(_ == entity)
      positionToEntities.put(currentPosition,contentWithEntityRemoved)
    }

    newContent.fold((entities) => {
      positionToEntities.put(position, entities)
      entities.foreach(x => entitiesToPosition.put(x, position))
      true
    }, (x) => false)

  }

  def isTileAccessible(p: GridPoint2) =
    isContentAccessible(getEntitiesAtPosition(p))

  def isContentAccessible(content: Seq[Entity]) =
    content.exists(e => !e.stackable)

  def getAllEntities: Iterator[Entity] = entitiesToPosition.keysIterator

  def getEntityPosition(e:Entity) = entitiesToPosition.get(e)

  def moveEntity(e:Entity,newPosition:GridPoint2)={
    entitiesToPosition += e -> newPosition


  }
}

class BattleMap(mapWidth: Int, mapHeigth: Int, tiledMap: TiledMap) {

  private val gameGrid = new GameGrid(mapWidth, mapHeigth)

  def isTileAccessible = gameGrid.isTileAccessible _

  def getActionContext: ActionContext =
    ActionContext(this)

  def getAllEntities = gameGrid.getAllEntities

  def addEntity(e: Entity,position:GridPoint2) = gameGrid.placeEntity(e,position)

  def getEntityPosition = gameGrid.getEntityPosition _

  def moveEntity(e:Entity,newPosition:GridPoint2)={
    if(e.canIMoveThere(newPosition))
      gameGrid.placeEntity(e,newPosition)
  }
}
