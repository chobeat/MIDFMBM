package org.anacletogames.events

import com.badlogic.gdx.math.GridPoint2
import org.anacletogames.actions.GridMovement
import org.anacletogames.battle.GameGrid
import org.anacletogames.entities.Entity

/**
  * Created by simone on 23.02.17.
  */
case class MoveByEvent(targetId: String, movement: GridMovement)
  extends GameEvent {

  def applyToEntity(e: Entity, context: GameGrid): Entity = {
    val destination= movement.calculateDestination(e.position.get)
    if (e.canIMoveThere(context, destination))
      e.copy(position = Some(destination))
    else
      e
  }
}
