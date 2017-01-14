package org.anacletogames.entities

import com.sun.javafx.tk.Toolkit.ImageRenderingContext
import org.anacletogames.actions.GameAction
import org.anacletogames.actions.GameAction.ActionContext
import org.anacletogames.battle.GameMap
import org.anacletogames.behaviour.EntityBehaviour
import org.anacletogames.game.world.Settlement
import render.WithDelta

import scala.util.{Success, Try}

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

class SettlementGameAction(s: Settlement, gameMap: GameMap)
    extends GameAction() {
  override def execute: Unit = s.doStep(gameMap)
}
