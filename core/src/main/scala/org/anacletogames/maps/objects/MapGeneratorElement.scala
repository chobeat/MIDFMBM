package org.anacletogames.maps.objects

import com.badlogic.gdx.maps.objects.RectangleMapObject
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell
import com.badlogic.gdx.math.GridPoint2

import scala.util.Random

/**
  * Created by lara on 10/31/16.
  */
abstract class MapGeneratorElement {
  val MAX_RANDOM_RETRIES = 10

  val parts: List[MapGeneratorElementPart]

  def canBePlaced(x: Int, y: Int, layer: TiledMapTileLayer): Boolean =
    !parts.exists {
      case MapGeneratorElementPart(p, _) =>
        layer.getCell(p.x + x, p.y + y) != null
    }

  def placeRandomOnLayer(layer: TiledMapTileLayer): Unit = {

    getRandomValidCoordinates(layer).foreach {
      case randomPos: GridPoint2 =>
        parts.foreach {
          case MapGeneratorElementPart(p, t) =>
            placeOnLayer(layer, randomPos.x + p.x, randomPos.y + p.y, t)
        }
    }

  }

  def placeOnLayer(layer: TiledMapTileLayer, x: Int, y: Int, t: Cell) = {
    val mo = layer.getObjects
    mo.add(
      new RectangleMapObject(x * layer.getTileWidth,
                             y * layer.getTileHeight,
                             layer.getTileWidth,
                             layer.getTileHeight))
    layer.setCell(x, y, t)
  }

  def placeOnLayerAbs(layer: TiledMapTileLayer, position: GridPoint2) = {
    parts.foreach {
      case MapGeneratorElementPart(p, t) => placeOnLayer(layer, p.x, p.y, t)
    }
  }
  def getRandomValidCoordinates(layer: TiledMapTileLayer,
                                retry: Int = 0): Option[GridPoint2] = {
    if (retry >= MAX_RANDOM_RETRIES)
      None
    else {
      val rx = Random.nextInt(layer.getWidth - 2) + 1

      val ry = Random.nextInt(layer.getHeight - 2) + 1

      if (canBePlaced(rx, ry, layer))
        Some(new GridPoint2(rx, ry))
      else
        getRandomValidCoordinates(layer, retry + 1)
    }
  }

}
