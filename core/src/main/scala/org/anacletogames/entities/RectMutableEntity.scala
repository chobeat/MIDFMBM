package org.anacletogames.entities

import org.anacletogames.battle.BattleMap
import render.{EntityWithAnimation, WithDelta}

abstract class RectMutableEntity(speed: Int = 1,
                                 battleMap: BattleMap,
                                 gameName:Option[String],
                                 renderingContext: WithDelta)
    extends MutableEntity(speed, battleMap,gameName, renderingContext) with EntityWithAnimation with SingleTileMovingEntity{

}
