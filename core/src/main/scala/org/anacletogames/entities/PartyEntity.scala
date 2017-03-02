package org.anacletogames.entities

import com.badlogic.gdx.math.GridPoint2
import org.anacletogames.actions.GridMovement
import org.anacletogames.battle.GameGrid
import org.anacletogames.behaviour.{DoNothingBehaviour, EntityBehaviour}
import org.anacletogames.entities.assets.MaleBaseCharacterTexture
import org.anacletogames.events.GameEvent
import org.anacletogames.game.world.Party
import render._

import scala.collection.mutable
import scala.util.{Success, Try}

/**
  * Created by simone on 15.01.17.
  */
class PartyBehaviour extends EntityBehaviour {
  override def doStep(subject: Entity,
                      context: GameGrid): (EntityBehaviour, Seq[GameEvent]) =
    (decideNextBehaviour(subject, context).get, Seq())

  override def decideNextBehaviour(subject: Entity,
                                   context: GameGrid): Try[EntityBehaviour] =
    Success(DoNothingBehaviour)
}

object PartyEntity {

  val partyEntityId = "party-entity"

  def createParty(position: GridPoint2, renderingContext: WithDelta) = {
    val renderer = EntityRenderer(renderingContext, MaleBaseCharacterTexture)
    Entity(Some(position),
           1,
           Some("party"),
           false,
           renderer,
           new PartyBehaviour,
           id = partyEntityId)
  }
}
