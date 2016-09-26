package entities

import sgl._
import geometry._
import scene._
import util._

trait HumanComponent extends GraphicsProvider {
  this: GraphicsProvider =>


  class Human(var x: Double, var y: Double, var speed: Double) {
    def update(): Unit = {
      if(x<300)
        x+=3
      else
        x-=200

      render(getScreenCanvas)
    }

    def render(canvas: Canvas): Unit = {
      canvas.drawRect(x.toInt, y.toInt, 40, 40, defaultPaint.withColor(Color.Blue))
    }

  }

}
