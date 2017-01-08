package org.anacletogames.entities.assets

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.{Animation, TextureRegion}
import org.anacletogames.entities._
import render.{MovementAnimation, RestAnimation}
import util.AssetsUtil

/**
  * Created by simone on 04.12.16.
  */
object MaleBaseCharacterTexture extends MovementAnimatedTexture {

  lazy val texture: scala.Array[Array[TextureRegion]] =
    AssetsUtil.readTextureFromFile("BODY_male.png", 9, 4)

  val frameDuration = 0.2F

}

case class OrientedAnimation(animation: Animation,
                             direction: EntityOrientation)

trait EntityTexture{
  val frameDuration: Float
  val texture: Array[Array[TextureRegion]]

  val animationFromFrameIndices =
    AssetsUtil.animationFromFrameIndices(texture,frameDuration,_:Int,_:Seq[Int])
}

trait MovementAnimatedTexture extends EntityTexture{


  lazy val upStandingBase = animationFromFrameIndices(0, 0 until 1)
  lazy val leftStandingBase = animationFromFrameIndices(1, 0 until 1)
  lazy val downStandingBase = animationFromFrameIndices(2, 0 until 1)
  lazy val rightStandingBase = animationFromFrameIndices(3, 0 until 1)

  lazy val upWalkingBase =
    OrientedAnimation(animationFromFrameIndices(0, 1 to 8), LookingUp)
  lazy val leftWalkingBase =
    OrientedAnimation(animationFromFrameIndices(1, 1 to 8), LookingLeft)
  lazy val downWalkingBase =
    OrientedAnimation(animationFromFrameIndices(2, 1 to 8), LookingDown)
  lazy val rightWalkingBase =
    OrientedAnimation(animationFromFrameIndices(3, 1 to 8), LookingRight)

}
