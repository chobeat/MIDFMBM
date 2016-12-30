package org.anacletogames.maps

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile
import com.badlogic.gdx.maps.tiled.{TiledMap, TiledMapTileLayer}
import com.badlogic.gdx.math.GridPoint2
import org.anacletogames.maps.assets.{GrassTile, HoleTile, TestMapLoader}
import org.anacletogames.maps.objects.{MapGeneratorRandomRectElement, MapGeneratorRectElement}
import render.Constants

/**
  * Created by lara on 10/29/16.
  */
object MapGenerator {

  val tileWidth = Constants.TileWidth
  val tileHeight =  Constants.TileHeigth

  val IMPASSABLE_LAYER_NAME = "IMPASSABLE"

  def generateRandomMap(width: Int, height: Int): TiledMap = {

    val dirtLayer =
      new TiledMapTileLayer(width, height, tileWidth, tileHeight)
    LayerGenerator.fillWithTexture(dirtLayer, TestMapLoader.dirtTiles.Center)

    val impassableTileLayer =
      new TiledMapTileLayer(width, height, tileWidth, tileHeight)
    val impassableLayer =
      LayerGenerator.generateImpassableLayer(impassableTileLayer)
    impassableLayer.setName(IMPASSABLE_LAYER_NAME)

    layersToMap(List(dirtLayer, impassableLayer))

  }

  def generateDebugMap(width: Int, height: Int): TiledMap = {

    val dirtLayer =
      new TiledMapTileLayer(width, height, tileWidth, tileHeight)
    LayerGenerator.fillWithTexture(dirtLayer, TestMapLoader.dirtTiles.Center)

    val impassableTileLayer =
      new TiledMapTileLayer(width, height, tileWidth, tileHeight)
    val impassableLayer =
      LayerGenerator.generateDebugImpassableLayer(impassableTileLayer)
    impassableLayer.setName(IMPASSABLE_LAYER_NAME)

    layersToMap(List(dirtLayer, impassableLayer))

  }

  def generateEmptyMap(width: Int, height: Int): TiledMap = {
    val dirtLayer =
      new TiledMapTileLayer(width, height, tileWidth, tileHeight)
    LayerGenerator.fillWithTexture(dirtLayer, TestMapLoader.dirtTiles.Center)

    layersToMap(List(dirtLayer))
  }

  def layersToMap(layers: Seq[TiledMapTileLayer]): TiledMap = {
    val map = new TiledMap()
    val mapLayers = map.getLayers()
    layers.foreach(mapLayers.add)
    map
  }
}

object LayerGenerator {

  def fillWithTexture(layer: TiledMapTileLayer,
                      texture: TextureRegion): TiledMapTileLayer = {

    val dirtCentercell = new Cell()

    dirtCentercell.setTile(new StaticTiledMapTile(texture))

    for {
      i <- 0 to layer.getWidth
      j <- 0 to layer.getHeight
    } {
      layer.setCell(i, j, dirtCentercell)
    }
    layer
  }

  def generateImpassableLayer(layer: TiledMapTileLayer) = {

    for {
      _ <- 0 to 5
      element = new MapGeneratorRandomRectElement with HoleTile
    } yield element.placeRandomOnLayer(layer)

    for {
      _ <- 0 to 5
      element = new MapGeneratorRandomRectElement(6) with GrassTile
    } yield element.placeRandomOnLayer(layer)

    layer
  }

  def generateDebugImpassableLayer(layer: TiledMapTileLayer) = {

    val element = new MapGeneratorRectElement(4,2) with HoleTile
    element.placeOnLayerWithPosition(layer, new GridPoint2(3, 8 ))
    layer
  }
}
