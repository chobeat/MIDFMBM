package org.anacletogames.modes

import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.OrthographicCamera
import org.anacletogames.actions.GridMovement
import org.anacletogames.entities.{Entity, PartyEntity}
import org.anacletogames.events.{GameEvent, MoveByEvent, MovementEvent}

/**
  * Created by simone on 05.11.16.
  */
trait MovementControllers { this: WithStage =>

  type InputProcessor = PartialFunction[Int, Unit]
  def arrowMovMap(movSize: Int = 64): InputProcessor = {

    case Input.Keys.LEFT => stage.getCamera.translate(movSize, 0, 0)
    case Input.Keys.RIGHT => stage.getCamera.translate(-movSize, 0, 0)
    case Input.Keys.UP => stage.getCamera.translate(0, -movSize, 0)
    case Input.Keys.DOWN => stage.getCamera.translate(0, movSize, 0)

  }

  def entityControl(partyEntity: Entity, userGeneratedEventsQueue: scala.collection.mutable.Queue[GameEvent]): InputProcessor = {
    case Input.Keys.W =>
      userGeneratedEventsQueue.enqueue(MoveByEvent(partyEntity.id,GridMovement(partyEntity.speed, 0)))
    case Input.Keys.A =>
      userGeneratedEventsQueue.enqueue(MoveByEvent(partyEntity.id,GridMovement(-partyEntity.speed, 0)))
    case Input.Keys.D =>
      userGeneratedEventsQueue.enqueue(MoveByEvent(partyEntity.id,GridMovement(partyEntity.speed, 0)))
    case Input.Keys.S =>
      userGeneratedEventsQueue.enqueue(MoveByEvent(partyEntity.id,GridMovement(0, -partyEntity.speed)))
  }

  def zoom: InputProcessor = {

    case Input.Keys.N =>
      stage.getCamera.asInstanceOf[OrthographicCamera].zoom *= 1.1F

    case Input.Keys.M =>
      stage.getCamera.asInstanceOf[OrthographicCamera].zoom *= 0.9F

  }

}
