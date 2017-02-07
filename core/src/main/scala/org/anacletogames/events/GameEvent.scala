package org.anacletogames.events

import org.anacletogames.battle.GameGrid
import org.anacletogames.entities.Entity

/**
  * Created by simone on 21.01.17.
  */
trait GameEvent {
  val targetId: String
  def applyToEntity(e: Entity, context: GameGrid): Entity
}
