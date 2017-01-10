package debug

import com.badlogic.gdx.backends.lwjgl.{LwjglApplication, LwjglApplicationConfiguration}
import org.anacletogames.modes.BattleMapScreen

/**
  * Created by simone on 06.11.16.
  */
object CollisionMapExplorer extends App {
  val cfg = new LwjglApplicationConfiguration
  cfg.title = "MIDFMBM"
  cfg.height = 480
  cfg.width = 800
  cfg.forceExit = false
  new LwjglApplication(new MainRenderer, cfg)
}
