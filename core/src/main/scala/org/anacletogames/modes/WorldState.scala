package org.anacletogames.modes

import org.anacletogames.battle.GameGrid
import org.anacletogames.game.world.Settlement
import render.Constants

import scala.collection.{GenSeq, mutable}
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by simone on 17.01.17.
  */
class WorldState(
    val settlements: GenSeq[Settlement]) {

  def doImmutableStep(gameGrid: GameGrid,
                      dayFraction: Int): Future[WorldState] = {

    Future {

      val chunks = getChunk(dayFraction)

      val updatedChunks=chunks match {

        case Some((start, end)) =>
          settlements.par.zipWithIndex.map {
            case (settlement, i) if i >= start && i < end =>
              settlement.doStep(gameGrid)
            case (settlement, i) => settlement
          }
        case None => settlements
      }

      new WorldState(updatedChunks)

    }
  }

  def getChunk(dayFraction: Int): Option[(Int, Int)] = {

    val chunkNumber = Constants.tileMovementsToDay

    if(dayFraction>chunkNumber)
      None
    else{
    val chunkSize = settlements.length / chunkNumber
    val chunkStartIndex = chunkSize * (dayFraction - 1)
    val chunkEndIndex = chunkSize * dayFraction
    Some(chunkStartIndex,chunkEndIndex)
    }
  }
}
object WorldState {
  def empty: WorldState = new WorldState(mutable.MutableList.empty[Settlement])
}
