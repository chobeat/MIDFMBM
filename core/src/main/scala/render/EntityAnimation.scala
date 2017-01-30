package render

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.{Animation, Batch, TextureRegion}
import org.anacletogames.battle.GameGrid
import org.anacletogames.entities._

/**
  * Created by simone on 26.12.16.
  */
abstract class EntityAnimation(animation: Animation[TextureRegion],
                               val orientation: EntityOrientation =
                                 NoOrientation,
                               val previousStatetime: Float) {

  lazy val statetime = {
    previousStatetime + Gdx.graphics.getDeltaTime
  }
  def draw(batch: Batch,
           renderer: EntityRenderer,
           entity: Entity): EntityAnimation

  def alpha = Constants.moveToCellTime * statetime

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
