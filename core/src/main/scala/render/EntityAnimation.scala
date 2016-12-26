package render

import com.badlogic.gdx.graphics.g2d.{Animation, Batch}
import org.anacletogames.entities.{Entity, EntityOrientation}

/**
  * Created by simone on 26.12.16.
  */
abstract class EntityAnimation(animation: Animation) {

  def draw(batch: Batch, entity: Entity): Unit
}
