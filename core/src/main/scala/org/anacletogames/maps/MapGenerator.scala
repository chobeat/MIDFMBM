package org.anacletogames.maps

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.{OrthographicCamera, Texture}
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.maps.objects.RectangleMapObject
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile
import com.badlogic.gdx.maps.tiled.{TiledMap, TiledMapTileLayer, TiledMapTileSet}
import org.anacletogames.maps.assets.{TestMapLoader, Tile9Region}
import org.anacletogames.maps.objects.MapGeneratorSquareHole

import scala.collection.JavaConversions._
import scala.util.Random

/**
  * Created by lara on 10/29/16.
  */
object MapGenerator {

  def generateRandomMap(width: Int, height: Int): TiledMap = {
    val genMap = new TiledMap()
    val layers = genMap.getLayers()

    val tileWidth = 32
    val tileHeight = 32

    val walkableLayer =
      new TiledMapTileLayer(width, height, tileWidth, tileHeight)
    val impassableLayer =
      new TiledMapTileLayer(width, height, tileWidth, tileHeight)
    val dirtLayer = LayerGenerator.generateBackgroundLayer(walkableLayer)
    val holeLayer = LayerGenerator.generate4HoleLayer(impassableLayer)

    layers.add(dirtLayer)
    layers.add(holeLayer)

    genMap
  }
}



object LayerGenerator {

  def isEmptyCell(x:Int, y:Int, layer:TiledMapTileLayer)= {
    Option(layer.getCell(x, y)).isEmpty

  }


  def generateBackgroundLayer(layer: TiledMapTileLayer) = {

    val dirtCentercell = new Cell()

    dirtCentercell.setTile(
      new StaticTiledMapTile(TestMapLoader.dirtTiles.Center))

    for {
      i <- 0 to layer.getWidth
      j <- 0 to layer.getHeight
    } {
      layer.setCell(i, j, dirtCentercell)
    }
    layer
  }

  def generate4HoleLayer(layer: TiledMapTileLayer) = {
    val holeTiles = TestMapLoader.holeTiles


    val res=for {_ <- 0 to 55
    element= new MapGeneratorSquareHole()
    } yield element.placeOnLayer(layer)

    layer
  }
}


