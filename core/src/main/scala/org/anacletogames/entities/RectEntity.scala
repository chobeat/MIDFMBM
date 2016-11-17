package org.anacletogames.entities

import com.badlogic.gdx.graphics.{Color, Pixmap, Texture}
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.math.GridPoint2
import org.anacletogames.Simulation
import org.anacletogames.battle.BattleMap

abstract class RectEntity(speed: Int = 1, battleMap: BattleMap) extends SingleTileEntity(speed,battleMap) {


  private val pixmap = new Pixmap(128, 128, Pixmap.Format.RGBA8888)

  //Fill it red
  //Draw a circle about the middle
  pixmap.setColor(Color.YELLOW)
  pixmap.drawCircle(pixmap.getWidth / 2 - 1, pixmap.getHeight / 2 - 1, pixmap.getWidth / 2 - 1)
  pixmap.fill()

  override val sprite = new Sprite(new Texture(pixmap))
  pixmap.dispose()

}
