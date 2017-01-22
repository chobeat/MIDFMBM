package org.anacletogames.entities

import com.badlogic.gdx.math.GridPoint2
import org.anacletogames.actions.GridMovement

/**
  * Created by simone on 21.01.17.
  */
sealed trait GameEvent {
  val target:Entity
}

case class MovementEvent(target:Entity,destination:GridMovement) extends GameEvent


object MovementEvent{
  def apply(target:Entity,destination:GridPoint2):MovementEvent={
    val x=destination.x - target.position.get.x
    val y=destination.y - target.position.get.y
    MovementEvent(target,GridMovement(x,y))
  }
}
