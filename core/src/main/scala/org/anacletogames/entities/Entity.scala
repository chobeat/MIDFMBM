package org.anacletogames.entities

import java.util.UUID

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.math.GridPoint2
import com.badlogic.gdx.scenes.scene2d.Actor
import org.anacletogames.battle.GameGrid
import org.anacletogames.behaviour.{DoNothingBehaviour, EntityBehaviour}
import org.anacletogames.events.{GameEvent, MoveByEvent, MovementEvent}
import render.EntityAnimation

import scala.util.Success

case class Entity(position: Option[GridPoint2],
                  speed: Int,
                  gameName: Option[String],
                  stackable: Boolean = false,
                  var renderer: EntityRenderer,
                  behaviour: EntityBehaviour = DoNothingBehaviour,
                  orientation: EntityOrientation = LookingUp,
                  id: String = UUID.randomUUID().toString)
    extends Actor {

  def getGameName: String = gameName.getOrElse("UnnamedEntity")

  def doStep(events: Seq[GameEvent],
             gameGrid: GameGrid): (Entity, Seq[GameEvent]) = {

    val animatedEntity = this.resolveMovementAnimation(events)

    val newEntity =
      events.foldLeft(animatedEntity)((entity, event) =>
        event.applyToEntity(entity, gameGrid))

    val (nextBehaviour, newEvents) = behaviour.doStep(newEntity, gameGrid)
    val entityWithBehaviour = newEntity.copy(behaviour = nextBehaviour)

    (entityWithBehaviour, newEvents)
  }

  def resolveMovementAnimation(oldEvents: Seq[GameEvent]): Entity = {
    val destinationOpt = oldEvents.collectFirst {

      case MovementEvent(e, d) => d
      case MoveByEvent(e, gridMovement) =>
        gridMovement.calculateDestination(this.position.get)
    }

    destinationOpt match {
      case Some(destination) =>
        val movementAnimation =
          renderer.getMovementAnimation(this.position.get, destination)

        this
          .copy(orientation = orientation)
          .setAnimation(movementAnimation)
      case None => this
    }
  }

  def setBehaviour(b: EntityBehaviour) = this.copy(behaviour = b)

  def canIMoveThere(gameGrid: GameGrid, destination: GridPoint2) =
    gameGrid.isTileAccessibleByEntity(destination, this)

  override def draw(batch: Batch, parentAlpha: Float): Unit = {

    renderer = renderer.draw(batch, parentAlpha, this)
  }

  def setAnimation(animation: EntityAnimation): Entity = {
    val newRenderer = renderer.copy(animationOpt = Some(animation))
    this.copy(renderer = newRenderer)
  }

}
