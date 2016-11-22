package org.anacletogames.battle

import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.math.GridPoint2
import org.anacletogames.actions.GameAction.ActionContext
import org.anacletogames.actions.GridMovement
import org.anacletogames.entities.Entity
import org.anacletogames.maps.TiledMap2Rich

import scala.collection.mutable

class GameGrid(x: Int, y: Int) {

  private val positionToEntities = mutable.HashMap[GridPoint2, Seq[Entity]]()
  private val entitiesToPosition = mutable.HashMap[Entity, GridPoint2]()

  def getEntitiesAtPosition(p: GridPoint2): Seq[Entity] =
    positionToEntities.getOrElse(p, Seq())

  private def addEntityToTileContent(
      entity: Entity,
      tileContent: Seq[Entity]): Either[Seq[Entity], Seq[Entity]] = {
    if (!entity.stackable && tileContent.nonEmpty)
      Left(tileContent)
    else if (!isContentAccessible(tileContent))
      Left(tileContent)
    else
      Right(tileContent :+ entity)
  }

  def placeEntity(entity: Entity, position: GridPoint2): Boolean = {

    val content = getEntitiesAtPosition(position)
    val newContent = addEntityToTileContent(entity, content)

    newContent.fold((x) => false, (entities) => {
      positionToEntities.put(position, entities)
      entities.foreach(x => entitiesToPosition.put(x, position))
      true
    })

  }

  def removeEntity(entity: Entity) = {
    entitiesToPosition
      .get(entity)
      .foreach(currentPosition => {
        entitiesToPosition -= entity
        val oldContent = positionToEntities(currentPosition)
        val contentWithEntityRemoved = oldContent.filter(_ != entity)
        positionToEntities.put(currentPosition, contentWithEntityRemoved)
      })
  }

  def isTileAccessible(p: GridPoint2) = {
    isContentAccessible(getEntitiesAtPosition(p))

  }
  def isContentAccessible(content: Seq[Entity]) =
    !content.exists(e => e.stackable)

  def getAllEntities: Iterator[Entity] = entitiesToPosition.keysIterator

  def updateEntitiesForDraw() = {
    entitiesToPosition.foreach {
      case (e: Entity, point) => e.setPosition(point.x, point.y)
    }
  }

  def getEntityPosition(e: Entity) = entitiesToPosition.get(e)

  def moveEntity(e: Entity, newPosition: GridPoint2) = {
    entitiesToPosition += e -> newPosition

  }
}

class BattleMap(mapWidth: Int, mapHeigth: Int, tiledMap: TiledMap) {

  def updateForDraw() = {
    gameGrid.updateEntitiesForDraw()
  }

  private val gameGrid = new GameGrid(mapWidth, mapHeigth)
  private lazy val impassableMapTile=
    tiledMap.getImpassableTiles

  def isTileAccessible(p:GridPoint2) = {
     gameGrid.isTileAccessible(p) && !impassableMapTile.isDefinedAt(p)
    }

  def getActionContext: ActionContext =
    ActionContext(this)

  def getAllEntities = gameGrid.getAllEntities

  def addEntity(e: Entity, position: GridPoint2) =
    gameGrid.placeEntity(e, position)

  def getEntityPosition = gameGrid.getEntityPosition _

  def moveEntity(e: Entity, movement: GridMovement) = {
    val newPosition = movement.calculateDestination(e.getPosition.get)
    if (e.canIMoveThere(newPosition))
      gameGrid.removeEntity(e)
    gameGrid.placeEntity(e, newPosition)
  }
}
