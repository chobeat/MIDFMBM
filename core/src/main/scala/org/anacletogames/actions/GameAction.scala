package org.anacletogames.actions

import com.badlogic.gdx.math.{GridPoint2, Intersector, Shape2D, Vector2}
import org.anacletogames.actions.GameAction.ActionContext
import org.anacletogames.entities.{CustomIntersector, Entity}

import scala.collection.mutable.ArrayBuffer

abstract class GameAction()(implicit val actionContext: ActionContext) {
  def executeStep: Unit

  def isValid: Boolean = true

}

abstract class MovementGameAction(subject:Entity,displacement: Vector2)(
    implicit val context: ActionContext)
    extends GameAction {
  override def isValid = {

    val shapes = context.collidables - subject.stageBounds
    val (origX,origY)=(subject.stageBounds.getX,subject.stageBounds.getY)
    val projected=MoveUtil.projectShapeWithDisplacement(subject,displacement)
    subject.setPosition(projected.x,projected.y)
    val isValid = !shapes.exists(shape =>CustomIntersector.overlaps(shape,subject.stageBounds))
    subject.setPosition(origX,origY)
    isValid

  }
}

abstract class GameActionWithoutContext
    extends GameAction()(GameAction.emptyContext)

object GameAction {
  case class ActionContext(entities:ArrayBuffer[Entity],collidables:ArrayBuffer[Shape2D])

  val emptyContext: ActionContext = ActionContext(ArrayBuffer.empty,ArrayBuffer.empty)
}
