package org.anacletogames.maps.assets

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture

/**
  * Created by lara on 10/30/16.
  */
object TestMapLoader {

  lazy val texture = new Texture(Gdx.files.internal("base_out_atlas.png"))
  val dirtTiles = new Tile9Region(texture, 480, 64, 32)
  val grassTiles = new Tile9Region(texture, 672, 64, 32)
  val holeTiles = new Tile9Region(texture, 764, 64, 32)

}
