package org.anacletogames.behaviour

import org.anacletogames.actions._
import org.anacletogames.battle.GameGrid
import org.anacletogames.entities.Entity
import org.anacletogames.events.GameEvent

import scala.util.Try

/**
  * Created by simone on 06.11.16.
  */
abstract class EntityBehaviour {

  def doStep(subject: Entity, context: GameGrid): (EntityBehaviour,Seq[GameEvent])
  def decideNextBehaviour(subject: Entity,
                          context: GameGrid): Try[EntityBehaviour]
}
