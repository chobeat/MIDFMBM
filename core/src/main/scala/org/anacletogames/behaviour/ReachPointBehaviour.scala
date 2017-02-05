package org.anacletogames.behaviour

import javax.security.auth.Subject

import com.badlogic.gdx.math.GridPoint2
import org.anacletogames.battle.GameGrid
import org.anacletogames.entities.{Entity, GameEvent, MovementEvent}

import scala.util.{Failure, Success, Try}

/**
  * Created by simone on 22.11.16.
  */
case class ReachPointBehaviour(destination: GridPoint2,
                               decidedPath: Option[Seq[GridPoint2]])
    extends EntityBehaviour {
  override def doStep(subject: Entity,
                      context: GameGrid): (EntityBehaviour, Seq[GameEvent]) = {
    val nextDestination = decidedPath.getOrElse(List()).headOption
    if (nextDestination.isEmpty)
      (this.setPath(findPath(subject, destination, context)), Seq())
    else {
      val decidedBehaviour = decideNextBehaviour(subject, context)
      val nextBehaviour = decidedBehaviour match {
        case Success(b) => b
        case Failure(_) => DoNothingBehaviour
      }

      val destination=if(subject.canIMoveThere(context,nextDestination.get))
        {
        nextDestination.get}
      else {
        decidedBehaviour.get.asInstanceOf[ReachPointBehaviour].decidedPath.get.head
      }
      (nextBehaviour, List(MovementEvent(subject.id,destination )))
    }


  }

  def findPath(subject: Entity, destination: GridPoint2, context: GameGrid) =
    context.findPath(subject, destination)

  def setPath(path: Seq[GridPoint2]): EntityBehaviour = {

    if (path.isEmpty)
      DoNothingBehaviour
    else
      this.copy(decidedPath = Some(path))
  }

  override def decideNextBehaviour(subject: Entity,
                                   context: GameGrid): Try[EntityBehaviour] = {
    val newPath = decidedPath match {
      case None =>
        this.findPath(subject, destination, context)

      case Some(head :: tail) =>
        if (subject.canIMoveThere(context, head)) tail else decidedPath.get
      case Some(Nil) => Seq()
    }
    Success(this.setPath(newPath))
  }
}
