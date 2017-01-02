package org.anacletogames.gui

import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.{Button, ImageButton, Table}
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import org.anacletogames.gui.assets.PauseButtonAsset
import org.anacletogames.modes.{BaseRenderer, BattleMapRenderer}

import scala.collection.JavaConversions._

/**
  * Created by simone on 31.12.16.
  */
class BattleMapGUIBar(bmRenderer: BattleMapRenderer) extends Table {

  val pauseButton = BattleMapGUIBar.createPauseButton(bmRenderer)
  val playButton = BattleMapGUIBar.createPlayButton(bmRenderer)
  this.addActor(playButton)
  this.addActor(pauseButton)
  pauseButton.setVisible(false)

  def togglePause() = {
    bmRenderer.isPaused = !bmRenderer.isPaused
    pauseButton.setVisible(!pauseButton.isVisible)
    playButton.setVisible(!playButton.isVisible)
  }

}

object BattleMapGUIBar {

  def togglePauseButtonListener(bmRenderer: BattleMapRenderer) = {
    new ClickListener() {
      override def clicked(event: InputEvent, x: Float, y: Float): Unit = {
        super.clicked(event, x, y)
        bmRenderer.togglePause()
      }
    }

  }
  def createPauseButton(bmRenderer: BattleMapRenderer): ImageButton = {
    val button =
      new ImageButton(PauseButtonAsset.pauseImage, PauseButtonAsset.pauseImage)
    button.addListener(togglePauseButtonListener(bmRenderer))
    button
  }
  def createPlayButton(bmRenderer: BattleMapRenderer): ImageButton = {
    val button =
      new ImageButton(PauseButtonAsset.playImage, PauseButtonAsset.playImage)
    button.addListener(togglePauseButtonListener(bmRenderer))
    button
  }

}
