package org.anacletogames.gui

import com.badlogic.gdx.graphics.Pixmap.Format
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.{Color, Pixmap, Texture}
import com.badlogic.gdx.scenes.scene2d._
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle
import com.badlogic.gdx.scenes.scene2d.ui._
import com.badlogic.gdx.utils.Align
import org.anacletogames.entities.Entity
import org.anacletogames.modes.BattleScreen

/**
  * Created by simone on 27.12.16.
  */
trait WithBattleMapGUI { this: BattleScreen =>

  lazy val debugConsoleTable = new DebugConsoleTable(this)
  lazy val battleMapGUIBar = new BattleMapGUIBar(this)
  def toggleDebugMenu(): Unit = debugConsoleTable.toggle()
  def togglePause(): Unit = battleMapGUIBar.togglePause()

}

class DebugConsoleTable(battleMapRenderer: BattleScreen)
    extends ToggleableTable(battleMapRenderer) {

  def createTable: Table = {
    val table = WithBattleMapGUI.buildDebugMenuTable

    battleMapRenderer.gameGrid.getAllEntities.foreach(entity => {
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
        //println(entity.getPosition(gameGrid).get)
        false
      }
    })

    button
  }

}
