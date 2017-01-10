package debug

import com.badlogic.gdx.Game
import org.anacletogames.modes.{BattleMapScreen, WorldMapScreen}

/**
  * Created by simone on 09.01.17.
  */
class WorldMapRenderer extends Game{
  val game:Game = this
  override def create(): Unit = {

    this.setScreen(new WorldMapScreen())

  }

  override def render(): Unit = {super.render()

  }
}
