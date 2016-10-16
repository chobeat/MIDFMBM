package org.anacletogames.entities

import com.badlogic.gdx.math.Shape2D
import org.anacletogames.TestActor
import org.anacletogames.actions.{MoveTo, GameAction, MoveBy}
import org.anacletogames.orders.Order

import scala.util.Random

abstract class Entity(x: Float, y: Float, val speed:Float=1) {
  val bounds:Shape2D
  val actor = new TestActor()
  actor.setX(x)
  actor.setY(y)
  var orders=List[Order]()
  def nextStep(): GameAction = {

    MoveTo(this,300,300)
  }
}

