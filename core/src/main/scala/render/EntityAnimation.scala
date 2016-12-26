package render

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.{Animation, Batch, TextureRegion}
import com.badlogic.gdx.math.Vector2
import org.anacletogames.entities._
import org.anacletogames.entities.assets.OrientedAnimation

/**
  * Created by simone on 26.12.16.
  */
abstract class EntityAnimation(animation: Animation) {

  def draw(batch: Batch, entity: Entity with EntityWithAnimation): Unit

  var statetime: Float = 0
  def updateStateTime():Unit={
    statetime+=Gdx.graphics.getDeltaTime
  }
  def drawAtPosition(batch: Batch, x: Float, y: Float) = {
    val currentFrame =
      animation.getKeyFrame(statetime)
    drawFrameAtPosition(batch, currentFrame, x, y)
  }

  def drawFrameAtPosition(batch: Batch,
                          frame: TextureRegion,
                          x: Float,
                          y: Float): Unit = {

    batch.draw(frame, x * Constants.TileWidth, y * Constants.TileHeigth)

  }

}
