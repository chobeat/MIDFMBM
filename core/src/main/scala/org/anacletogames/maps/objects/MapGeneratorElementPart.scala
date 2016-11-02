package org.anacletogames.maps.objects

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell
import com.badlogic.gdx.math.GridPoint2

/**
  * Created by lara on 10/30/16.
  */
case class MapGeneratorElementPart(position: GridPoint2, cell: Cell) {


  val x=position.x
  val y=position.y
}

object MapGeneratorElementPart{
  def apply(x: Int, y: Int, cell: Cell)=new MapGeneratorElementPart(new GridPoint2(x, y), cell)
}
