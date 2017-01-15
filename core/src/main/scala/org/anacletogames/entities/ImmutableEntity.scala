package org.anacletogames.entities

import com.badlogic.gdx.scenes.scene2d.Actor
import org.anacletogames.battle.GameMap
import render.WithDelta



abstract class ImmutableEntity(val speed: Int = 1,
                               override val gameName: Option[String] = None,
                               renderingContext: WithDelta)
    extends Actor with Entity {

  def doStep(): ImmutableEntity
}
