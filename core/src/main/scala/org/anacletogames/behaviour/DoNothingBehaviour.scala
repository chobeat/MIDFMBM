package org.anacletogames.behaviour
import org.anacletogames.battle.GameGrid
import org.anacletogames.entities.{Entity, GameEvent}

import scala.util.{Success, Try}

/**
  * Created by simone on 22.11.16.
  */
case object DoNothingBehaviour extends EntityBehaviour {
  override def doStep(subject: Entity,
                      context: GameGrid): (Entity, Seq[GameEvent]) =
    (subject,List())

  override def decideNextBehaviour(subject: Entity,
                                   context: GameGrid): Try[EntityBehaviour] =
    Success(DoNothingBehaviour)
}
