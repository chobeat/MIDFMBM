package org.anacletogames.actions

import com.badlogic.gdx.scenes.scene2d.Actor
import org.anacletogames.entities.Entity

case class MoveBy(entity: Entity, x: Float, y: Float) extends GameAction {
  def executeStep: Unit = {
    entity.actor.moveBy(x, y)
  }
}

case class MoveTo(entity: Entity, destX: Float, destY: Float)
    extends GameAction {
  def executeStep: Unit = {

    val currX = entity.actor.getX
    val currY = entity.actor.getY

    val movCalc: PartialFunction[(Float, Float), Float] = {
      case (a, b) if a == b => 0
      case (a, b) if a - b < entity.speed => entity.speed
      case (a, b) if b - a < entity.speed => -entity.speed
      case (a, b) if a > b => a - entity.speed
      case (a, b) if b > a => a + entity.speed
    }

    val movX = movCalc(currX, destX)

    val movY = movCalc(currY, destY)

    MoveBy(entity, movX, movY).executeStep
  }
}

case class RelocateTo(entity: Entity, x: Float, y: Float) extends GameAction {
  def executeStep: Unit = {
    entity.actor.setX(x)
    entity.actor.setY(y)
  }
}

case class MultiAction(actions: GameAction*) extends GameAction {
  def executeStep: Unit = {
    actions.foreach(_.executeStep)
  }
}
