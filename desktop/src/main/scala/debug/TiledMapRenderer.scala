package debug

import com.badlogic.gdx.backends.lwjgl.{LwjglApplication, LwjglApplicationConfiguration}

/**
  * Created by simone on 06.11.16.
  */

object TiledMapRenderer extends App {
  val cfg = new LwjglApplicationConfiguration
  cfg.title = "MIDFMBM"
  cfg.height = 480
  cfg.width = 800
  cfg.forceExit = false
  new LwjglApplication(new TestMapRender, cfg)
}
