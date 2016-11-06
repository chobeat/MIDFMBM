package debug

import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import org.anacletogames.BattleMap

/**
  * Created by simone on 06.11.16.
  */
trait WithTiledMap {

  var map:TiledMap=null

  var tiledMapRenderer:OrthogonalTiledMapRenderer=null
}
