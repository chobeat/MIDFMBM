package org.anacletogames.battle

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell
import com.badlogic.gdx.math.GridPoint2
import org.anacletogames.actions.GridMovement
import org.anacletogames.behaviour.PathFinding
import org.anacletogames.entities.{Entity, GameEvent, MovementEvent}

import scala.collection.{Map, breakOut, mutable}
case class GameGrid(gridWidth: Int,
                    gridHeight: Int,
                    positionToEntities: Map[GridPoint2, Seq[Entity]] = Map(),
                    impassableCells: Set[GridPoint2])
    extends PathFinding {

  def getEntitiesAtPosition(p: GridPoint2): Seq[Entity] =
    positionToEntities.getOrElse(p, Seq())

  private def addEntityToTileContent(entity: Entity,
                                     tileContent: Seq[Entity]): Seq[Entity] = {
    if (!entity.stackable && tileContent.nonEmpty)
      tileContent
    else if (!isContentAccessible(tileContent))
      tileContent
    else
      tileContent :+ entity
  }

  def isInsideGrid(position: GridPoint2) =
    position.x >= 0 && position.x <= gridWidth && position.y >= 0 && position.y <= gridHeight

  def placeEntity(entity: Entity, position: GridPoint2): Option[GameGrid] = {
    if (!isInsideGrid(position))
      None
    else {
      val content = getEntitiesAtPosition(position)
      val positionedEntity = entity.copy(position = Some(position))
      val newContent = addEntityToTileContent(positionedEntity, content)
      val result = positionToEntities + (position -> newContent)
      Some(this.copy(positionToEntities = result))
    }

  }

  def removeEntity(entityToBeRemoved: Entity) = {
    val newMap = positionToEntities.flatMap {
      case (pos, entities) =>
        val updatedContent = entities.filter(x => x.id != entityToBeRemoved.id)
        if (updatedContent.nonEmpty) Some(pos -> updatedContent) else None
      case _ => None
    }
    this.copy(positionToEntities = newMap)
  }

  def isTileAccessible(p: GridPoint2) = {
    isInsideGrid(p) && isContentAccessible(getEntitiesAtPosition(p)) && !impassableCells.contains(p)
  }
  def isContentAccessible(content: Seq[Entity]) =
    !content.exists(e => e.stackable)

  def getAllEntities: Seq[Entity] = positionToEntities.values.flatten.toList

  def getReversedIndex: Map[Entity, GridPoint2] = {

    for {
      (position, entities) <- positionToEntities
      entity <- entities
    } yield entity -> position

  }

  def doStep(events: Seq[GameEvent]): (GameGrid, Seq[GameEvent]) = {
    val entitiesToEvents = events.groupBy(_.target)

    val newEntitiesWithEventsAndPositions: Map[Entity,
                                               (GridPoint2, Seq[GameEvent])] =
      (for {
        (pos, entities) <- positionToEntities
        entity <- entities
        (newEntity, events) = entity.doStep(
          entitiesToEvents.getOrElse(entity, Seq()),
          this)
      } yield newEntity -> (pos, events))(breakOut)
    val newEvents = newEntitiesWithEventsAndPositions.flatMap(_._2._2).toList
    val newPosToEntities: Map[Entity, GridPoint2] =
      newEntitiesWithEventsAndPositions.map {
        case (e, (p, _)) => e -> p
      }
    val gridWithUpdatedEntities = this.updateEntities(newPosToEntities)

    val movementEvents = newEvents.flatMap { case x: MovementEvent => Some(x) }
    val newGrid =
      movementEvents.foldLeft(gridWithUpdatedEntities)((grid, event) =>
        grid.moveEntity(event.target, event.destination))

    (newGrid, newEvents.diff(movementEvents))
  }

  def moveEntity(e: Entity, movement: GridMovement): GameGrid = {
    val newPosition = movement.calculateDestination(e.position.get)
    if (e.canIMoveThere(this, newPosition)) {
      val afterRemove = this.removeEntity(e)
      val placed = afterRemove.placeEntity(e, newPosition)
      placed match {
        case Some(g) => g
        case None => this
      }
    } else
      this
  }

  def updateEntities(entitiesToPos: Map[Entity, GridPoint2]): GameGrid = {
    val posToEntities =
      entitiesToPos.toList.groupBy(_._2).map {
        case (k, v) => k -> v.map(_._1)
      }
    this.copy(positionToEntities = posToEntities)
  }
}

object GameGrid {
  def empty(width: Int, height: Int, impassableCells:Set[GridPoint2]) =
    GameGrid(width, height,impassableCells = impassableCells)
}
