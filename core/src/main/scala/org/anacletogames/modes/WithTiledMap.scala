package org.anacletogames.modes

import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import org.anacletogames.maps.MapGenerator

/**
  * Created by simone on 06.11.16.
  */
trait WithTiledMap { this: BaseRenderer =>

  val mapWidth: Int
  val mapHeight: Int
  lazy val tiledMap: TiledMap = mapGenerator(mapWidth, mapHeight)
  val mapGenerator: (Int, Int) => TiledMap
  lazy val tiledMapRenderer: OrthogonalTiledMapRenderer =
    new OrthogonalTiledMapRenderer(tiledMap)

}
