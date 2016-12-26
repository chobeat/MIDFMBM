package org.anacletogames.entities

import com.badlogic.gdx.graphics.{Color, Pixmap, Texture}
import com.badlogic.gdx.graphics.g2d.Sprite
import org.anacletogames.battle.BattleMap
import render.{EntityWithMovementAnimation, WithDelta}

abstract class RectEntity(speed: Int = 1,
                          battleMap: BattleMap,
                          renderingContext: WithDelta)
    extends Entity(speed, battleMap, renderingContext) with EntityWithMovementAnimation with SingleTileMovingEntity{

}
