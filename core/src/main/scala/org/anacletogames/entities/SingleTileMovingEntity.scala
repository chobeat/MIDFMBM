package org.anacletogames.entities

import com.badlogic.gdx.math.GridPoint2
import org.anacletogames.battle.BattleMap
import render.{EntityWithAnimation, WithDelta}

/**
  * Created by simone on 12.11.16.
  */
trait SingleTileMovingEntity extends WithEntityMovement {
  this: MutableEntity with EntityWithAnimation =>
  override def canIMoveThere(destination: GridPoint2): Boolean = {
    gameMap.isTileAccessible(destination)
  }

}
