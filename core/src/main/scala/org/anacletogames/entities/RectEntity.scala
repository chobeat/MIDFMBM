package org.anacletogames.entities

import com.badlogic.gdx.graphics.{Color, Pixmap, Texture}
import com.badlogic.gdx.graphics.g2d.Sprite
import org.anacletogames.Entity

class RectEntity(x: Float, y: Float, speed: Float = 1) extends Entity(x, y, speed) {


  private val pixmap = new Pixmap(128, 128, Pixmap.Format.RGBA8888)

  //Fill it red
  //Draw a circle about the middle
  pixmap.setColor(Color.YELLOW)
  pixmap.drawCircle(pixmap.getWidth / 2 - 1, pixmap.getHeight / 2 - 1, pixmap.getWidth / 2 - 1)

  override val sprite = new Sprite(new Texture(pixmap))
  pixmap.dispose()

  this.setBounds(x, y, sprite.getWidth, sprite.getHeight)
}
