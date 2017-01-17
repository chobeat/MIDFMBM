package render

import com.badlogic.gdx.graphics.g2d.{Animation, Batch, TextureRegion}
import org.anacletogames.entities.{EntityOrientation, MutableEntity}

/**
  * Created by simone on 26.12.16.
  */
case class RestAnimation(animation: Animation[TextureRegion],
                         override val orientation: EntityOrientation)
    extends EntityAnimation(animation, orientation) {
  override def draw(batch: Batch,
                    entity: MutableEntity with EntityWithAnimation): Unit = {

    val currentFrame = animation.getKeyFrame(0)
    val position = entity.getPosition
    position match {
      case Some(drawPosition) =>
        batch.draw(currentFrame,
                   drawPosition.x * Constants.TileWidth,
                   drawPosition.y * Constants.TileHeigth)
      case None =>
    }
  }
}
