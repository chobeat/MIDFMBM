package org.anacletogames.maps

import com.badlogic.gdx.graphics.{GL20, OrthographicCamera}
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.badlogic.gdx._
import util.InputDefaultHandler

/**
  * Created by lara on 10/29/16.
  */
class TestMapRender extends ApplicationAdapter with InputProcessor with InputDefaultHandler{
  val camera = new OrthographicCamera();

  var w:Int=0
  var h:Int=0
  var  map:TiledMap=null
  var tiledMapRenderer:OrthogonalTiledMapRenderer=null

  override def create(): Unit = {
    map=  MapGenerator.generateRandomMap(40,40)
    w = Gdx.graphics.getWidth()
    h = Gdx.graphics.getHeight()
    tiledMapRenderer= new OrthogonalTiledMapRenderer(map)

    camera.setToOrtho(false,w,h);
    camera.update();
    Gdx.input.setInputProcessor(this)
  }

  override def render(): Unit = {
    Gdx.gl.glClearColor(1, 0, 0, 1);
    Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    camera.update();
    tiledMapRenderer.setView(camera);
    tiledMapRenderer.render();
  }


  override def keyUp(keycode:Int):Boolean= {
    if(keycode == Input.Keys.LEFT)
      camera.translate(-32,0)
    if(keycode == Input.Keys.RIGHT)
      camera.translate(32,0)
    if(keycode == Input.Keys.UP)
      camera.translate(0,-32)
    if(keycode == Input.Keys.DOWN)
      camera.translate(0,32)
    if(keycode == Input.Keys.NUM_1)
      map.getLayers().get(0).setVisible(!map.getLayers().get(0).isVisible())
    if(keycode == Input.Keys.NUM_2)
      map.getLayers().get(1).setVisible(!map.getLayers().get(1).isVisible())
    false
  }


}
