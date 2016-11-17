package org.anacletogames.actions

import com.badlogic.gdx.math.{GridPoint2, Vector2}
import com.badlogic.gdx.scenes.scene2d.Actor
import org.anacletogames.actions.GameAction.ActionContext
import org.anacletogames.entities.Entity

case class MoveBy(entity: Entity, x: Int, y: Int)
    extends MovementGameAction(entity, new GridPoint2(x, y))(
      Some(entity.getActionContext)) {
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
/*
case class MoveTo(entity: Entity, destination: Vector2)
    extends GameAction()(entity.getActionContext) {
  def executeStep: Unit = {

    val (movX, movY) = MoveUtil.projectStep(entity, destination)
    MoveBy(entity, movX, movY).executeStep
  }
}
 */
case class DoOnceAction(a: GameAction, entity: Entity)
    extends GameAction()(Some(entity.getActionContext)) {
  override def executeStep: Unit = {
    a.executeStep
    entity.setBehaviour((_, _) => NoAction)
  }
}

case class RelocateTo(entity: Entity, destination: GridPoint2)
    extends MovementGameAction(entity, destination)(Some(entity.getActionContext)) {
  def executeStep: Unit = {
    context.map(ctx=>{
      if(this.isValid)
        ctx.battleMap
    })
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
