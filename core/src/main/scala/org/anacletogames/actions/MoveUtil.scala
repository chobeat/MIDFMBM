package org.anacletogames.actions

import com.badlogic.gdx.math.Vector2
import org.anacletogames.entities.Entity

/**
  * Created by simone on 12.11.16.
  */
object MoveUtil {
  def projectStep(entity: Entity, destination:Vector2) = {
    val currX = entity.getX
    val currY = entity.getY

    val movCalc: PartialFunction[(Float, Float), Float] = {
      case (a, b) if a == b => 0
      case (a, b) if a - b < entity.speed => entity.speed
      case (a, b) if b - a < entity.speed => -entity.speed
      case (a, b) if a > b => a - entity.speed
      case (a, b) if b > a => a + entity.speed
    }

    val movX = movCalc(currX, destination.x)

    val movY = movCalc(currY, destination.y)

    (movX, movY)
  }
}
