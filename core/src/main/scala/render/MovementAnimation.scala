package render

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.{Animation, Batch}
import com.badlogic.gdx.math.{GridPoint2, Vector2}
import org.anacletogames.entities.{Entity, EntityOrientation}

/**
  * Created by simone on 26.12.16.
  */
case class MovementAnimation(animation: Animation,
                             startingPosition:GridPoint2,
                             destination:GridPoint2)
    extends EntityAnimation(animation) {

  var statetime: Float = 0

  override def draw(batch: Batch, entity: Entity): Unit = {
    statetime += Gdx.graphics.getDeltaTime
    if (!startingPosition.equals(destination))

    {

        val alpha = Math.min(Constants.moveToCellTime * statetime, 1)
        val currentFrame =
          animation.getKeyFrame(statetime)
        val startVector=new Vector2(startingPosition.x,startingPosition.y)
        val destVector=new Vector2(destination.x,destination.y)
        val drawPosition =
          startVector.lerp(destVector, alpha)
        batch.draw(currentFrame,
                   drawPosition.x * Constants.TileWidth,
                   drawPosition.y * Constants.TileHeigth)

    }}


}
