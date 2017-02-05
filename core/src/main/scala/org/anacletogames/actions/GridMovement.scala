package org.anacletogames.actions

import com.badlogic.gdx.math.GridPoint2
import org.anacletogames.battle.GameGrid
import org.anacletogames.entities.Entity

case class GridMovement(x: Int, y: Int) {
  def calculateDestination(currentPosition: GridPoint2) =
    new GridPoint2(currentPosition.x + x, currentPosition.y + y)

  def isAdjacent = {
    val sum = Math.abs(x) + Math.abs(y)
    sum <= 1 && sum >= 1 && sum != 0
  }
}

