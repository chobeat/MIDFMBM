package debug

import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer

/**
  * Created by simone on 06.11.16.
  */
trait WithTiledMap {

  var tiledMap: TiledMap = null

  var tiledMapRenderer: OrthogonalTiledMapRenderer = null

}
