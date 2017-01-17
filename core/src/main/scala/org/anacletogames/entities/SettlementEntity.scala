package org.anacletogames.entities

import org.anacletogames.battle.GameMap
import org.anacletogames.game.world.Settlement
import render.WithDelta

/**
  * Created by simone on 14.01.17.
  */
class SettlementEntity(settlement: Settlement,
                       val gameMap: GameMap,
                       renderingContext: WithDelta)
    extends ImmutableEntity(0, Some(settlement.name), renderingContext) {
  override def doStep(): SettlementEntity =
    new SettlementEntity(settlement.doStep(gameMap), gameMap, renderingContext)

  override val stackable: Boolean = false
}
