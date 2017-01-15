package org.anacletogames.entities

import com.badlogic.gdx.math.GridPoint2
import com.badlogic.gdx.scenes.scene2d.Actor
import org.anacletogames.battle.GameMap

/**
  * Created by simone on 14.01.17.
  */
trait Entity extends Actor {
  val gameName: Option[String] = None
  val stackable: Boolean
  val gameMap: GameMap

  def getPosition = gameMap.getEntityPosition(this)

  def getGameName: String = gameName.getOrElse("UnnamedEntity")
}
