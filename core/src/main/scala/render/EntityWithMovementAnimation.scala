package render

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.{Animation, Batch, TextureRegion}
import com.badlogic.gdx.math.{GridPoint2, Vector2}
import com.badlogic.gdx.utils.{Array => GdxArray}
import org.anacletogames.entities.assets.MaleBaseCharacterTexture
import org.anacletogames.entities.{Entity, EntityOrientation}

/**
  * Created by simone on 04.12.16.
  */
trait EntityWithMovementAnimation { this: Entity =>
  var animation: EntityAnimation = RestAnimation(MaleBaseCharacterTexture.upStandingBase)

  override def draw(batch: Batch, alpha: Float) = {
    this.animation.draw(batch, this)
  }
}





