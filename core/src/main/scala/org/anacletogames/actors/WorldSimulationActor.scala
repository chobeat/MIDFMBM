package org.anacletogames.actors

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import akka.event.LoggingReceive
import org.anacletogames.battle.GameGrid
import akka.pattern._
import akka.util.Timeout

import scala.collection.mutable
import scala.concurrent.Future
import scala.concurrent.duration.Duration
import scala.util.Random

case object InitSimulation

case class SettlementStatus(name:String) {
  override def equals(that: Any) = ???
}

case class UpdateCompleted(int: Int) {

  override def equals(that: Any) = ???
}

case class AddSettlement(name: String) {
  override def equals(that: Any) = ???
}

case class UpdateSimulation(gameGrid: GameGrid, dayFraction: Int) {
  override def equals(that: Any) = ???
}

class WorldSimulationActor extends Actor {
  val worldState: ActorRef = context.actorOf(Props[WorldStateActor],"worldstate")

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

  def waitForUpdateCompleted(childrenNum: Int, partialCount: Int = 0): Receive = {
    case UpdateCompleted(i) =>
      childrenNum match {
        case 1 => {
          println(s"Updated with $partialCount")
          context.parent ! UpdateCompleted
          context.become(receive)
        }
        case n => {
          context.become(waitForUpdateCompleted(n - 1, partialCount + i))
        }
      }

  }
}

class Inhabitant(){
  var x:Int=0

  def updateX(v:Int):Int={
    x=x+v+Random.nextInt(5)-2
    x
  }


}

class SettlementActor extends Actor {
  val inhabitants:List[Inhabitant] = (0 to 2000).map(x=>new Inhabitant()).toList

  override def receive: Receive =
    logic(0)

  def logic(int: Int): Receive = {
    case UpdateSimulation(grid, df) =>
      //logice
      println(self.path)
      val sum=inhabitants.map(_.updateX(int)).sum
      context.sender ! UpdateCompleted(sum)
      context.become(logic(sum))
  }


}

case class GetSettlementStatus(){
  override def equals(that: Any) = ???
}

object Settlements{
  implicit val timeout:Timeout=Duration(1000,"seconds")

  def getSettlementStatus(id:String)(implicit system:ActorSystem): Future[SettlementStatus] ={
    system.actorSelection("/Game/user/$a/worldstate/test"+id).ask(GetSettlementStatus()).mapTo[SettlementStatus]


  }

}