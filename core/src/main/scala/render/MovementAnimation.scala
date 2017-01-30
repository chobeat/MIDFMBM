package render

import com.badlogic.gdx.graphics.g2d.{Animation, Batch, TextureRegion}
import com.badlogic.gdx.math.{GridPoint2, Vector2}
import org.anacletogames.entities.{Entity, EntityOrientation, EntityRenderer}

/**
  * Created by simone on 26.12.16.
  */
case class MovementAnimation(animation: Animation[TextureRegion],
                             override val orientation: EntityOrientation,
                             startingPosition: GridPoint2,
                             destination: GridPoint2,
                             override val previousStatetime: Float)
    extends EntityAnimation(animation, orientation, previousStatetime) {
  override def draw(batch: Batch,
                    renderer: EntityRenderer,
                    entity: Entity): MovementAnimation = {

    if (alpha < 1.0) {

      val startVector = new Vector2(startingPosition.x, startingPosition.y)
      val destVector = new Vector2(destination.x, destination.y)
      val drawPosition =
        startVector.lerp(destVector, alpha)

      drawAtPosition(batch, drawPosition.x, drawPosition.y)

    } else {
      val endMovementAnimation =
        renderer.getRestingAnimation(this.orientation)
      val newRenderer=renderer.copy(animation = endMovementAnimation)
      endMovementAnimation.draw(batch, newRenderer, entity)
    }
    this.copy(previousStatetime = statetime)
  }

}
