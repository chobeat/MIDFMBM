package org.anacletogames.behaviour

import com.badlogic.gdx.math.GridPoint2
import org.anacletogames.actions._
import org.anacletogames.battle.BattleMap
import org.anacletogames.entities.Entity

import scala.collection.immutable.::
import scala.util.Try

/**
  * Created by simone on 06.11.16.
  */
abstract class EntityBehaviour {

  def decideNextAction(context: BattleMap): Try[GameAction]
  def decideNextBehaviour(action:GameAction): Try[EntityBehaviour]
}




