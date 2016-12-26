package org.anacletogames.actions

import javax.print.attribute.standard.Destination

import com.badlogic.gdx.math.{GridPoint2, Intersector, Shape2D, Vector2}
import org.anacletogames.actions.GameAction.ActionContext
import org.anacletogames.battle.{BattleMap, GameGrid}
import org.anacletogames.entities.{CustomIntersector, Entity, WithEntityMovement}

import scala.collection.mutable.ArrayBuffer

case class GridMovement(x: Int, y: Int) {
  def calculateDestination(currentPosition: GridPoint2) =
    new GridPoint2(currentPosition.x + x, currentPosition.y + y)

  def isAdjacent = {
    val sum=Math.abs(x)+Math.abs(y)
    sum<=1 && sum >=1 && sum!=0
  }
}

abstract class GameAction()(implicit val actionContext: Option[ActionContext]) {
  def execute: Unit

  def isValid: Boolean = true

}

abstract class MovementGameAction(subject: Entity with WithEntityMovement, destination: GridPoint2)(
    implicit val context: Option[ActionContext])
    extends GameAction {
  override def isValid = {
    subject.canIMoveThere(destination)

  }
}

abstract class GameActionWithoutContext extends GameAction()(None)

object GameAction {
  case class ActionContext(battleMap: BattleMap) {
    def isTileAccessible(p: GridPoint2) = battleMap.isTileAccessible(p)
  }

}
