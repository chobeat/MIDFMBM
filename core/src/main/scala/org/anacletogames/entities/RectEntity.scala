package org.anacletogames.entities

import org.anacletogames.Entity

class RectEntity(x: Float, y: Float, speed:Float=1) extends Entity(x,y,speed){

  this.setBounds(x,y,sprite.getWidth,sprite.getHeight)
}
