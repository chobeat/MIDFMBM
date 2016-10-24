package org.anacletogames.entities

class RectEntity(x: Float, y: Float, speed:Float=1, sizeX:Int,sizeY:Int) extends Entity(x,y,speed){

  actor.setBounds(x,y,sizeX,sizeY)
}
