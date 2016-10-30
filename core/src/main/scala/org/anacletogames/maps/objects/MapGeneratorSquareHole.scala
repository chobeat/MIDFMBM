package org.anacletogames.maps.objects

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell
import com.badlogic.gdx.maps.tiled.{TiledMapTileLayer, TiledMapTileSet}
import org.anacletogames.maps.assets.{TestMapLoader, Tile9Region}

import scala.util.Random

/**
  * Created by lara on 10/30/16.
  */

case class MapGeneratorElementPart(x:Int,y:Int,cell:Cell)
abstract class MapGeneratorElement{



  val parts:List[MapGeneratorElementPart]

  def canBePlaced(x: Int, y: Int,layer: TiledMapTileLayer): Boolean =
    !parts.exists{case MapGeneratorElementPart(xt,yt,_)=>layer.getCell(xt+x,yt+y) != null}

  def placeOnLayer(layer: TiledMapTileLayer): Unit = {
    val (x,y)=getRandomValidCoordinates(layer)
    parts.foreach { case MapGeneratorElementPart(xt, yt, t) => layer.setCell(x + xt, y + yt, t) }
  }
  def getRandomValidCoordinates(layer:TiledMapTileLayer):(Int,Int)= {
    val rx=Random.nextInt(layer.getWidth-2)+1

    val ry=Random.nextInt(layer.getHeight - 2)+1

    if(canBePlaced(rx,ry,layer))
      (rx,ry)
    else
      getRandomValidCoordinates(layer)
  }

}

abstract class MapGeneratorSquareElement extends MapGeneratorElement{

  def tiles: Tile9Region
  lazy val parts = List(
    MapGeneratorElementPart(0, 0, tiles.SWCell),
    MapGeneratorElementPart(1, 0, tiles.SECell),
    MapGeneratorElementPart(0, 1, tiles.NWCell),
    MapGeneratorElementPart(1, 1, tiles.NECell)
  )




}

class MapGeneratorSquareHole extends MapGeneratorSquareElement {
  override val tiles = TestMapLoader.holeTiles
}
