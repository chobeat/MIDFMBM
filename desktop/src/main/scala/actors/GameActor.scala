package actors

import akka.actor.{Actor, Props}
import com.badlogic.gdx.backends.lwjgl.{LwjglApplication, LwjglApplicationConfiguration}
import debug.WorldMapRenderer
import org.anacletogames.actors.RendererActor
case class CreateGame(config:LwjglApplicationConfiguration) {
  override def equals(that: Any) = config == that
}

class GameActor extends Actor{

  val rendererActor = context.actorOf(Props[RendererActor].withDispatcher("single-thread-dispatcher"),"renderer")
  override def receive={
    case CreateGame(cfg)=>{

      new LwjglApplication(new WorldMapRenderer(rendererActor) , cfg)
    }
  }

}
