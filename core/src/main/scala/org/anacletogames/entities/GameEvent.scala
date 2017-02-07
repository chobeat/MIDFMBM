package org.anacletogames.entities

import com.badlogic.gdx.math.GridPoint2
import org.anacletogames.actions.GridMovement
import org.anacletogames.battle.GameGrid

/**
  * Created by simone on 21.01.17.
  */
sealed trait GameEvent {
  val targetId: String
  def applyToEntity(e: Entity, context: GameGrid): Entity
}

case class MovementEvent(targetId: String, destination: GridPoint2)
    extends GameEvent {

  def applyToEntity(e: Entity, context: GameGrid): Entity = {
    if (e.canIMoveThere(context, destination))
      e.copy(position = Some(destination))
    else
      e
  }
}
