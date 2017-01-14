package debug

import com.badlogic.gdx.Game
import org.anacletogames.modes.{BattleScreen, WorldMapScreen}

import scala.util.Random

/**
  * Created by simone on 09.01.17.
  */
class MainRenderer extends Game{
  val game:Game = this
  override def create(): Unit = {

    this.setScreen(new BattleScreen())

  }

  override def render(): Unit = {super.render()

  }
}
