package org.anacletogames.modes

import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer

/**
  * Created by simone on 06.11.16.
  */
trait WithTiledMap { this: BaseScreen =>

  val tiledMap: TiledMap
  lazy val tiledMapRenderer: OrthogonalTiledMapRenderer =
    new OrthogonalTiledMapRenderer(tiledMap)

}
