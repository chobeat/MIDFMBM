package render

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.{Animation, Batch, TextureRegion}
import com.badlogic.gdx.math.{GridPoint2, Vector2}
import com.badlogic.gdx.utils.{Array => GdxArray}
import org.anacletogames.entities.assets.{
  EntityTexture,
  MaleBaseCharacterTexture,
  MovementAnimatedTexture,
  OrientedAnimation
}
import org.anacletogames.entities._

/**
  * Created by simone on 04.12.16.
  */
trait MaleAnimatedTexture {

  val entityTextures = MaleBaseCharacterTexture
}

trait EntityWithAnimation { this: MutableEntity =>

  val entityTextures: MovementAnimatedTexture
  var animation: EntityAnimation = null
  lazy val defaultAnimation = RestAnimation(entityTextures.upStandingBase)

  override def draw(batch: Batch, alpha: Float) = {
    //Done this way because of an init problem with traits

    if(this.animation==null)
      animation=defaultAnimation

    this.animation.draw(batch, this)
  }

  def getRestingAnimation(direction: EntityOrientation): RestAnimation = {
    direction match {
      case LookingUp => RestAnimation(entityTextures.upStandingBase)
      case LookingLeft => RestAnimation(entityTextures.leftStandingBase)
      case LookingDown => RestAnimation(entityTextures.downStandingBase)
      case LookingRight => RestAnimation(entityTextures.rightStandingBase)

    }
  }
}
