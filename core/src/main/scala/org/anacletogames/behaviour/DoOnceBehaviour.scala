package org.anacletogames.behaviour

import org.anacletogames.actions.GameAction
import org.anacletogames.battle.{BattleMap, GameMap}

import scala.util.{Failure, Success, Try}

/**
  * Created by simone on 03.12.16.
  */
case class DoOnceBehaviour(a:GameAction) extends EntityBehaviour{
  override def decideNextAction(context: GameMap): Try[GameAction] = Success(a)

  override def decideNextBehaviour(action: GameAction): Try[EntityBehaviour] = Failure(new Exception("No behaviour found"))
}
