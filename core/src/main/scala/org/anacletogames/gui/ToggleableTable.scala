package org.anacletogames.gui

import com.badlogic.gdx.scenes.scene2d.ui.Table
import org.anacletogames.modes.BattleMapRenderer

/**
  * Created by simone on 31.12.16.
  */
trait ToggleableTable { this: BattleMapRenderer =>
  var table: Option[Table] = None
  def createTable: () => Table
  def toggle() = {

    table match {
      case None =>
        val t = createTable()
        guiStage.addActor(t)

        table = Some(t)

      case Some(t) =>
        t.remove()
        table = None
    }
  }
}
