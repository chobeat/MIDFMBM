package org.anacletogames.maps.objects

import com.badlogic.gdx.maps.tiled.{TiledMapTileLayer, TiledMapTileSet}
import com.badlogic.gdx.math.GridPoint2
import org.anacletogames.maps.assets.Tile9Region
import org.anacletogames.maps._
import scala.collection.JavaConversions._
import scala.util.Random
import Directions._

abstract class MapGeneratorLineElement(layers: List[TiledMapTileLayer],
                                       maxSize: Int = 3)
    extends MapGeneratorElement {

  def tiles: Tile9Region
  val width: Int = (Math.abs(Random.nextGaussian()) * (maxSize + 1)).toInt + 1

  def generateRandomPath(start: GridPoint2,
                         arrival: GridPoint2,
                         randomness: Int): List[GridPoint2] = {
    require(randomness <= 100 && randomness > 0)

    def isOccupied(p: GridPoint2) =
      layers.exists(l => {
        l.getCell(p.x, p.y) != null
      })

    def isValid(p: GridPoint2) = {
      val width = layers.head.getWidth
      val height = layers.head.getHeight
      p.x >= 0 && p.y >= 0 && p.x < width && p.y < width && p.y < height
    }

    def generateSup(visited: List[GridPoint2],
                    offCount: Int = 0): List[GridPoint2] = {

      def nextStep(curr: GridPoint2, goOff: Boolean): Option[GridPoint2] = {

        val directions=Set(N, W, S, E)
        val availableDestionations: Set[GridPoint2] =
          directions.map {
            case (x, y) => new GridPoint2(x + curr.x, y + curr.y)
          }.filter(x => isValid(x) && !isOccupied(x) && !visited.contains(x))
        val closestDestinations = availableDestionations.foldLeft(
          List.empty[(GridPoint2, Double)])((closerDirections, direction) => {
          val distanceFromArrival = direction.dst(arrival)
          closerDirections match {
            case Nil => List((direction, distanceFromArrival))
            case (_, dist) :: tail if dist > distanceFromArrival =>
              List((direction, distanceFromArrival))
            case list @ (_, dist) :: tail if dist == distanceFromArrival =>
              (direction, distanceFromArrival) :: list
            case list => list
          }
        })
        if (closestDestinations.isEmpty)
          None
        else
          Some(Random.shuffle(closestDestinations).head._1)
      }

      visited match {
        case h :: tail if h == arrival => visited
        case h :: tail if offCount <= randomness => {
          val goOff = Math.random() < randomness
          val next = nextStep(h, goOff)
          if (next.isEmpty)
            visited
          else
            generateSup(next.get :: visited)
        }
        case h :: tail =>
          val next = nextStep(h, goOff = false)
          if (next.isEmpty)
            List()
          else
            generateSup(next.get :: visited)

      }

    }

    generateSup(List(start))
  }

  val path = generateRandomPath(new GridPoint2(1, 1), new GridPoint2(3, 3), 30)

  override lazy val parts: List[MapGeneratorElementPart] = path.map(
    p => MapGeneratorElementPart(p, tiles.CenterCell)
  )

}

object Directions {
  val N = (0, 1)
  val S = (0, -1)
  val E = (1, 0)
  val W = (-1, 0)
  val NE = (1, 1)
  val SW = (-1, -1)
  val NW = (-1, 1)
  val SE = (1, -1)

  val directions = Set(N, W, S, E
    //,SW, SE, NW, NE
  )

}
