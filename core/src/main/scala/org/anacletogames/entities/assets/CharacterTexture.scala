package org.anacletogames.entities.assets

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.{Animation, TextureRegion}
import com.badlogic.gdx.utils.{Array => GdxArray}
import org.anacletogames.entities._
import render.{MovementAnimation, RestAnimation}

/**
  * Created by simone on 04.12.16.
  */
object MaleBaseCharacterTexture extends DefaultHumanWalkingTexture {

  lazy val texture: scala.Array[Array[TextureRegion]] = readTextureFromFile(
    "BODY_male.png")

  val frameDuration = 0.2F

}

case class OrientedAnimation(animation: Animation,
                             direction: EntityOrientation)

trait DefaultHumanWalkingTexture {
  val frameDuration: Float
  val texture: Array[Array[TextureRegion]]
  def readTextureFromFile(fileName: String): Array[Array[TextureRegion]] = {
    val texture =
      new Texture(Gdx.files.internal(fileName))
    TextureRegion
      .split(texture, texture.getWidth() / 9, texture.getHeight() / 4)

  }

  def animationFromFrameIndices(row: Int, frames: Seq[Int]) = {
    val rowTexture = texture(row)
    val arr = new GdxArray[TextureRegion]()
    frames.foreach(frame => arr.add(rowTexture(frame)))
    new Animation(frameDuration, arr, Animation.PlayMode.LOOP)

  }

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
