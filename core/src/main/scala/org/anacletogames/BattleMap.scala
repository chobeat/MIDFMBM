package org.anacletogames

import com.badlogic.gdx.math.Intersector
import org.anacletogames.entities.Entity

class BattleMap(sizeX: Int, sizeY: Int) {
  val intersector = new Intersector

  val entities = scala.collection.mutable.Map[(Int, Int), Entity]()
  def setEntity(x: Int, y: Int, newEntity: Entity): Boolean =
    if (x < sizeX && y < sizeY && x > 0 && y > 0) {
      entities.put((x, y), newEntity)
      true
    } else false


}
