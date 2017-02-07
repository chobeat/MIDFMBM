package org.anacletogames.behaviour

import com.badlogic.gdx.math.GridPoint2
import org.anacletogames.battle.GameGrid
import org.anacletogames.entities.Entity
import org.xguzm.pathfinding.grid.finders.{AStarGridFinder, GridFinderOptions}
import org.xguzm.pathfinding.grid.{GridCell, NavigationGrid}

import scala.collection.JavaConversions._

/**
  * Created by simone on 30.11.16.
  */
trait PathFinding { this: GameGrid =>

  lazy val navGrid = {

    val cells: Array[Array[GridCell]] = (for (i <- 0 until this.gridWidth)
      yield
        (for (j <- 0 until this.gridHeight)
          yield
            new GridCell(i, j, isTileAccessible(new GridPoint2(i, j)))).toArray).toArray

    new NavigationGrid[GridCell](cells, true)
  }
  val gridFinderOptions = new GridFinderOptions()
  gridFinderOptions.allowDiagonal = false
  gridFinderOptions.dontCrossCorners = true
  val pathFinder = new AStarGridFinder(classOf[GridCell], gridFinderOptions)

  def findPath(subject: Entity, destination: GridPoint2): Seq[GridPoint2] = {

    val pos = subject.position.get
    navGrid.setCell(pos.x,pos.y,new GridCell(pos.x,pos.y,true))
    val resultPath =
      pathFinder.findPath(pos.x, pos.y, destination.x, destination.y, navGrid)
    val gridWithFreeStartingPosition= navGrid.setCell(pos.x,pos.y,new GridCell(pos.x,pos.y,isTileAccessible(pos)))
    if (resultPath != null)
      resultPath.map(p => new GridPoint2(p.x, p.y)).toList
    else
      List()
  }
}
