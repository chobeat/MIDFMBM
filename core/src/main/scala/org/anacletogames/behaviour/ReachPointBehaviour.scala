package org.anacletogames.behaviour

import com.badlogic.gdx.math.GridPoint2
import org.anacletogames.battle.GameGrid
import org.anacletogames.entities.{
  Entity,
  GameEvent,
  MovementEvent,
  WithEntityMovement
}

import scala.util.{Failure, Success, Try}

/**
  * Created by simone on 22.11.16.
  */
case class ReachPointBehaviour(destination: GridPoint2,
                               decidedPath: Option[Seq[GridPoint2]])
    extends EntityBehaviour {
  override def doStep(subject: Entity, context: GameGrid): Seq[GameEvent] = {
    val destination = decidedPath.getOrElse(List()).headOption
    val events = destination match {
      case None => Seq()
      case Some(d) =>
        List(MovementEvent(subject.id, d))
    }
    events
  }

  override def decideNextBehaviour(subject: Entity,
                                   context: GameGrid): Try[EntityBehaviour] = {
    val newPath = decidedPath match {
      case None =>
        context.findPath(subject, destination)

      case Some(head :: tail) => tail
      case Some(Nil) => Seq()
    }
    if (newPath.isEmpty) {
      Success(DoNothingBehaviour)
    } else
      Success(this.copy(decidedPath = Some(newPath)))
  }
}
