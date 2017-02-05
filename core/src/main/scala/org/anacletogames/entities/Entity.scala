package org.anacletogames.entities

import java.util.UUID

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.math.GridPoint2
import com.badlogic.gdx.scenes.scene2d.Actor
import org.anacletogames.battle.GameGrid
import org.anacletogames.behaviour.{DoNothingBehaviour, EntityBehaviour}
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
        event.applyToEntity(entity))

    val (nextBehaviour, newEvents) = behaviour.doStep(newEntity, gameGrid)
    val entityWithBehaviour = newEntity.copy(behaviour = nextBehaviour)

    (entityWithBehaviour, newEvents)
  }

  def resolveMovementAnimation(oldEvents: Seq[GameEvent]): Entity = {
    val movementOpt = oldEvents.collectFirst {
      case x: MovementEvent => x
    }
    movementOpt match {
      case Some(MovementEvent(entity, destination)) =>

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
    gameGrid.isTileAccessible(destination)

  override def draw(batch: Batch, parentAlpha: Float): Unit = {

    renderer = renderer.draw(batch, parentAlpha, this)
  }

  def setAnimation(animation: EntityAnimation): Entity = {
    val newRenderer = renderer.copy(animation = animation)
    this.copy(renderer = newRenderer)
  }

}
