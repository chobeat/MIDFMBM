package org.anacletogames.entities

import com.badlogic.gdx.math.GridPoint2
import org.anacletogames.actions.GridMovement

/**
  * Created by simone on 21.01.17.
  */
sealed trait GameEvent {
  val targetId: String
  def applyToEntity(e: Entity): Entity
}

case class MovementEvent(targetId: String, destination: GridPoint2)
    extends GameEvent {

  def applyToEntity(e: Entity): Entity = {
    e.copy(position = Some(destination))
  }
}
