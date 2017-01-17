package org.anacletogames.entities

import com.badlogic.gdx.graphics.g2d.{Animation, TextureRegion}
import com.badlogic.gdx.math.GridPoint2
import org.anacletogames.actions.GridMovement
import org.anacletogames.entities.assets.{
  MaleBaseCharacterTexture
}
import render.{EntityWithAnimation, MovementAnimation, RestAnimation}

/**
  * Created by simone on 04.12.16.
  */
trait WithEntityMovement { this: MutableEntity with EntityWithAnimation =>
  override def moveBy(x: Float, y: Float): Unit = {
    val startingPosition = getPosition
    val mov = GridMovement(x.toInt, y.toInt)
    this.gameMap.moveEntity(this, mov)
    val destination = getPosition
    this.animation = (startingPosition, destination) match {
      case (Some(s), Some(d)) =>
        val (animation, orientation) = getMovementAnimation(mov)
        MovementAnimation(animation, orientation, s, d)
      case _ => getRestingAnimation(this.animation.orientation)
    }
  }

  def getMovementAnimation(gridMovement: GridMovement)
    : (Animation[TextureRegion], EntityOrientation) = {
    gridMovement match {
      case GridMovement(0, 1) =>
        (MaleBaseCharacterTexture.upWalkingBase, LookingUp)
      case GridMovement(1, 0) =>
        (MaleBaseCharacterTexture.rightWalkingBase, LookingRight)
      case GridMovement(-1, 0) =>
        (MaleBaseCharacterTexture.leftWalkingBase, LookingLeft)
      case GridMovement(0, -1) =>
        (MaleBaseCharacterTexture.downWalkingBase, LookingDown)

    }
  }

  def canIMoveThere(destination: GridPoint2): Boolean
}
