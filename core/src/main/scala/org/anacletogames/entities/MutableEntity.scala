package org.anacletogames.entities

import com.badlogic.gdx.scenes.scene2d.Actor
import org.anacletogames.actions._
import org.anacletogames.battle.GameMap
import org.anacletogames.behaviour.{DoOnceBehaviour, EntityBehaviour}
import render.WithDelta

import scala.util.{Failure, Success, Try}

abstract class MutableEntity(val speed: Int = 1,
                             val gameMap: GameMap,
                             override val gameName: Option[String] = None,
                             renderingContext: WithDelta)
    extends Actor with Entity{

  def getDefaultBehaviour: EntityBehaviour





  private var behaviour = getDefaultBehaviour

  def doOnce(a: GameAction) = {
    setBehaviour(DoOnceBehaviour(a))
  }


  def nextAction(): Try[GameAction] = {
    behaviour.decideNextAction(gameMap)
  }

  def nextBehaviour(action: GameAction): Try[EntityBehaviour] = {
    behaviour.decideNextBehaviour(action)
  }

  def setBehaviour(newBehaviour: EntityBehaviour) = {
    behaviour = newBehaviour
  }

  def setBehaviour(newBehaviour: Try[EntityBehaviour]) = {
    newBehaviour match {
      case Success(b) => behaviour = b
      case Failure(e) => behaviour = getDefaultBehaviour
    }
  }

  def act(): Unit = {
    val action = nextAction()
    action match {
      case Success(a) =>
        a.execute
        setBehaviour(nextBehaviour(a))

      case Failure(e) =>
    }
  }

}
