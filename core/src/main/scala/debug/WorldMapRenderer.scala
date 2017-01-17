package debug

import com.badlogic.gdx.Game
import org.anacletogames.modes.{BattleScreen, WorldMapScreen}

/**
  * Created by simone on 09.01.17.
  */
class WorldMapRenderer extends Game {
  val game: Game = this
  override def create(): Unit = {

    this.setScreen(WorldMapScreen.debugWorldMapScreen)

  }

  override def render(): Unit = { super.render() }
}
