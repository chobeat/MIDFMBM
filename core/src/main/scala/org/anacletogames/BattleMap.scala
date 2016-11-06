package org.anacletogames

import com.badlogic.gdx.math.Intersector
import org.anacletogames.entities.RectEntity

class BattleMap(sizeX: Int, sizeY: Int) {
  val intersector = new Intersector

  val entities = scala.collection.mutable.MutableList[Entity]()

  def addEntity(entity:Entity):BattleMap={entities += entity;this}

}
