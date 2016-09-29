package sprites

import entities.CharacterOrientation
import sgl.{GraphicsProvider, WindowProvider}

trait SpriteHandler extends GraphicsProvider with WindowProvider{

  case class CharacterSprite(orientation:CharacterOrientation, bitmapRegion: BitmapRegion)
  def parseCharacterSprite(
                            resource: String): Map[CharacterOrientation, CharacterSprite]
}
