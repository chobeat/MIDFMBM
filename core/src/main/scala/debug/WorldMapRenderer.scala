package debug

import akka.actor.{ActorRef, ActorSystem}
import com.badlogic.gdx.{Game, Screen}
import org.anacletogames.modes.WorldMapScreen

/**
  * Created by simone on 09.01.17.
  */
class WorldMapRenderer(implicit system:ActorSystem) extends Game {
  override def create(): Unit = {

    this.setScreen(WorldMapScreen.debugWorldMapScreen)

  }

  override def render(): Unit = { super.render()}
}
