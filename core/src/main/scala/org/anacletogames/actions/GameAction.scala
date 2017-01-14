package org.anacletogames.actions

import javax.print.attribute.standard.Destination

import com.badlogic.gdx.math.{GridPoint2, Intersector, Shape2D, Vector2}
import org.anacletogames.actions.GameAction.ActionContext
import org.anacletogames.battle.{BattleMap, GameGrid}
import org.anacletogames.entities.{CustomIntersector, MutableEntity, WithEntityMovement}

import scala.collection.mutable.ArrayBuffer

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

abstract class GameActionWithoutContext extends GameAction()

object GameAction {
  case class ActionContext(gameGrid: GameGrid) {
    def isTileAccessible(p: GridPoint2) = gameGrid.isTileAccessible(p)
  }

}
