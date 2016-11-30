package org.anacletogames.behaviour

import com.badlogic.gdx.math.GridPoint2
import org.anacletogames.actions.{MoveToAdjacent, MoveUtil, NoAction}
import org.anacletogames.battle.BattleMap
import org.anacletogames.entities.Entity

import scala.util.Random

/**
  * Created by simone on 22.11.16.
  */
case class ReachPointBehaviour(subject: Entity,
                               destination: GridPoint2,
                               decidedPath: Option[Seq[GridPoint2]] = None)
    extends EntityBehaviour {
  override def decideNextAction(context: BattleMap): Decision = {

    val path = decidedPath match {
      case None =>
        context.findPath(subject, destination)

      case Some(nextPos :: rest) =>
        if (subject.canIMoveThere(nextPos))
          nextPos :: rest
        else
          context.findPath(subject, destination)
    }
    path match {

      case Nil => Decision(subject.getDefaultBehaviour, NoAction)

      case nextPosition :: Nil =>
        val action = MoveToAdjacent(subject, nextPosition)
        Decision(subject.getDefaultBehaviour, action)

      case nextPosition :: rest =>
        val action = MoveToAdjacent(subject, nextPosition)
        Decision(ReachPointBehaviour(subject, destination, Some(rest)), action)


    }

  }

}
