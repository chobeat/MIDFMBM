package org.anacletogames.entities

import com.badlogic.gdx.math.{GridPoint2, Vector2}
import com.badlogic.gdx.scenes.scene2d.Actor
import org.anacletogames.actions.GameAction.ActionContext
import org.anacletogames.actions._
import org.anacletogames.battle.BattleMap
import org.anacletogames.behaviour.{DoOnceBehaviour, EntityBehaviour}
import render.WithDelta

import scala.util.{Failure, Success, Try}



abstract class Entity(val speed: Int = 1,
                      val battleMap: BattleMap,
                      val gameName:Option[String]=None,
                      renderingContext: WithDelta)
    extends Actor {

  def getDefaultBehaviour: EntityBehaviour

  def getPosition = battleMap.getEntityPosition(this)

  def getGameName:String= gameName match{
    case None=> getName
    case Some(n)=>n

  }

  val stackable: Boolean

  private var behaviour = getDefaultBehaviour

  def doOnce(a: GameAction) = {
    setBehaviour(DoOnceBehaviour(a))
  }

  def getActionContext: ActionContext = battleMap.getActionContext

  def nextAction(): Try[GameAction] = {
    behaviour.decideNextAction(battleMap)
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
