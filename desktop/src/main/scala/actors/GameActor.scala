package actors

import akka.actor.{Actor, ActorSystem, Props}
import com.badlogic.gdx.Game
import com.badlogic.gdx.backends.lwjgl.{LwjglApplication, LwjglApplicationConfiguration}
import debug.WorldMapRenderer
case class CreateGame(config:LwjglApplicationConfiguration) {
  override def equals(that: Any) = config == that
}


class GameActor extends Actor{
  var game:Game = null
  override def receive={
    case CreateGame(cfg)=>{

      new LwjglApplication(new WorldMapRenderer()(context.system), cfg)
    }

  }

}
