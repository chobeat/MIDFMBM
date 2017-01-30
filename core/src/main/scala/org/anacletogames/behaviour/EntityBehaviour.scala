package org.anacletogames.behaviour

import org.anacletogames.actions._
import org.anacletogames.battle.GameGrid
import org.anacletogames.entities.{Entity, GameEvent}

import scala.util.Try

/**
  * Created by simone on 06.11.16.
  */
abstract class EntityBehaviour {

  def doStep(subject: Entity, context: GameGrid): Seq[GameEvent]
  def decideNextBehaviour(subject: Entity,
                          context: GameGrid): Try[EntityBehaviour]
}
