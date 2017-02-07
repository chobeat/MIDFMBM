package org.anacletogames.behaviour

import javax.security.auth.Subject

import com.badlogic.gdx.math.GridPoint2
import org.anacletogames.battle.GameGrid
import org.anacletogames.entities.Entity
import org.anacletogames.events.{GameEvent, MovementEvent}

import scala.util.{Failure, Success, Try}

/**
  * Created by simone on 22.11.16.
  */
case class ReachPointBehaviour(destination: GridPoint2,
                               decidedPath: Option[Seq[GridPoint2]])
    extends EntityBehaviour {
  override def doStep(subject: Entity,
                      context: GameGrid): (EntityBehaviour, Seq[GameEvent]) = {
    val path = decidedPath.getOrElse(findPath(subject, destination, context))

    val nextStep = getNextValidStep(path, subject, context)
    val nextBehaviour =
      decideNextBehaviour(subject, context).getOrElse(DoNothingBehaviour)
    nextStep match {
      case None => (nextBehaviour, List())
      case Some(step) =>
        (nextBehaviour, List(MovementEvent(subject.id, step)))

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

  def getNextValidStep(path: Seq[GridPoint2],
                       subject: Entity,
                       context: GameGrid): Option[GridPoint2] = {
    val candidateNextStep = path match {
      case head :: Nil
          if head == destination || head == subject.position.get =>
        None
      case head :: Nil => Some(head)
      case Nil => None
      case head :: tail if head == subject.position.get => Some(tail.head)
      case head :: tail => Some(head)
    }
    candidateNextStep.flatMap(ns =>
      if (subject.canIMoveThere(context, ns)) Some(ns) else None)
  }
}
