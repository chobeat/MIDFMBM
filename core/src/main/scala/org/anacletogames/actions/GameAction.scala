package org.anacletogames.actions

import com.badlogic.gdx.math.GridPoint2
import org.anacletogames.entities.{MutableEntity, WithEntityMovement}

case class GridMovement(x: Int, y: Int) {
  def calculateDestination(currentPosition: GridPoint2) =
    new GridPoint2(currentPosition.x + x, currentPosition.y + y)

  def isAdjacent = {
    val sum=Math.abs(x)+Math.abs(y)
    sum<=1 && sum >=1 && sum!=0
  }
}

abstract class GameAction() {
  def execute: Unit

  def isValid: Boolean = true

}

abstract class MovementGameAction(subject: MutableEntity with WithEntityMovement, destination: GridPoint2)
    extends GameAction {
  override def isValid = {
    subject.canIMoveThere(destination)
  }
}

