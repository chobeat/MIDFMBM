package org.anacletogames.actions

import com.badlogic.gdx.math.{GridPoint2, Vector2}
import org.anacletogames.battle.BattleMap
import org.anacletogames.entities.Entity

/**
  * Created by simone on 12.11.16.
  */
object MoveUtil {

  /*
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

  def projectShapeWithDisplacement(entity: Entity,displacement:Vector2)={
    val adjustedDisplacement=displacement.limit(entity.speed)
    val shape=entity.stageBounds
    val origPos=new Vector2(shape.x,shape.y)
    origPos.add(adjustedDisplacement)

  }*/

  def findPath(subject: Entity, battleMap: BattleMap): Seq[GridPoint2] =
    {
      val s=List(new GridPoint2(2,2),new GridPoint2(3,3))
      Stream.continually(s).flatten.take(10000).toList
    }
}
