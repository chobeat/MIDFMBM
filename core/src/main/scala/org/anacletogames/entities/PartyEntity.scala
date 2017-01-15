package org.anacletogames.entities

import org.anacletogames.battle.GameMap
import org.anacletogames.behaviour.EntityBehaviour
import org.anacletogames.entities.assets.MovementAnimatedTexture
import org.anacletogames.game.world.Party
import render.{EntityWithAnimation, MaleAnimatedTexture, WithDelta}

/**
  * Created by simone on 15.01.17.
  */
class PartyEntity(wrappedParty: Party,
                  gameMap: GameMap,
                  renderingContext: WithDelta)
    extends MutableEntity(1,
                          gameMap,
                          Some(wrappedParty.name),
                          renderingContext)
    with DoNothingByDefault
    with EntityWithAnimation
    with SingleTileMovingEntity
    with MaleAnimatedTexture
    with WithNonStackable {}
