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
trait WithBattleMapGUI { this: BattleMapRenderer =>

  lazy val debugConsoleTable = new DebugConsoleTable(this)
  lazy val battleMapGUIBar = new BattleMapGUIBar(this)
  def toggleDebugMenu(): Unit = debugConsoleTable.toggle()
  def togglePause(): Unit = battleMapGUIBar.togglePause()

}

class DebugConsoleTable(battleMapRenderer: BattleMapRenderer)
    extends ToggleableTable(battleMapRenderer) {
  def createTable: Table = {
    val table = WithBattleMapGUI.buildDebugMenuTable

    battleMapRenderer.battleMap.getAllEntities.foreach(entity => {
      val button = WithBattleMapGUI.buildEntityButton(entity)
      table.add(button)
      table.row()
    })

    table
  }
}

object WithBattleMapGUI {
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