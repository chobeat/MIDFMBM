package org.anacletogames.entities

import com.badlogic.gdx.scenes.scene2d.Actor
import org.anacletogames.battle.GameMap
import render.WithDelta

/**
  * Created by simone on 14.01.17.
  */

trait Entity{

  val stackable: Boolean
}

abstract class ImmutableEntity(val speed: Int = 1,
                                val gameName: Option[String] = None,
                                renderingContext: WithDelta) extends Entity{

  def doStep():ImmutableEntity
}
