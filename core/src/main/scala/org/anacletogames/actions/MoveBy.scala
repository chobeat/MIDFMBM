package org.anacletogames.actions

import com.badlogic.gdx.math.{GridPoint2, Vector2}
import org.anacletogames.entities.{
  MutableEntity,
  PartyEntity,
  WithEntityMovement
}

case class MoveBy(entity: MutableEntity with WithEntityMovement,
                  movement: GridMovement)
    extends MovementGameAction(
      entity,
      movement.calculateDestination(entity.getPosition.get)) {
  def execute: Unit = {
    if (this.isValid) {
      entity.moveBy(movement.x, movement.y)
    }
  }
}

/*case class Attack(attacker:Entity, targetX:Float,targetY:Float) extends GameAction{
  def executeStep:Unit={
    val attackActor=new MeleeAttack(targetX,targetY)
    attacker.getParent.addActor(attackActor)
  }
}
 */

case class MoveToAdjacent(entity: MutableEntity with WithEntityMovement,
                          destination: GridPoint2)
    extends GameAction() {
  def execute: Unit = {
    val (diffX, diffY) = (destination.x - entity.getPosition.get.x,
                          destination.y - entity.getPosition.get.y)
    val nextStep = GridMovement(diffX, diffY)
    if (nextStep.isAdjacent && entity.canIMoveThere(destination))
      MoveBy(entity, nextStep).execute
  }
}

case class DoOnceAction(a: GameAction, entity: MutableEntity)
    extends GameAction() {
  override def execute: Unit = {
    a.execute
  }
}

case class RelocateTo(entity: MutableEntity with WithEntityMovement,
                      destination: GridPoint2)
    extends MovementGameAction(entity, destination) {
  def execute: Unit = {
    entity.gameMap.removeEntity(entity)
    entity.gameMap.addEntity(entity, destination)
  }
}

case class MultiAction(actions: GameAction*) extends GameAction{
  def execute: Unit = {
    actions.foreach(_.execute)
  }

}

case object NoAction extends GameAction{
  def execute: Unit = {}
}
