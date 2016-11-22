package org.anacletogames.behaviour
import org.anacletogames.actions.NoAction
import org.anacletogames.battle.BattleMap

/**
  * Created by simone on 22.11.16.
  */
case object DoNothingBehaviour extends EntityBehaviour{
  override def decideNextAction(context: BattleMap): Decision = Decision(DoNothingBehaviour,NoAction)
}
