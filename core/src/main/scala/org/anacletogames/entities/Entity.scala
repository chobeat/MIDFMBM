package org.anacletogames.entities

import org.anacletogames.battle.GameMap

/**
  * Created by simone on 14.01.17.
  */
trait Entity {
  val gameName: Option[String] = None
  val stackable: Boolean
  val gameMap: GameMap

  def getPosition = gameMap.getEntityPosition(this)

  def getGameName: String = gameName.getOrElse("UnnamedEntity")
}
