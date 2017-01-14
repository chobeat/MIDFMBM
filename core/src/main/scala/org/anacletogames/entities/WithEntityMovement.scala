package org.anacletogames.entities

import com.badlogic.gdx.math.GridPoint2
import org.anacletogames.actions.GridMovement
import org.anacletogames.entities.assets.MaleBaseCharacterTexture
import render.{EntityWithAnimation, MovementAnimation}

/**
  * Created by simone on 04.12.16.
  */
trait WithEntityMovement { this: MutableEntity with EntityWithAnimation=>
  override def moveBy(x: Float, y: Float): Unit = {
    val startingPosition=getPosition
    val mov=GridMovement(x.toInt, y.toInt)
    this.gameMap.moveEntity(this, mov)
    val destination=getPosition
    this.animation=MovementAnimation(getMovementAnimation(mov),startingPosition.get,destination.get)
  }


  def getMovementAnimation(gridMovement: GridMovement) = {
    gridMovement match {
      case GridMovement(0, 1) => MaleBaseCharacterTexture.upWalkingBase
      case GridMovement(1, 0) => MaleBaseCharacterTexture.rightWalkingBase
      case GridMovement(-1, 0) => MaleBaseCharacterTexture.leftWalkingBase
      case GridMovement(0, -1) => MaleBaseCharacterTexture.downWalkingBase

    }
  }

  def canIMoveThere(destination: GridPoint2): Boolean
}
