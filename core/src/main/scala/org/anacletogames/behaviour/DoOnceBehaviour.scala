package org.anacletogames.behaviour

import org.anacletogames.battle.GameGrid
import org.anacletogames.entities.{Entity, GameEvent}

import scala.util.{Failure, Success, Try}

/**
  * Created by simone on 03.12.16.
  */
case class DoOnceBehaviour(a: GameEvent) extends EntityBehaviour {
  override def doStep(subject: Entity,
                      context: GameGrid): (Entity, Seq[GameEvent]) =
    subject.doStep(List(a), context)

  def decideNextBehaviour(subject: Entity,
                          emittedEvents: Seq[GameEvent],
                          context: GameGrid): Try[EntityBehaviour] =
    Failure(new Exception("No behaviour found"))
}
