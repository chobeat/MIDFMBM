package org.anacletogames.behaviour
import org.anacletogames.battle.GameGrid
import org.anacletogames.entities.Entity
import org.anacletogames.events.GameEvent

import scala.util.{Success, Try}

/**
  * Created by simone on 22.11.16.
  */
case object DoNothingBehaviour extends EntityBehaviour {
  override def doStep(subject: Entity, context: GameGrid): (EntityBehaviour, Seq[GameEvent]) =
    (this,List())

  override def decideNextBehaviour(subject: Entity,
                                   context: GameGrid): Try[EntityBehaviour] =
    Success(DoNothingBehaviour)
}
