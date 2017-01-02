package org.anacletogames.gui

import com.badlogic.gdx.Input
import org.anacletogames.modes.WithStage

/**
  * Created by simone on 26.12.16.
  */
trait BattleMapGUIControl { this: WithBattleMapGUI with WithStage=>
  def battleMapGUIKeyprocessor(): PartialFunction[Int, Unit] = {
    case Input.Keys.SLASH => toggleDebugMenu()
    case Input.Keys.SPACE => togglePause()

  }

}
