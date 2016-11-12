package org.anacletogames.actions

import com.badlogic.gdx.math.{GridPoint2, Vector2}
import org.anacletogames.actions.GameAction.ActionContext
import org.anacletogames.entities.Entity

import scala.collection.mutable.ArrayBuffer

abstract class GameAction()(implicit val actionContext: ActionContext) {
  def executeStep: Unit

  def isValid: Boolean = true

}

abstract class MovementGameAction(subject:Entity,destination: Vector2)(
    implicit val context: ActionContext)
    extends GameAction {
  override def isValid = {
    val shapes = (context -= subject).map(_.stageBounds)
    !shapes.exists(shape => shape.overlaps(subject.stageBounds))
  }
}

abstract class GameActionWithoutContext
    extends GameAction()(GameAction.emptyContext)

object GameAction {
  type ActionContext = ArrayBuffer[Entity]

  val emptyContext: ActionContext = ArrayBuffer()
}
