package org.anacletogames.battle

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell
import com.badlogic.gdx.math.GridPoint2
import org.anacletogames.actions.GridMovement
import org.anacletogames.behaviour.PathFinding
import org.anacletogames.entities.{Entity, GameEvent, MovementEvent}

import scala.collection.{Map, breakOut}
case class GameGrid(gridWidth: Int,
                    gridHeight: Int,
                    positionToEntities: Map[GridPoint2, Seq[Entity]] = Map(),
                    impassableCells: Set[GridPoint2])
    extends PathFinding {

  def getEntitiesAtPosition(p: GridPoint2): Seq[Entity] =
    positionToEntities.getOrElse(p, Seq())

  private def addEntityToTileContent(
      entity: Entity,
      tileContent: Seq[Entity]): Option[Seq[Entity]] = {
    if (!entity.stackable && tileContent.nonEmpty)
      None
    else if (!isContentAccessible(tileContent))
      None
    else
      Some(tileContent :+ entity)
  }

  def isInsideGrid(position: GridPoint2) =
    position.x >= 0 && position.x <= gridWidth && position.y >= 0 && position.y <= gridHeight

  def placeEntity(entity: Entity): Option[GameGrid] = {
    val position = entity.position.get
    if (!isInsideGrid(position))
      None
    else {
      val content = getEntitiesAtPosition(position)
      val positionedEntity = entity.copy(position = Some(position))
      val newContentOpt = addEntityToTileContent(positionedEntity, content)
      newContentOpt.map(content => {
        val result = positionToEntities + (position -> content)
        this.copy(positionToEntities = result)
      })
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
    val content=getEntitiesAtPosition(p)
    val res=isInsideGrid(p) && isContentAccessible(content) && !impassableCells
      .contains(p)
    res
  }

  def isTileAccessibleByEntity(p:GridPoint2,entity: Entity):Boolean= {
    val content=getEntitiesAtPosition(p)
    val contentWithoutEntity=content.filter(_.id!=entity.id)
    val canEnter= !(!entity.stackable && contentWithoutEntity.nonEmpty)
    isInsideGrid(p) && canEnter && !impassableCells.contains(p)

  }

  def isContentAccessible(content: Seq[Entity]) = {
    val nonStackableExist=content.exists(e => !e.stackable)
    val res= !nonStackableExist || content.isEmpty
    res

  }

  def getAllEntities: Seq[Entity] = positionToEntities.values.flatten.toList

  def getReversedIndex: Map[Entity, GridPoint2] = {

    for {
      (position, entities) <- positionToEntities
      entity <- entities
    } yield entity -> position

  }

  def doStep(events: Seq[GameEvent]): (GameGrid, Seq[GameEvent]) = {
    val entitiesToEvents = events.groupBy(_.targetId)

    val entities = positionToEntities.values.flatten
    val entitiesWithEvents = entities.map(entity =>
      entity -> entitiesToEvents.getOrElse(entity.id, Seq()))

    entitiesWithEvents.foldLeft((this, List.empty[GameEvent])) {
      case ((grid, accumulatedEvents), (entity, eventList)) =>
        val (newEntity, newEvents) = entity.doStep(eventList, grid)
        val updatedGridOpt=grid.updateEntityPosition(newEntity)
        val newGrid=updatedGridOpt match{
          case Some(updatedGrid)=>updatedGrid
          case None=> grid
        }

        (newGrid, accumulatedEvents ++ newEvents)

    }

  }

  def moveEntity(e: Entity, movement: GridMovement): GameGrid = {
    val newPosition = movement.calculateDestination(e.position.get)
    if (e.canIMoveThere(this, newPosition)) {
      val afterRemove = this.removeEntity(e)
      val placed = afterRemove.placeEntity(e)
      placed match {
        case Some(g) => g
        case None => this
      }
    } else
      this
  }

  def updateEntitiesPosition(entities: Iterable[Entity]): GameGrid = {
    val newPosToEntities: Map[Entity, GridPoint2] =
      entities.map(entity => entity -> entity.position.get)(breakOut)

    val posToEntities =
      newPosToEntities.toList.groupBy(_._2).map {
        case (k, v) => k -> v.map(_._1)
      }
    this.copy(positionToEntities = posToEntities)
  }

  def updateEntityPosition(entity: Entity): Option[GameGrid] = {

    this.removeEntity(entity).placeEntity(entity)
  }
}

object GameGrid {
  def empty(width: Int, height: Int, impassableCells: Set[GridPoint2]) =
    GameGrid(width, height, impassableCells = impassableCells)
}
