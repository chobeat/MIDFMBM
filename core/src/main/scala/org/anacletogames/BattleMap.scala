package org.anacletogames

import com.badlogic.gdx.math.Intersector
import org.anacletogames.entities.{Entity, RectEntity}

class BattleMap(sizeX: Int, sizeY: Int) {
  val intersector = new Intersector

  val entities = scala.collection.mutable.ArrayBuffer[Entity]()

  def addEntity(entity:Entity):BattleMap={entities += entity;this}

}
