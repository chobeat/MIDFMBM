package org.anacletogames.entities

import com.badlogic.gdx.graphics.{Color, Pixmap, Texture}
import com.badlogic.gdx.graphics.g2d.Sprite
import org.anacletogames.Entity

/**
  * Created by lara on 10/27/16.
  */
class MeleeAttack(target:Entity) extends Entity(target.getX,target.getY,0){

  private val pixmap = new Pixmap(25, 25, Pixmap.Format.RGBA8888)

  //Fill it red
  //Draw a circle about the middle
  pixmap.setColor(Color.BLUE)
  pixmap.drawCircle(pixmap.getWidth / 2 - 1, pixmap.getHeight / 2 - 1, pixmap.getWidth / 2 - 1)

  override val sprite = new Sprite(new Texture(pixmap))
  pixmap.dispose()


}
