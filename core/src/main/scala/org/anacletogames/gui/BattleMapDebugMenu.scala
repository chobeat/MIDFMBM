package org.anacletogames.gui

import java.io.File

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.files.FileHandle
import com.badlogic.gdx.graphics.Pixmap.Format
import com.badlogic.gdx.graphics.{Color, Pixmap, Texture}
import com.badlogic.gdx.graphics.g2d.{BitmapFont, TextureAtlas}
import com.badlogic.gdx.scenes.scene2d._
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle
import com.badlogic.gdx.scenes.scene2d.ui._
import com.badlogic.gdx.scenes.scene2d.utils.{Align, ChangeListener}
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent
import org.anacletogames.battle.BattleMap
import org.anacletogames.entities.Entity
import org.anacletogames.modes.BattleMapRenderer

/**
  * Created by simone on 27.12.16.
  */
trait BattleMapDebugMenu extends ToggleableTable{ this: BattleMapRenderer =>

  lazy val atlas = new TextureAtlas(Gdx.files.internal("GUI/uiskin.atlas"))

  def createTable:Table= {
        val table = BattleMapDebugMenu.buildDebugMenuTable

        battleMap.getAllEntities.foreach(entity => {
          val button = BattleMapDebugMenu.buildEntityButton(entity)
          table.add(button)
          table.row()
        })

    table
    }

}

object BattleMapDebugMenu {
  val skin = new Skin()

  val pixmap = new Pixmap(1, 1, Format.RGBA8888)
  pixmap.setColor(Color.WHITE)
  pixmap.fill()
  skin.add("white", new Texture(pixmap))
  skin.add("default", new BitmapFont())

  val textButtonStyle = new TextButtonStyle()
  textButtonStyle.up = skin.newDrawable("white", Color.DARK_GRAY)
  textButtonStyle.down = skin.newDrawable("white", Color.DARK_GRAY)
  textButtonStyle.checked = skin.newDrawable("white", Color.BLUE)
  textButtonStyle.over = skin.newDrawable("white", Color.LIGHT_GRAY)
  textButtonStyle.font = skin.getFont("default")
  skin.add("default", textButtonStyle)

  def buildDebugMenuTable: Table = {
    val table = new Table()
    table.setColor(Color.BLUE)
    table.setFillParent(true)
    table.setTouchable(Touchable.enabled)
    table.setSize(100, 100)
    table.align(Align.center | Align.top)
    table.debug()
    table
  }

  def buildEntityButton(entity: Entity): TextButton = {

    val button = new ButtonWithEntity(entity, textButtonStyle)
    button.addListener(new InputListener {
      override def touchDown(event: InputEvent,
                             x: Float,
                             y: Float,
                             pointer: Int,
                             button: Int): Boolean = {
        println(entity.getPosition.get)
        false
      }
    })

    button
  }
}
