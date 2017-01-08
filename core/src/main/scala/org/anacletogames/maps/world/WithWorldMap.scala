package org.anacletogames.maps.world

import com.badlogic.gdx.maps.tiled.{TiledMap, TiledMapTileLayer, TmxMapLoader}

/**
  * Created by simone on 07.01.17.
  */
trait WithWorldMap {
  lazy val tiledMap: TiledMap =new TmxMapLoader().load("test.tmx")
  lazy val mapHeight= tiledMap.getLayers.get(0).asInstanceOf[TiledMapTileLayer].getHeight
  lazy val mapWidth= tiledMap.getLayers.get(0).asInstanceOf[TiledMapTileLayer].getWidth


}
