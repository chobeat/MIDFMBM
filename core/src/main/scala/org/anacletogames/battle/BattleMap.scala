package org.anacletogames.battle

import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.math.GridPoint2
import org.anacletogames.actions.GameAction.ActionContext
import org.anacletogames.actions.GridMovement
import org.anacletogames.behaviour.PathFinding
import org.anacletogames.entities.{Entity, MutableEntity, WithEntityMovement}
import org.anacletogames.maps.TiledMap2Rich
import org.xguzm.pathfinding.grid.GridCell

import scala.collection.mutable

class BattleMap(mapWidth: Int, mapHeigth: Int, tiledMap: TiledMap)
    extends GameMap(mapWidth, mapHeigth, tiledMap)

class GameMap(val mapWidth: Int, val mapHeigth: Int, tiledMap: TiledMap)
    extends GameGrid(mapWidth, mapHeigth)
    with PathFinding {

  private lazy val impassableMapTile =
    tiledMap.getImpassableTiles

  override def isTileAccessible(p: GridPoint2) = {
    super.isTileAccessible(p) && !impassableMapTile.isDefinedAt(p)
  }

  def addEntity(e: Entity, position: GridPoint2) = {
    val isPlaced = this.placeEntity(e, position)
    if (isPlaced) {
      //Setting the act
      e.setPosition(position.x, position.y)

      navGrid.setCell(
        position.x,
        position.y,
        new GridCell(position.x, position.y, isTileAccessible(position)))
    }
  }

  override def removeEntity(entity: Entity) = {
    super.removeEntity(entity)
    entity.getPosition match {
      case Some(pos) =>
        navGrid.setCell(pos.x,
                        pos.y,
                        new GridCell(pos.x, pos.y, isTileAccessible(pos)))
      case None =>
    }
  }

  def moveEntity(e: MutableEntity with WithEntityMovement, movement: GridMovement) = {
    val newPosition = movement.calculateDestination(e.getPosition.get)
    if (e.canIMoveThere(newPosition)) {
      this.removeEntity(e)
      this.addEntity(e, newPosition)
    }
  }

}
