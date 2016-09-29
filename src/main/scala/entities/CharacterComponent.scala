package entities


trait CharacterComponent extends StandardEntity {


  class Character(var x: Int, var y: Int, var speed: Int, var charOrientation:Orientation=South) {


    def updateOrientation(orientation:Orientation)=charOrientation=orientation

    def move(orientation: Orientation)=
     { updateOrientation(orientation)

      orientation match {
        case South => y += speed
        case North => y -= speed
        case East => x += speed
        case West => x -= speed
      }
    }
    var count:Int=0
    def update(): Unit = {
      /*count+=1
      if(count%50>25)
      move(East)
      else
      move(West)
*/
      render(getScreenCanvas)
    }

    def render(canvas: Canvas): Unit = {
     Character.tile(CharacterOrientation(charOrientation,Standing)).bitmapRegion.render(canvas,x,y)
      Character.tile(CharacterOrientation(charOrientation,RunningRightLeg)).bitmapRegion.render(canvas,x*2,y*2)
    }

  }

  object Character{
    lazy val tile = parseCharacterSprite("testsprite.png")
  }
}
