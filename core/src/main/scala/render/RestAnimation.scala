package render

import com.badlogic.gdx.graphics.g2d.{Animation, Batch, TextureRegion}
import org.anacletogames.battle.GameGrid
import org.anacletogames.entities.{Entity, EntityOrientation, EntityRenderer}

/**
  * Created by simone on 26.12.16.
  */
case class RestAnimation(animation: Animation[TextureRegion],
                         override val orientation: EntityOrientation)
    extends EntityAnimation(animation, orientation) {
  override def draw(batch: Batch,
                    renderer: EntityRenderer,
                    entity: Entity): Unit = {

    val currentFrame = animation.getKeyFrame(0)
    val position = entity.position
    position match {
      case Some(drawPosition) =>
        batch.draw(currentFrame,
                   drawPosition.x * Constants.TileWidth,
                   drawPosition.y * Constants.TileHeigth)
      case None =>
    }
  }
}
