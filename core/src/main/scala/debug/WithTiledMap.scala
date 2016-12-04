package debug

import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import org.anacletogames.battle.BattleMap
import org.anacletogames.maps.MapGenerator

/**
  * Created by simone on 06.11.16.
  */
trait WithTiledMap {
  this:{def tiledMapWidth:Int
    def tiledMapHeigth:Int}=>
  var tiledMap: TiledMap = null

  var tiledMapRenderer: OrthogonalTiledMapRenderer = null

  def create()={

    tiledMap = MapGenerator.generateRandomMap(tiledMapWidth, tiledMapHeigth)

    tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap)
  }
}


trait WithTiledEmptyMap extends WithTiledMap{
  this:{def tiledMapWidth:Int
    def tiledMapHeigth:Int}=>

  override def create()={

    tiledMap = MapGenerator.generateEmptyMap(tiledMapWidth,tiledMapHeigth)

    tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap)
  }
}