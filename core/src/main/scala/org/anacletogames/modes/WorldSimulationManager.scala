package org.anacletogames.modes

import org.anacletogames.battle.GameGrid
import render.Constants

import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Await, Future}

/**
  * Created by simone on 17.01.17.
  */
class WorldSimulationManager(var worldState: WorldState) {

  private var nextWorldState: Future[WorldState] =
    Future.successful(worldState)


  def doImmutableStep(gameMap:GameGrid, movedCountToday:Int) = {

    nextWorldState =
      nextWorldState.flatMap(_.doImmutableStep(gameMap, movedCountToday))

    if (movedCountToday >= Constants.tileMovementsToDay) {
      worldState = Await.result(nextWorldState, 5 seconds)
      println("pre")
      val c=worldState.settlements.flatMap(_.population.map(x=>x.count))
      println(c.max,c.min)
    }
  }

}
