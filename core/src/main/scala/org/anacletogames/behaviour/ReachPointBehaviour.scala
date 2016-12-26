package org.anacletogames.behaviour

import com.badlogic.gdx.math.GridPoint2
import org.anacletogames.actions.{GameAction, MoveToAdjacent, MoveUtil, NoAction}
import org.anacletogames.battle.BattleMap
import org.anacletogames.entities.{Entity, WithEntityMovement}
import sun.plugin.dom.exception.InvalidStateException

import scala.util.{Failure, Random, Success, Try}

/**
  * Created by simone on 22.11.16.
  */
case class ReachPointBehaviour(subject: Entity with WithEntityMovement,
                               destination: GridPoint2,
                               var decidedPath: Option[Seq[GridPoint2]] = None)
    extends EntityBehaviour {
  override def decideNextAction(context: BattleMap): Try[GameAction] = {

    val path = decidedPath match {
      case None =>
        context.findPath(subject, destination)

      case Some(path) => path
    }
    decidedPath = Some(path)

    path match {
      case Nil => Success(NoAction)
      case head :: tail => Success(MoveToAdjacent(subject, head))
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
          new InvalidStateException(
            "Invalid movement action for ReachPointBehaviour"))
    }

  }
}
