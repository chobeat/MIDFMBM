package org.anacletogames.entities

import com.badlogic.gdx.graphics.g2d.{Animation, Batch, TextureRegion}
import com.badlogic.gdx.math.GridPoint2
import com.badlogic.gdx.scenes.scene2d.Actor
import org.anacletogames.actions.GridMovement
import org.anacletogames.battle.GameGrid
import org.anacletogames.behaviour.{DoNothingBehaviour, EntityBehaviour}
import org.anacletogames.entities.assets.{
  EntityTexture,
  MaleBaseCharacterTexture,
  MovementAnimatedTexture
}
import render.{EntityAnimation, MovementAnimation, RestAnimation, WithDelta}

import scala.util.Success

/**
  * Created by simone on 14.01.17.
  */
case class EntityRenderer(animation: EntityAnimation,
                          renderingContext: WithDelta,
                          entityTextures: MovementAnimatedTexture,
                          gameGrid: GameGrid) {
  /* override def moveBy(x: Float, y: Float, entity: Entity): Unit = {
    val startingPosition = entity.getPosition
    val mov = GridMovement(x.toInt, y.toInt)
    entity.gameMap.moveEntity(this, mov)
    val destination = entity.getPosition
    this.animation = (startingPosition, destination) match {
      case (Some(s), Some(d)) =>
        val (animation, orientation) = getMovementAnimation(mov)
        MovementAnimation(animation, orientation, s, d)
      case _ => getRestingAnimation(this.animation.orientation)
    }
  }*/

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

  def defaultAnimation =
    RestAnimation(entityTextures.upStandingBase, LookingUp)

  def draw(batch: Batch, alpha: Float, entity: Entity) = {

    animation.draw(batch, this, entity)
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

case class Entity(position: Option[GridPoint2],
                  speed: Int,
                  gameName: Option[String],
                  stackable: Boolean = false,
                  renderer: EntityRenderer,
                  behaviour: EntityBehaviour = DoNothingBehaviour)
    extends Actor {

  def getGameName: String = gameName.getOrElse("UnnamedEntity")

  def doStep(events: Seq[GameEvent],
             gameGrid: GameGrid): (Entity, Seq[GameEvent]) = {
    val (entity, events) = behaviour.doStep(this, gameGrid)
    val nextBehaviour =
      behaviour.decideNextBehaviour(this, events, gameGrid) match {
        case Success(b) => b
        case _ => DoNothingBehaviour
      }
    val newEntity = entity.copy(behaviour = nextBehaviour)
    (newEntity, events)

  }

  def setBehaviour(b: EntityBehaviour) = this.copy(behaviour = b)

  def canIMoveThere(gameGrid: GameGrid, destination: GridPoint2) =
    gameGrid.isTileAccessible(destination)

  override def draw(batch: Batch, parentAlpha: Float): Unit =
    renderer.draw(batch, parentAlpha, this)
}
