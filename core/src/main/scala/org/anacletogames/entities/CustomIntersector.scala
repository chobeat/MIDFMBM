package org.anacletogames.entities

import com.badlogic.gdx.math._

/**
  * Created by simone on 13.11.16.
  */
object CustomIntersector {

  def overlaps(a: Shape2D, b: Shape2D): Boolean = (a, b) match {
    case (a: Rectangle, b: Rectangle) => Intersector.overlaps(a, b)
    case (a: Circle, b: Circle) => Intersector.overlaps(a, b)
    case (a: Rectangle, b: Circle) => Intersector.overlaps(b, a)
    case (a: Circle,b:Rectangle)=> Intersector.overlaps(a,b)
    case _=>false
  }

  def contains(a:Shape2D,p:Vector2):Boolean=
    a match{
      case x:Rectangle=>x.contains(p)
      case x:Circle=>x.contains(p)
    }
}
