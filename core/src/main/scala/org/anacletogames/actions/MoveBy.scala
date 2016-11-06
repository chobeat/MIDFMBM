package org.anacletogames.actions

import com.badlogic.gdx.scenes.scene2d.Actor
import org.anacletogames.Entity

case class MoveBy(entity: Entity, x: Float, y: Float) extends GameAction {
  def executeStep: Unit = {
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
case class MoveTo(entity: Entity, destX: Float, destY: Float)
    extends GameAction {
  def executeStep: Unit = {

    val (movX,movY)=MoveUtil.projectStep(entity,destX,destY)
    MoveBy(entity, movX, movY).executeStep
  }
}

case class DoOnceAction(a:GameAction,entity: Entity) extends  GameAction{
  override def executeStep: Unit = {
    a.executeStep
    entity.setBehaviour((_,_)=>NoAction)
  }
}

object MoveUtil{
  def projectStep(entity: Entity,destX:Float,destY:Float)= {
    val currX = entity.getX
    val currY = entity.getY

    val movCalc: PartialFunction[(Float, Float), Float] = {
      case (a, b) if a == b => 0
      case (a, b) if a - b < entity.speed => entity.speed
      case (a, b) if b - a < entity.speed => -entity.speed
      case (a, b) if a > b => a - entity.speed
      case (a, b) if b > a => a + entity.speed
    }

    val movX = movCalc(currX, destX)

    val movY = movCalc(currY, destY)

    (movX,movY)
  }
}

case class RelocateTo(entity: Entity, x: Float, y: Float) extends GameAction {
  def executeStep: Unit = {
    entity.setX(x)
    entity.setY(y)
  }
}

case class MultiAction(actions: GameAction*) extends GameAction {
  def executeStep: Unit = {
    actions.foreach(_.executeStep)
  }
}

case object NoAction extends GameAction {
  def executeStep: Unit = {
  }
}