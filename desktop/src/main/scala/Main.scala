package org.anacletogames

import com.badlogic.gdx.backends.lwjgl._
import org.anacletogames.maps.TestMapRender

object Main extends App {
    val cfg = new LwjglApplicationConfiguration
    cfg.title = "MIDFMBM"
    cfg.height = 480
    cfg.width = 800
    cfg.forceExit = false
    new LwjglApplication(new Midfmbm, cfg)
}

object TileMapRender extends App {
  val cfg = new LwjglApplicationConfiguration
  cfg.title = "MIDFMBM"
  cfg.height = 480
  cfg.width = 800
  cfg.forceExit = false
  new LwjglApplication(new TestMapRender, cfg)
}
