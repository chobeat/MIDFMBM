package org.anacletogames.entities

import com.badlogic.gdx.math.GridPoint2
import render.EntityWithAnimation

/**
  * Created by simone on 12.11.16.
  */
trait SingleTileMovingEntity extends WithEntityMovement {
  this: MutableEntity with EntityWithAnimation =>
  override def canIMoveThere(destination: GridPoint2): Boolean = {
    gameMap.isTileAccessible(destination)
  }

}
