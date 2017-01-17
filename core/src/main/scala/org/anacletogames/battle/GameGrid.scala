package org.anacletogames.battle

import com.badlogic.gdx.math.GridPoint2
import org.anacletogames.entities.{Entity, ImmutableEntity, MutableEntity}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.collection.mutable
import scala.concurrent.Future
class GameGrid(gridWidth: Int, gridHeight: Int) {

  private var positionToEntities =
    mutable.HashMap[GridPoint2, Seq[Entity]]()

  private var entitiesToPosition = mutable.HashMap[Entity, GridPoint2]()

  private val cachedOccupied = mutable.HashMap[GridPoint2, Boolean]()

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

  def isInsideGrid(position: GridPoint2) =
    position.x >= 0 && position.x <= gridWidth && position.y >= 0 && position.y <= gridHeight

  protected def placeEntity(entity: Entity, position: GridPoint2): Boolean = {
    if (!isInsideGrid(position))
      false
    else {
      val content = getEntitiesAtPosition(position)
      val newContent = addEntityToTileContent(entity, content)

      newContent.fold((x) => false, (entities) => {
        positionToEntities.put(position, entities)
        entities.foreach(x => entitiesToPosition.put(x, position))
        true
      })
    }

  }

  protected def removeEntity(entity: Entity) = {
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
    isInsideGrid(p) && isContentAccessible(getEntitiesAtPosition(p))

  }
  def isContentAccessible(content: Seq[Entity]) =
    !content.exists(e => e.stackable)

  def getAllEntities: Iterator[Entity] = entitiesToPosition.keysIterator

  def getEntityPosition(e: Entity) = entitiesToPosition.get(e)

  def doStep(): Unit = {

    entitiesToPosition.foreach {
      case (entity: MutableEntity, _) => entity.act()
      case _ =>
    }

    cachedOccupied.clear()
  }
  def doImmutableStep(): Future[Unit] = {
    Future {
      entitiesToPosition.map {
        case (entity: ImmutableEntity, pos) => entity.doStep() -> pos
        case x => x
      }
    }.map(x => {
      entitiesToPosition = x

      //recalculate the inverse index
      alignPositionToEntities()
    })
  }

  private def alignPositionToEntities() = {

    entitiesToPosition.toList
      .groupBy(_._2)
      .foreach(x => {

        positionToEntities.put(x._1, x._2.map(_._1))
      })

  }

}
