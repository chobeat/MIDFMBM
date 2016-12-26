package org.anacletogames.battle

import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.math.GridPoint2
import org.anacletogames.actions.GameAction.ActionContext
import org.anacletogames.actions.GridMovement
import org.anacletogames.behaviour.PathFinding
import org.anacletogames.entities.{Entity, WithEntityMovement}
import org.anacletogames.maps.TiledMap2Rich
import org.xguzm.pathfinding.grid.GridCell

import scala.collection.mutable

class BattleMap(val mapWidth: Int, val mapHeigth: Int, tiledMap: TiledMap)
    extends PathFinding {

  val gameGrid = new GameGrid(mapWidth, mapHeigth)
  private lazy val impassableMapTile =
    tiledMap.getImpassableTiles

  def isTileAccessible(p: GridPoint2) = {
    gameGrid.isTileAccessible(p) && !impassableMapTile.isDefinedAt(p)
  }

  def getActionContext: ActionContext =
    ActionContext(this)

  def getAllEntities = gameGrid.getAllEntities

  def addEntity(e: Entity, position: GridPoint2) = {
    val isPlaced = gameGrid.placeEntity(e, position)
    if (isPlaced) {
      e.setPosition(position.x, position.y)
      navGrid.setCell(
        position.x,
        position.y,
        new GridCell(position.x, position.y, isTileAccessible(position)))
    }
  }

  def removeEntity(entity: Entity) = {
    gameGrid.removeEntity(entity)
    entity.getPosition match {
      case Some(pos) =>
        navGrid.setCell(pos.x,
                        pos.y,
                        new GridCell(pos.x, pos.y, isTileAccessible(pos)))
      case None =>
    }
  }

  def getEntityPosition = gameGrid.getEntityPosition _

  def moveEntity(e: Entity with WithEntityMovement, movement: GridMovement) = {
    val newPosition = movement.calculateDestination(e.getPosition.get)
    if (e.canIMoveThere(newPosition)) {
      this.removeEntity(e)
      this.addEntity(e, newPosition)
    }
  }
}
