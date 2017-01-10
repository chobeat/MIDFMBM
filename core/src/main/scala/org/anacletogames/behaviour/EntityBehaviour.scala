package org.anacletogames.behaviour

import org.anacletogames.actions._
import org.anacletogames.battle.{BattleMap, GameGrid, GameMap}

import scala.util.Try

/**
  * Created by simone on 06.11.16.
  */
abstract class EntityBehaviour {

  def decideNextAction(context: GameMap): Try[GameAction]
  def decideNextBehaviour(action:GameAction): Try[EntityBehaviour]
}




