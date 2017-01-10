package org.anacletogames.behaviour
import org.anacletogames.actions.{GameAction, NoAction}
import org.anacletogames.battle.{BattleMap, GameMap}

import scala.util.{Success, Try}

/**
  * Created by simone on 22.11.16.
  */
case object DoNothingBehaviour extends EntityBehaviour{
  override def decideNextAction(context: GameMap) = Success(NoAction)

  override def decideNextBehaviour(action: GameAction): Try[EntityBehaviour] = Success(DoNothingBehaviour)
}
