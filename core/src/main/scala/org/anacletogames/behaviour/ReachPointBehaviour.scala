package org.anacletogames.behaviour

import com.badlogic.gdx.math.GridPoint2
import org.anacletogames.actions.{MoveToAdjacent, MoveUtil}
import org.anacletogames.battle.BattleMap
import org.anacletogames.entities.Entity

/**
  * Created by simone on 22.11.16.
  */
case class ReachPointBehaviour(subject: Entity,
                               destination: GridPoint2,
                               decidedPath: Option[Seq[GridPoint2]]=None)
  extends EntityBehaviour {
    override def decideNextAction(context: BattleMap): Decision = {

      def decideNextMovement(proposedStep: MoveToAdjacent) = {}

      val nextPosition::path = decidedPath match {
        case None =>
          MoveUtil.findPath(subject, context)

        case Some(nextPos :: rest) =>
          if (subject.canIMoveThere(nextPos))
            nextPos :: rest
          else
            MoveUtil.findPath(subject, context)
      }

      val action = MoveToAdjacent(subject, nextPosition)

      if (path.nonEmpty)
        Decision(ReachPointBehaviour(subject, destination, Some(path)), action)
      else
        Decision(subject.getDefaultBehaviour, action)

    }

  }
