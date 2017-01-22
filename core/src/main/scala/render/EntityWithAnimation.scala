package render

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.utils.{Array => GdxArray}
import org.anacletogames.entities._
import org.anacletogames.entities.assets.{
  MaleBaseCharacterTexture,
  MovementAnimatedTexture
}

/**
  * Created by simone on 04.12.16.
  */
trait MaleAnimatedTexture {

  val entityTextures: MaleBaseCharacterTexture.type = MaleBaseCharacterTexture
}