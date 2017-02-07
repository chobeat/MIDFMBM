package org.anacletogames.events

import com.badlogic.gdx.math.GridPoint2
import org.anacletogames.battle.GameGrid
import org.anacletogames.entities.Entity

/**
  * Created by simone on 07.02.17.
  */
case class MovementEvent(targetId: String, destination: GridPoint2)
    extends GameEvent {

  def applyToEntity(e: Entity, context: GameGrid): Entity = {
    if (e.canIMoveThere(context, destination))
      e.copy(position = Some(destination))
    else
      e
  }
}
