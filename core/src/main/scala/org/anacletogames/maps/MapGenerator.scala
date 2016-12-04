package org.anacletogames.maps

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.{OrthographicCamera, Texture}
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.maps.objects.RectangleMapObject
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile
import com.badlogic.gdx.maps.tiled.{TiledMap, TiledMapTileLayer, TiledMapTileSet}
import org.anacletogames.maps.assets.{GrassTile, HoleTile, TestMapLoader, Tile9Region}
import org.anacletogames.maps.objects.{ MapGeneratorLineElement, MapGeneratorRectElement}

import scala.collection.JavaConversions._
import scala.util.Random

/**
  * Created by lara on 10/29/16.
  */
object MapGenerator {

  val tileWidth = 32
  val tileHeight = 32

  val IMPASSABLE_LAYER_NAME="IMPASSABLE"

  def generateRandomMap(width: Int, height: Int): TiledMap = {


    val dirtLayer =
      new TiledMapTileLayer(width, height, tileWidth, tileHeight)
    LayerGenerator.fillWithTexture(dirtLayer,TestMapLoader.dirtTiles.Center)

    val impassableTileLayer =
      new TiledMapTileLayer(width, height, tileWidth, tileHeight)
    val impassableLayer = LayerGenerator.generateImpassableLayer(impassableTileLayer)
    impassableLayer.setName(IMPASSABLE_LAYER_NAME)

    layersToMap(List(dirtLayer, impassableLayer))



  }

  def generateEmptyMap(width: Int, height: Int): TiledMap ={
    val dirtLayer =
      new TiledMapTileLayer(width, height, tileWidth, tileHeight)
    LayerGenerator.fillWithTexture(dirtLayer,TestMapLoader.dirtTiles.Center)

    layersToMap(List(dirtLayer))
  }


  def layersToMap(layers:Seq[TiledMapTileLayer]): TiledMap ={
    val map = new TiledMap()
    val mapLayers = map.getLayers()
    layers.foreach(mapLayers.add)
    map
  }
}



object LayerGenerator {

  def isEmptyCell(x:Int, y:Int, layer:TiledMapTileLayer)= {
    Option(layer.getCell(x, y)).isEmpty

  }


  def fillWithTexture(layer: TiledMapTileLayer, texture:TextureRegion): TiledMapTileLayer = {

    val dirtCentercell = new Cell()

    dirtCentercell.setTile(
      new StaticTiledMapTile(texture))

    for {
      i <- 0 to layer.getWidth
      j <- 0 to layer.getHeight
    } {
      layer.setCell(i, j, dirtCentercell)
    }
    layer
  }

  def generateImpassableLayer(layer: TiledMapTileLayer) = {

    for {_ <- 0 to 5
    element= new MapGeneratorRectElement with HoleTile
    } yield element.placeRandomOnLayer(layer)

    for {_ <- 0 to 5
         element= new MapGeneratorRectElement(6) with GrassTile
    } yield element.placeRandomOnLayer(layer)


    layer
  }
}


