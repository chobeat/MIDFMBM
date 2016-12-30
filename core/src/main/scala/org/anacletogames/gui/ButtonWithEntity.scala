package org.anacletogames.gui

import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle
import com.badlogic.gdx.scenes.scene2d.ui.{Skin, TextButton}
import org.anacletogames.entities.Entity

/**
  * Created by simone on 30.12.16.
  */
class ButtonWithEntity(val entity:Entity, style:TextButtonStyle) extends TextButton(entity.getGameName,style){


}
