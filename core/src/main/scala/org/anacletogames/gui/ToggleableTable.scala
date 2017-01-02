package org.anacletogames.gui

import com.badlogic.gdx.scenes.scene2d.ui.Table
import org.anacletogames.modes.{BaseRenderer, BattleMapRenderer}

/**
  * Created by simone on 31.12.16.
  */
abstract class ToggleableTable(baseRenderer: BaseRenderer){
  var table: Option[Table] = None
  def createTable:Table
  def toggle() = {

    table match {
      case None =>
        val t = createTable
        baseRenderer.guiStage.addActor(t)

        table = Some(t)

      case Some(t) =>
        t.remove()
        table = None
    }
  }
}
