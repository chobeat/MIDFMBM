package org.anacletogames.actions

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Actor
import org.anacletogames.actions.GameAction.ActionContext
import org.anacletogames.entities.Entity

case class MoveBy(entity: Entity, x: Float, y: Float)
    extends MovementGameAction(entity,new Vector2(x,y))(
      entity.getActionContext) {
  def executeStep: Unit = {
    if (this.isValid)
      entity.moveBy(x, y)
  }
}

/*case class Attack(attacker:Entity, targetX:Float,targetY:Float) extends GameAction{
  def executeStep:Unit={
    val attackActor=new MeleeAttack(targetX,targetY)
    attacker.getParent.addActor(attackActor)
  }
}
 */
case class MoveTo(entity: Entity, destination: Vector2)
    extends GameAction()(entity.getActionContext) {
  def executeStep: Unit = {

    val (movX, movY) = MoveUtil.projectStep(entity, destination)
    MoveBy(entity, movX, movY).executeStep
  }
}

case class DoOnceAction(a: GameAction, entity: Entity)(
    implicit actionContext: ActionContext)
    extends GameAction()(entity.getActionContext) {
  override def executeStep: Unit = {
    a.executeStep
    entity.setBehaviour((_, _) => NoAction)
  }
}

case class RelocateTo(entity: Entity, destination: Vector2)
    extends MovementGameAction(entity,destination)(entity.getActionContext) {
  def executeStep: Unit = {
    entity.setX(destination.x)
    entity.setY(destination.x)
  }
}

case class MultiAction(actions: GameAction*) extends GameActionWithoutContext {
  def executeStep: Unit = {
    actions.foreach(_.executeStep)
  }

}

case object NoAction extends GameActionWithoutContext {
  def executeStep: Unit = {}
}
