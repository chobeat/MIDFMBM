package org.anacletogames.gui

import com.badlogic.gdx.Input
import org.anacletogames.battle.BattleMap
import org.anacletogames.modes.WithStage

/**
  * Created by simone on 26.12.16.
  */
trait BattleMapGUIControl {
  this: BattleMapDebugMenu with WithStage=>
  def battleMapGUIKeyprocessor():PartialFunction[Int,Unit]= {
    case Input.Keys.SLASH => openDebugMenu()
  }

}
