package render

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.{Animation, Batch}
import com.badlogic.gdx.math.{GridPoint2, Vector2}
import org.anacletogames.entities.assets.OrientedAnimation
import org.anacletogames.entities.{MutableEntity, EntityOrientation}

/**
  * Created by simone on 26.12.16.
  */
case class MovementAnimation(orientedAnimation: OrientedAnimation,
                             startingPosition: GridPoint2,
                             destination: GridPoint2)
    extends EntityAnimation(orientedAnimation.animation) {

  override def draw(batch: Batch,
                    entity: MutableEntity with EntityWithAnimation): Unit = {

    val alpha = Constants.moveToCellTime * statetime
    if (alpha < 1.0) {

      updateStateTime()

      val startVector = new Vector2(startingPosition.x, startingPosition.y)
      val destVector = new Vector2(destination.x, destination.y)
      val drawPosition =
        startVector.lerp(destVector, alpha)

      drawAtPosition(batch, drawPosition.x, drawPosition.y)

    } else {
      val endMovementAnimation =
        entity.getRestingAnimation(this.orientedAnimation.direction)
      entity.animation = endMovementAnimation
      endMovementAnimation.draw(batch, entity)
    }
  }

}
