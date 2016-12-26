package org.anacletogames.maps.objects

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell
import com.badlogic.gdx.maps.tiled.{TiledMapTileLayer, TiledMapTileSet}
import org.anacletogames.maps.assets.{TestMapLoader, Tile9Region}

import scala.util.Random

abstract class MapGeneratorRandomRectElement(maxSize: Int = 3)
    extends MapGeneratorRectElement(
      MapGeneratorRectElement.randomSize(maxSize),
      MapGeneratorRectElement.randomSize(maxSize))

object MapGeneratorRectElement {
  def randomSize(maxSize: Int) =
    (Math.abs(Random.nextGaussian()) * (maxSize + 1)).toInt + 1
}

abstract class MapGeneratorRectElement(width: Int, height: Int)
    extends MapGeneratorElement {

  def tiles: Tile9Region

  lazy val corners = List(
    MapGeneratorElementPart(0, 0, tiles.SWCell),
    MapGeneratorElementPart(width, 0, tiles.SECell),
    MapGeneratorElementPart(0, height, tiles.NWCell),
    MapGeneratorElementPart(width, height, tiles.NECell)
  )

  lazy val interior = for {
    i <- 1 until width
    j <- 1 until height
  } yield MapGeneratorElementPart(i, j, tiles.CenterCell)

  lazy val infBorder = for {
    i <- 1 until width
  } yield MapGeneratorElementPart(i, 0, tiles.SCell)

  lazy val supBorder = for {
    i <- 1 until width
  } yield MapGeneratorElementPart(i, height, tiles.NCell)

  lazy val leftBorder = for {
    i <- 1 until height
  } yield MapGeneratorElementPart(0, i, tiles.WCell)

  lazy val rightBorder = for {
    i <- 1 until height
  } yield MapGeneratorElementPart(width, i, tiles.ECell)

  lazy val parts = corners ++ interior ++ infBorder ++ supBorder ++ leftBorder ++ rightBorder

}
