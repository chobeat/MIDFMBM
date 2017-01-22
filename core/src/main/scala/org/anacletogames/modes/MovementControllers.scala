package org.anacletogames.modes

import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.OrthographicCamera
import org.anacletogames.actions.GridMovement
import org.anacletogames.entities.PartyEntity

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

  def entityControl(partyEntity: PartyEntity): InputProcessor = {
    case Input.Keys.W =>
      partyEntity.enqueueMovement(GridMovement(0, partyEntity.entity.speed))
    case Input.Keys.A =>
      partyEntity.enqueueMovement(GridMovement(-partyEntity.entity.speed, 0))
    case Input.Keys.D =>
      partyEntity.enqueueMovement(GridMovement(partyEntity.entity.speed, 0))
    case Input.Keys.S =>
      partyEntity.enqueueMovement(GridMovement(0, -partyEntity.entity.speed))
  }

  def zoom: InputProcessor = {

    case Input.Keys.N =>
      stage.getCamera.asInstanceOf[OrthographicCamera].zoom *= 1.1F

    case Input.Keys.M =>
      stage.getCamera.asInstanceOf[OrthographicCamera].zoom *= 0.9F

  }

}
