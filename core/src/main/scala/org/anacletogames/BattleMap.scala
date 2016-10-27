package org.anacletogames

import com.badlogic.gdx.math.Intersector

class BattleMap(sizeX: Int, sizeY: Int) {
  val intersector = new Intersector

  val entities = scala.collection.mutable.Map[(Int, Int), Entity]()


}
