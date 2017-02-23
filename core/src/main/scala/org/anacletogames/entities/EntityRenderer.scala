package org.anacletogames.entities
import java.util.UUID

import com.badlogic.gdx.graphics.g2d.{Animation, Batch, TextureRegion}
import com.badlogic.gdx.math.GridPoint2
import com.badlogic.gdx.scenes.scene2d.Actor
import org.anacletogames.actions.GridMovement
import org.anacletogames.battle.GameGrid
import org.anacletogames.behaviour.{DoNothingBehaviour, EntityBehaviour}
import org.anacletogames.entities.assets.{
  MaleBaseCharacterTexture,
  MovementAnimatedTexture
}
import render.{EntityAnimation, MovementAnimation, RestAnimation, WithDelta}
import org.anacletogames.maps._

/**
  * Created by simone on 14.01.17.
  */
case class EntityRenderer(renderingContext: WithDelta,
                          entityTextures: MovementAnimatedTexture,
                          animationOpt: Option[EntityAnimation] = None) {

  val animation = animationOpt.getOrElse(
    RestAnimation(entityTextures.upStandingBase, LookingUp, 0))

  def getMovementAnimation(startingPosition: GridPoint2,
                           destination: GridPoint2): MovementAnimation = {
    val gridMovement = startingPosition.gridDst(destination)
    val (animation, orientation) = gridMovement match {
      case GridMovement(0, 1) =>
        (MaleBaseCharacterTexture.upWalkingBase, LookingUp)
      case GridMovement(1, 0) =>
        (MaleBaseCharacterTexture.rightWalkingBase, LookingRight)
      case GridMovement(-1, 0) =>
        (MaleBaseCharacterTexture.leftWalkingBase, LookingLeft)
      case GridMovement(0, -1) =>
        (MaleBaseCharacterTexture.downWalkingBase, LookingDown)

    }
    MovementAnimation(animation,
                      orientation,
                      startingPosition,
                      destination,
                      previousStatetime = 0)
  }

  def defaultAnimation =
    RestAnimation(entityTextures.upStandingBase, LookingUp)

  def draw(batch: Batch, alpha: Float, entity: Entity): EntityRenderer = {
    val newAnimation = animation.draw(batch, this, entity)

    this.copy(animationOpt = Some(newAnimation))
  }

  def getRestingAnimation(direction: EntityOrientation): RestAnimation = {
    direction match {
      case LookingUp => RestAnimation(entityTextures.upStandingBase, LookingUp)
      case LookingLeft =>
        RestAnimation(entityTextures.leftStandingBase, LookingLeft)
      case LookingDown =>
        RestAnimation(entityTextures.downStandingBase, LookingDown)
      case LookingRight =>
        RestAnimation(entityTextures.rightStandingBase, LookingRight)

    }
  }
}
