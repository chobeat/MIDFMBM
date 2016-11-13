package debug

import javafx.scene.input.KeyCode

import com.badlogic.gdx.graphics.{GL20, OrthographicCamera}
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.badlogic.gdx.{ApplicationAdapter, Gdx, Input, InputProcessor}
import org.anacletogames.maps.MapGenerator
import util.InputDefaultHandler

/**
  * Created by simone on 05.11.16.
  */
abstract class DebugRenderer(tiledMapWidth: Int, tiledMapHeigth: Int)
    extends ApplicationAdapter
    with InputProcessor
    with InputDefaultHandler
    with WithCamera
    with WithTiledMap {

  var w: Int = 0
  var h: Int = 0

  override def create(): Unit = {
    tiledMap = MapGenerator.generateRandomMap(tiledMapWidth, tiledMapHeigth)
    w = Gdx.graphics.getWidth()
    h = Gdx.graphics.getHeight()
    tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap)

    camera.setToOrtho(false, w, h);
    camera.update();
    Gdx.input.setInputProcessor(this)
  }

  override def render(): Unit = {
    Gdx.gl.glClearColor(1, 0, 0, 1)
    Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA)
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
    camera.update()
    tiledMapRenderer.setView(camera)
    tiledMapRenderer.render()
  }

  var inputProcessor: PartialFunction[Int, Unit] = { case x: Int => }

  override def keyUp(keycode: Int): Boolean = {
    super.keyUp(keycode)
    val processorWithCatchAll = inputProcessor orElse PartialFunction[Int,
                                                                      Unit] {
      case x: Int =>
    }
    processorWithCatchAll(keycode)
    false
  }

}
