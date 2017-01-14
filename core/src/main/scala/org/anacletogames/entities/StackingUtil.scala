package org.anacletogames.entities

/**
  * Created by simone on 17.11.16.
  */
trait WithStackable{
  this:MutableEntity=>
  val stackable=true
}

trait WithNonStackable{
  this:MutableEntity=>
  val stackable=false
}
