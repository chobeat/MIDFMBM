package org.anacletogames.gui.assets

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.{Color, Pixmap, Texture}
import com.badlogic.gdx.graphics.Pixmap.Format
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.utils.{Drawable, TextureRegionDrawable}
import util.AssetsUtil

/**
  * Created by simone on 31.12.16.
  */
object PauseButtonAsset {

  Gdx.files.internal(".").list().foreach(println)

  /*TODO:replace with real asset
  lazy val texture: scala.Array[Array[TextureRegion]] =
    AssetsUtil.readTextureFromFile("play_pause.svg", 1, 2)

  lazy val pauseImage: Drawable = new TextureRegionDrawable(texture(0)(0))
  lazy val playImage: Drawable = new TextureRegionDrawable(texture(0)(1))
   */
  val pausePixMap = new Pixmap(100, 100, Format.RGB888)
  pausePixMap.setColor(Color.BLACK)
  pausePixMap.fill()
  pausePixMap.setColor(Color.WHITE)
  pausePixMap.fillRectangle(10, 10, 30, 80)
  pausePixMap.fillRectangle(60, 10, 30, 80)


  lazy val pauseImage: Drawable = new TextureRegionDrawable(
    new TextureRegion(new Texture(pausePixMap)))

  val playPixmap= new Pixmap(100,100,Format.RGB888)
  playPixmap.setColor(Color.BLACK)
  playPixmap.fill()
  playPixmap.setColor(Color.WHITE)
  playPixmap.fillTriangle(10,10,10,70,80,40)

  lazy val playImage: Drawable = new TextureRegionDrawable(
    new TextureRegion(new Texture(playPixmap)))


}
