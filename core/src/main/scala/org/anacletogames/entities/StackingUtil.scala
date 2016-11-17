package org.anacletogames.entities

/**
  * Created by simone on 17.11.16.
  */
trait WithStackable{
  this:Entity=>
  val stackable=true
}

trait WithNonStackable{
  this:Entity=>
  val stackable=false
}
