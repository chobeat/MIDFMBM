package org.anacletogames.modes

import com.badlogic.gdx.graphics.{FPSLogger, GL20, OrthographicCamera}
import com.badlogic.gdx.{Gdx, _}
import render.WithDelta
import util.InputDefaultHandler

/**
  * Created by simone on 05.11.16.
  */
abstract class BaseScreen()
    extends Screen
    with InputProcessor
    with InputDefaultHandler
    with WithTiledMap
    with WithStage
      with WithDelta {

  var isPaused:Boolean=false
  val fpsLogger = new FPSLogger
  def renderContent():Unit
  /*override def create(): Unit = {*/

    val inputMulti = new InputMultiplexer()
    inputMulti.addProcessor(this)
    inputMulti.addProcessor(guiStage)
    Gdx.input.setInputProcessor(inputMulti)

  override def render(delta:Float): Unit = {

    updateDelta()
    fpsLogger.log()
    Gdx.gl.glClearColor(1, 0, 0, 1)
    Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA)
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
    tiledMapRenderer.setView(stage.getCamera.asInstanceOf[OrthographicCamera])
    tiledMapRenderer.render()
    renderContent()
    guiStage.draw()
  }



  val inputProcessor: PartialFunction[Int, Unit]

  override def keyDown(keycode: Int): Boolean = {
    super.keyDown(keycode)
    val processorWithCatchAll = inputProcessor orElse PartialFunction[Int,
                                                                      Unit] {
      case x: Int =>
    }
    processorWithCatchAll(keycode)
    false
  }

  override def hide(): Unit = {
    Gdx.input.setInputProcessor(null)
  }

  override def dispose(): Unit = {stage.dispose()}


  override def show(): Unit = {
    Gdx.input.setInputProcessor(this)
    render(0)
  }

  override def pause(): Unit = {}

  override def resume(): Unit = {}
}
