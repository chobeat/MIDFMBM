package debug

import actors.{CreateGame, GameActor}
import akka.actor.{ActorSystem, Props}
import com.badlogic.gdx.backends.lwjgl.{LwjglApplication, LwjglApplicationConfiguration}
import com.sun.org.apache.xml.internal.utils.ThreadControllerWrapper.ThreadController
import com.typesafe.config.{Config, ConfigFactory}
import debug.WorldMapExplorer.systemConfig
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

  val systemConfig = ConfigFactory.parseString(
    """
 |     single-thread-dispatcher {
 |  type = Dispatcher
 |executor = "fork-join-executor"
 |  fork-join-executor {
 |    parallelism-min = 1
 |    parallelism-factor = 1
 |    parallelism-max = 1
 |  }
 |  throughput = 1
 |}
    """.stripMargin)
  val gameActor = GameActorSystem.system.actorOf(Props[GameActor].withDispatcher("single-thread-dispatcher"), "GameActor")

  gameActor ! CreateGame(cfg)
}

object GameActorSystem{
  val system = ActorSystem("Game",systemConfig)

}