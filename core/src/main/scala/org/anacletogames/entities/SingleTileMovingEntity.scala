package org.anacletogames.entities

import com.badlogic.gdx.math.GridPoint2
import org.anacletogames.battle.BattleMap
import render.{EntityWithAnimation, WithDelta}

/**
  * Created by simone on 12.11.16.
  */
trait SingleTileMovingEntity extends WithEntityMovement {
  this: Entity with EntityWithAnimation =>
  override def canIMoveThere(destination: GridPoint2): Boolean = {
    stackable && gameMap.isTileAccessible(destination)
  }

}
