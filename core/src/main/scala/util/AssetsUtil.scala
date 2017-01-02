package util

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.{Animation, TextureRegion}

import com.badlogic.gdx.utils.{Array => GdxArray}

/**
  * Created by simone on 31.12.16.
  */
object AssetsUtil {
  def readTextureFromFile(fileName: String,
                          rows: Int,
                          columns: Int): Array[Array[TextureRegion]] = {


    val texture =
      new Texture(Gdx.files.internal(fileName))
    TextureRegion
      .split(texture, texture.getWidth / rows, texture.getHeight / columns)

  }
  def animationFromFrameIndices(texture: Array[Array[TextureRegion]],
                                frameDuration: Float,
                                row: Int,
                                frames: Seq[Int]) = {
    val rowTexture = texture(row)
    val arr = new GdxArray[TextureRegion]()
    frames.foreach(frame => arr.add(rowTexture(frame)))
    new Animation(frameDuration, arr, Animation.PlayMode.LOOP)

  }
}
