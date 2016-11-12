package org.anacletogames.entities

import com.badlogic.gdx.graphics.{Color, Pixmap, Texture}
import com.badlogic.gdx.graphics.g2d.Sprite
import org.anacletogames.{BattleMap, Simulation}

class RectEntity(x: Float, y: Float, speed: Float = 1, context: BattleMap) extends Entity(x, y, speed,context: BattleMap) {


  private val pixmap = new Pixmap(128, 128, Pixmap.Format.RGBA8888)

  //Fill it red
  //Draw a circle about the middle
  pixmap.setColor(Color.YELLOW)
  pixmap.drawCircle(pixmap.getWidth / 2 - 1, pixmap.getHeight / 2 - 1, pixmap.getWidth / 2 - 1)
  pixmap.fill()

  override val sprite = new Sprite(new Texture(pixmap))
  pixmap.dispose()

  this.setBounds(x, y, sprite.getWidth, sprite.getHeight)
}
