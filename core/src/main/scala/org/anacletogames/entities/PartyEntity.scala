package org.anacletogames.entities

import org.anacletogames.actions.GridMovement
import org.anacletogames.battle.GameMap
import org.anacletogames.behaviour.EntityBehaviour
import org.anacletogames.entities.assets.MovementAnimatedTexture
import org.anacletogames.game.world.Party
import render._

import scala.collection.mutable

/**
  * Created by simone on 15.01.17.
  */
class PartyEntity(wrappedParty: Party,
                  gameMap: GameMap,
                  renderingContext: WithDelta)
    extends MutableEntity(1,
                          gameMap,
                          Some(wrappedParty.name),
                          renderingContext)
    with DoNothingByDefault
    with EntityWithAnimation
    with SingleTileMovingEntity
    with MaleAnimatedTexture
    with WithNonStackable {

  private var movedCountToday = 0
  private val movementsToBeDone: scala.collection.mutable.Queue[GridMovement] =
    mutable.Queue()

  def isMovingAnimationCompleted() =
    this.animation match{
      case x:MovementAnimation=>
        this.animation.alpha>=1.0
      case _=>true
    }


  def isTimeToAct(): Boolean = movedCountToday >= Constants.tileMovementsToDay

  def resetMovedCount(): Unit = movedCountToday = 0

  def increaseMovedCount(): Unit = movedCountToday += 1

  def enqueueMovement(g: GridMovement) = movementsToBeDone.enqueue(g)

  override def act() = {
    if (movementsToBeDone.nonEmpty) {
      this.increaseMovedCount()
      val movement = movementsToBeDone.dequeue()
      this.moveBy(movement.x, movement.y)
    }
  }

}
