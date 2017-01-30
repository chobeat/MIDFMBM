package org.anacletogames

import com.badlogic.gdx.maps.MapObject
import com.badlogic.gdx.maps.objects.{
  CircleMapObject,
  PolylineMapObject,
  RectangleMapObject
}
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell
import com.badlogic.gdx.maps.tiled.{TiledMap, TiledMapTileLayer}
import com.badlogic.gdx.math.{GridPoint2, Shape2D}
import org.anacletogames.actions.GridMovement
import org.anacletogames.maps._

import scala.collection.JavaConversions._
import scala.collection.breakOut

/**
  * Created by lara on 11/2/16.
  */
package object maps {

  implicit def GridPoint2ToRich(p: GridPoint2): GridPoint2Rich =
    new GridPoint2Rich(p)

  implicit def MapObjectToExtractor(m: MapObject): MapObjectWithExtractor =
    new MapObjectWithExtractor(m)

  implicit def TiledMap2Rich(m: TiledMap): TiledMapRich = new TiledMapRich(m)

}
class GridPoint2Rich(p: GridPoint2) {
  def dst(other: GridPoint2): Double =
    Math.sqrt(Math.pow(p.x - other.x, 2) + Math.pow(p.y - other.y, 2))

  def gridDst(other: GridPoint2) =
    GridMovement(other.x - p.x, other.y - p.y)
}
class MapObjectWithExtractor(m: MapObject) {
  def getShape: Shape2D = m match {
    case x: RectangleMapObject => x.getRectangle
    case x: CircleMapObject => x.getCircle
    case x: PolylineMapObject => x.getPolyline
  }

}

class TiledMapRich(m: TiledMap) {
  def getImpassableShapes = {
    for {
      obj <- m.getLayers.get(MapGenerator.IMPASSABLE_LAYER_NAME).getObjects
    } yield obj.getShape
  }

  def getImpassableTiles: Map[GridPoint2, Cell] = {
    val impassableLayer = Option(
      m.getLayers
        .get(MapGenerator.IMPASSABLE_LAYER_NAME)
        .asInstanceOf[TiledMapTileLayer])
    impassableLayer match {
      case Some(impLayer) =>
        (for {
          i <- 0 to impLayer.getWidth
          j <- 0 to impLayer.getHeight
          cell = impLayer.getCell(i, j) if cell != null
        } yield new GridPoint2(i, j) -> cell)(breakOut)

      case None => Map.empty
    }
  }
}
