package org.anacletogames.entities

import com.badlogic.gdx.math.{GridPoint2, Vector2}
import com.badlogic.gdx.scenes.scene2d.Actor
import org.anacletogames.actions.GameAction.ActionContext
import org.anacletogames.actions._
import org.anacletogames.battle.{BattleMap, GameGrid, GameMap}
import org.anacletogames.behaviour.{DoOnceBehaviour, EntityBehaviour}
import render.WithDelta

import scala.util.{Failure, Success, Try}

abstract class MutableEntity(val speed: Int = 1,
                             val gameMap: GameMap,
                             val gameName: Option[String] = None,
                             renderingContext: WithDelta)
    extends Actor with Entity{

  def getDefaultBehaviour: EntityBehaviour

  def getPosition = gameMap.getEntityPosition(this)

  def getGameName: String = gameName match {
    case None => getName
    case Some(n) => n

  }



  private var behaviour = getDefaultBehaviour

  def doOnce(a: GameAction) = {
    setBehaviour(DoOnceBehaviour(a))
  }

  def getActionContext: ActionContext = gameMap.getActionContext

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
