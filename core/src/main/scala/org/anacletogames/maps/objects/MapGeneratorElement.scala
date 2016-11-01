package org.anacletogames.maps.objects

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer

import scala.util.Random

/**
  * Created by lara on 10/31/16.
  */
abstract class MapGeneratorElement{
  val MAX_RANDOM_RETRIES=10


  val parts:List[MapGeneratorElementPart]

  def canBePlaced(x: Int, y: Int,layer: TiledMapTileLayer): Boolean =
    !parts.exists{case MapGeneratorElementPart(xt,yt,_)=>layer.getCell(xt+x,yt+y) != null}

  def placeOnLayer(layer: TiledMapTileLayer): Unit = {
    getRandomValidCoordinates(layer).foreach{case (x,y)=>
    parts.foreach { case MapGeneratorElementPart(xt, yt, t) => layer.setCell(x + xt, y + yt, t) }
    }
  }
  def getRandomValidCoordinates(layer:TiledMapTileLayer, retry:Int = 0):Option[(Int,Int)]= {
    if(retry>=MAX_RANDOM_RETRIES)
      None
    else{
    val rx=Random.nextInt(layer.getWidth-2)+1

    val ry=Random.nextInt(layer.getHeight - 2)+1

    if(canBePlaced(rx,ry,layer))
      Some((rx,ry))
    else
      getRandomValidCoordinates(layer,retry+1)
  }}

}
