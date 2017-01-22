package org.anacletogames.entities

import com.badlogic.gdx.math.GridPoint2
import org.anacletogames.actions.GridMovement
import org.anacletogames.battle.GameGrid
import org.anacletogames.game.world.Party
import render._

import scala.collection.mutable

/**
  * Created by simone on 15.01.17.
  */
class PartyEntity(wrappedParty: Party,
                  gameGrid: GameGrid,
                  renderingContext: WithDelta) {

  val entity =
    Entity(Some(new GridPoint2(0, 0)), 1, Some(wrappedParty.name), false, null)
  private var movedCountToday = 0
  private val movementsToBeDone: scala.collection.mutable.Queue[GridMovement] =
    mutable.Queue()
  var hasEverMoved = false
  def isMovingAnimationCompleted() =
    entity.renderer.animation match {
      case x: MovementAnimation =>
        entity.renderer.animation.alpha >= 1.0
      case _ => true
    }

  def getMovedCountToday = movedCountToday

  def isTimeToAct(): Boolean = movedCountToday >= Constants.tileMovementsToDay

  def resetMovedCount(): Unit = movedCountToday = 0

  def increaseMovedCount(): Unit = movedCountToday += 1

  def enqueueMovement(g: GridMovement) = movementsToBeDone.enqueue(g)

  def act() = {
    if (movementsToBeDone.nonEmpty) {
      hasEverMoved = true

      if (movedCountToday >= Constants.tileMovementsToDay)
        resetMovedCount()

      this.increaseMovedCount()

      val movement = movementsToBeDone.dequeue()
      //this.moveBy(movement.x, movement.y)
    }
  }

}
