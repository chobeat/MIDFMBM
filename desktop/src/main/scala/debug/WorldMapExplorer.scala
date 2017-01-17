package debug

import com.badlogic.gdx.backends.lwjgl.{LwjglApplication, LwjglApplicationConfiguration}
import org.anacletogames.modes.{BattleScreen, WorldMapScreen}

/**
  * Created by simone on 07.01.17.
  */
object WorldMapExplorer extends App {
  val cfg = new LwjglApplicationConfiguration
  cfg.title = "MIDFMBM"
  cfg.height = 480
  cfg.width = 800
  cfg.forceExit = false
  new LwjglApplication(new WorldMapRenderer , cfg)
}