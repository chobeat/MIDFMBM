package org.anacletogames.behaviour

import com.badlogic.gdx.math.GridPoint2
import org.anacletogames.actions._
import org.anacletogames.battle.BattleMap
import org.anacletogames.entities.Entity

import scala.collection.immutable.::

/**
  * Created by simone on 06.11.16.
  */
abstract class EntityBehaviour {

  def decideNextAction(context: BattleMap): Decision
}

case class DoOnceBehaviour(subject:Entity,action:GameAction,previousBehaviour:EntityBehaviour) extends EntityBehaviour{
  override def decideNextAction(context: BattleMap): Decision = Decision(previousBehaviour,action)
}



case class Decision(nextBehaviour: EntityBehaviour, nextAction: GameAction)

object EntityBehaviour {

  def doOnce(action: GameAction) =
    (self: Entity, entities: List[Entity]) => action

}
