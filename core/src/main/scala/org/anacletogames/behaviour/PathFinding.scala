package org.anacletogames.behaviour

import com.badlogic.gdx.math.GridPoint2
import org.anacletogames.battle.GameMap
import org.anacletogames.entities.MutableEntity
import org.xguzm.pathfinding.grid.finders.{AStarGridFinder, GridFinderOptions}
import org.xguzm.pathfinding.grid.{GridCell, NavigationGrid}

import scala.collection.JavaConversions._

/**
  * Created by simone on 30.11.16.
  */
trait PathFinding { this: GameMap =>

  lazy val navGrid = {

    val cells: Array[Array[GridCell]] = (for (i <- 0 until this.mapWidth)
      yield
        (for (j <- 0 until this.mapHeigth)
          yield new GridCell(i, j, isTileAccessible(new GridPoint2(i,j)))).toArray).toArray

    new NavigationGrid[GridCell](cells, true)
  }
  val gridFinderOptions = new GridFinderOptions()
  gridFinderOptions.allowDiagonal = false
  gridFinderOptions.dontCrossCorners = true
  val pathFinder = new AStarGridFinder(classOf[GridCell], gridFinderOptions)

  def findPath(subject: MutableEntity, destination: GridPoint2): Seq[GridPoint2] = {

    val pos = subject.getPosition.get
    val resultPath =
      pathFinder.findPath(pos.x, pos.y, destination.x, destination.y, navGrid)

    if (resultPath != null)
      resultPath.map(p => new GridPoint2(p.x, p.y)).toList
    else
      List()
  }
}
