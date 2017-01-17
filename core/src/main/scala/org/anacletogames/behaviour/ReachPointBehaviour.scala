package org.anacletogames.behaviour

import com.badlogic.gdx.math.GridPoint2
import org.anacletogames.actions.{GameAction, MoveToAdjacent, NoAction}
import org.anacletogames.battle.GameMap
import org.anacletogames.entities.{MutableEntity, WithEntityMovement}

import scala.util.{Failure, Success, Try}

/**
  * Created by simone on 22.11.16.
  */
case class ReachPointBehaviour(subject: MutableEntity with WithEntityMovement,
                               destination: GridPoint2,
                               var decidedPath: Option[Seq[GridPoint2]] = None)
    extends EntityBehaviour {
  override def decideNextAction(context: GameMap): Try[GameAction] = {

    decidedPath = decidedPath match {
      case None =>
        Some(context.findPath(subject, destination))

      case Some(_) => decidedPath
    }

    decidedPath match {
      case Some(Nil) => Success(NoAction)
      case Some(head :: tail) => Success(MoveToAdjacent(subject, head))
    }
  }

  override def decideNextBehaviour(action: GameAction): Try[EntityBehaviour] = {

    (action, decidedPath) match {
      case (MoveToAdjacent(e, d), Some(head :: Nil))
          if d.equals(head) =>
        Success(DoNothingBehaviour)
      case (MoveToAdjacent(e, d), Some(head :: rest))
          if d.equals(head) =>
        Success(ReachPointBehaviour(subject, destination, Some(rest)))
      case _ =>
        Failure(
          new Exception(
            "Invalid movement action for ReachPointBehaviour"))
    }

  }
}
