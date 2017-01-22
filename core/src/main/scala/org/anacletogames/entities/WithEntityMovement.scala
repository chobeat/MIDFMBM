package org.anacletogames.entities

import com.badlogic.gdx.graphics.g2d.{Animation, TextureRegion}
import com.badlogic.gdx.math.GridPoint2
import org.anacletogames.actions.GridMovement
import org.anacletogames.entities.assets.{
  MaleBaseCharacterTexture
}
import render.{EntityWithAnimation, MovementAnimation, RestAnimation}

/**
  * Created by simone on 04.12.16.
  */
trait WithEntityMovement { this: Entity with EntityWithAnimation =>


  def canIMoveThere(destination: GridPoint2): Boolean
}
