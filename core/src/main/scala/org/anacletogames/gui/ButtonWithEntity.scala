package org.anacletogames.gui

import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle
import com.badlogic.gdx.scenes.scene2d.ui.{Skin, TextButton}
import org.anacletogames.entities.MutableEntity

/**
  * Created by simone on 30.12.16.
  */
class ButtonWithEntity(val entity:MutableEntity, style:TextButtonStyle) extends TextButton(entity.getGameName,style){


}
