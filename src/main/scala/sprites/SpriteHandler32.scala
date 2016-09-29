package sprites

import entities._

import scala.collection.breakOut

trait SpriteHandler32 {
  this: SpriteHandler =>

  val spriteOrientation = Map[Int, Orientation](
      0 -> South,
      1 -> West,
      2 -> East,
      3 -> North
  )

  val movements = Map(0 -> RunningRightLeg, 1 -> Standing, 2 -> RunningLeftLeg)

  val padding = 4

  override def parseCharacterSprite(
      resource: String): Map[CharacterOrientation, CharacterSprite] = {
    val bitmap = loadImageFromResource(resource)

    (for {
      x <- 0 to 2
      y <- 0 to 3
    } yield {
      val orientation =
        CharacterOrientation(spriteOrientation(y), movements(x))
      orientation -> CharacterSprite(
          orientation,
          new BitmapRegion(bitmap, x * 32 + 4 * x, y * 32, 32, 32))
    })(breakOut)
  }
}
