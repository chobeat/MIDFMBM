package org.anacletogames.battle

import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.math.GridPoint2
import org.anacletogames.actions.GameAction.ActionContext
import org.anacletogames.actions.GridMovement
import org.anacletogames.entities.Entity
import org.anacletogames.maps.TiledMap2Rich
import org.xguzm.pathfinding.grid.finders.AStarGridFinder
import org.xguzm.pathfinding.grid.{GridCell, NavigationGrid}
import scala.collection.JavaConversions._
import scala.collection.mutable

/**
  * Created by simone on 30.11.16.
  */
trait PathFinding { this: BattleMap =>

  var cells: Array[Array[GridCell]] = (for (i <- 0 until this.mapWidth)
    yield
      (for (j <- 0 until this.mapHeigth)
        yield new GridCell(i, j, true)).toArray).toArray

  val navGrid = new NavigationGrid[GridCell](cells, true)
  val pathFinder = new AStarGridFinder(classOf[GridCell])

  def findPath(subject: Entity, destination: GridPoint2): Seq[GridPoint2] = {

    val pos = subject.getPosition.get
    val resultPath =
      pathFinder.findPath(pos.x, pos.y, destination.x, destination.y, navGrid)

    if (resultPath != null)
      resultPath.map(p => new GridPoint2(p.x, p.y)).toList
    else
      List()
  }
}

class GameGrid(x: Int, y: Int) {

  private val positionToEntities = mutable.HashMap[GridPoint2, Seq[Entity]]()
  private val entitiesToPosition = mutable.HashMap[Entity, GridPoint2]()

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
    cachedOccupied.get(p) match {
      case None =>
        val b = isContentAccessible(getEntitiesAtPosition(p))
        cachedOccupied.put(p, b)
        b
      case Some(b) => b
    }

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

  def nextStep(): Unit = {
    cachedOccupied.clear()
  }
}
