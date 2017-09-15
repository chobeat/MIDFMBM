package org.anacletogames.actors

import akka.actor.{Actor, ActorRef, Props}
import akka.event.LoggingReceive
import org.anacletogames.battle.GameGrid

import scala.collection.mutable

case object InitSimulation

case class UpdateCompleted(int:Int) {

  override def equals(that: Any) = ???
}

case class AddSettlement(name: String) {
  override def equals(that: Any) = ???
}

case class UpdateSimulation(gameGrid: GameGrid, dayFraction: Int) {
  override def equals(that: Any) = ???
}

class WorldSimulationActor extends Actor {
  val worldState: ActorRef = context.actorOf(Props[WorldStateActor])

  override def receive = LoggingReceive {
    case m @ UpdateSimulation(g, df) => {
      worldState forward m
      context.become(waitForUpdateCompleted(sender))
    }
    case m: AddSettlement => {
      worldState forward m
    }
  }

  def waitForUpdateCompleted(asker: ActorRef): Receive = {
    case UpdateCompleted => {
      asker ! UpdateCompleted
      context.become(receive)
    }

  }
}

class WorldStateActor extends Actor {
  override def receive = {
    case AddSettlement(name: String) => {
      context.actorOf(Props[SettlementActor], name)

    }

    case m @ UpdateSimulation(g, df) => {
      context.children.foreach(c => c ! m)
      context.become(waitForUpdateCompleted(context.children.size))
    }

  }

  def waitForUpdateCompleted(childrenNum: Int, partialCount:Int=0): Receive = {
    case UpdateCompleted(i) =>
      childrenNum match {
        case 1 => {
          println(s"Updated with $partialCount")
          context.parent ! UpdateCompleted
          context.become(receive)
        }
        case n => {
          context.become(waitForUpdateCompleted(n - 1,partialCount+i))
        }
      }

  }
}

class SettlementActor extends Actor {

  override def receive: Receive = logic(0)

  def logic(int: Int): Receive = {
    case UpdateSimulation(grid, df) =>
      //logic
      context.become(logic(int + 1))
      sender ! UpdateCompleted(int)
  }

}
