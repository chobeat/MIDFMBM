package org.anacletogames.entities.assets

import com.badlogic.gdx.graphics.g2d.{Animation, TextureRegion}
import org.anacletogames.entities._
import util.AssetsUtil

/**
  * Created by simone on 04.12.16.
  */
object MaleBaseCharacterTexture extends MovementAnimatedTexture {

  lazy val texture: scala.Array[Array[TextureRegion]] =
    AssetsUtil.readTextureFromFile("assets/BODY_male.png", 9, 4)

  val frameDuration = 0.2F

}

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
    animationFromFrameIndices(0, 1 to 8)
  lazy val leftWalkingBase =
    animationFromFrameIndices(1, 1 to 8)
  lazy val downWalkingBase =
    animationFromFrameIndices(2, 1 to 8)
  lazy val rightWalkingBase =
    animationFromFrameIndices(3, 1 to 8)

}
