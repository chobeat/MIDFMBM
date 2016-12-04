package org.anacletogames.actions

import com.badlogic.gdx.math.{GridPoint2, Vector2}
import com.badlogic.gdx.scenes.scene2d.Actor
import org.anacletogames.actions.GameAction.ActionContext
import org.anacletogames.entities.Entity

case class MoveBy(entity: Entity, movement: GridMovement)
    extends MovementGameAction(entity, movement.calculateDestination(entity.getPosition.get))(
      Some(entity.getActionContext)) {
  def execute: Unit = {
    if (this.isValid)
      entity.moveBy(movement.x,movement.y)
  }
}

/*case class Attack(attacker:Entity, targetX:Float,targetY:Float) extends GameAction{
  def executeStep:Unit={
    val attackActor=new MeleeAttack(targetX,targetY)
    attacker.getParent.addActor(attackActor)
  }
}
 */

case class MoveToAdjacent(entity: Entity, destination: GridPoint2)
    extends GameAction()(Some(entity.getActionContext)) {
  def execute: Unit = {
    val (diffX,diffY)=(destination.x - entity.getPosition.get.x,destination.y - entity.getPosition.get.y)
    val nextStep=GridMovement(diffX,diffY)
    if(nextStep.isAdjacent&& entity.canIMoveThere(destination))
        MoveBy(entity, nextStep).execute
  }
}

case class DoOnceAction(a: GameAction, entity: Entity)
    extends GameAction()(Some(entity.getActionContext)) {
  override def execute: Unit = {
    a.execute
  }
}

case class RelocateTo(entity: Entity, destination: GridPoint2)
    extends MovementGameAction(entity, destination)(Some(entity.getActionContext)) {
  def execute: Unit = {
    context.map(ctx=>{
      if(this.isValid)
        ctx.battleMap
    })
  }
}

case class MultiAction(actions: GameAction*) extends GameActionWithoutContext {
  def execute: Unit = {
    actions.foreach(_.execute)
  }

}

case object NoAction extends GameActionWithoutContext {
  def execute: Unit = {}
}
