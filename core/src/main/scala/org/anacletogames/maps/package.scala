package org.anacletogames

import com.badlogic.gdx.math.GridPoint2

/**
  * Created by lara on 11/2/16.
  */
package object maps {

  class GridPoint2Rich(p:GridPoint2){
    def dst(other:GridPoint2):Double= Math.sqrt(Math.pow(p.x-other.x,2)+Math.pow(p.y-other.y,2))
  }

  implicit def GridPoint2ToRich(p:GridPoint2)=new GridPoint2Rich(p)
}
