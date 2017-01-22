package org.anacletogames.entities

import org.anacletogames.battle.GameGrid
import org.anacletogames.game.world.Settlement
import render.WithDelta

/**
  * Created by simone on 14.01.17.
  */
class SettlementEntity(val settlement: Settlement,
                        val gameGrid: GameGrid,
                       renderingContext: WithDelta)
    extends Entity(None,0,  Some(settlement.name), false, renderer = null) {
  def doStep(): SettlementEntity =
    new SettlementEntity(settlement.doStep(gameGrid), gameGrid, renderingContext)

  override val stackable: Boolean = false
}
