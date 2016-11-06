package debug

import com.badlogic.gdx.Input
import org.anacletogames.Entity
import org.anacletogames.actions.MoveBy
import com.badlogic.gdx.graphics.OrthographicCamera
/**
  * Created by simone on 05.11.16.
  */
trait MovementControllers {
  this:WithCamera with WithStage =>

  type InputProcessor=PartialFunction[Int,Unit]
  def arrowMovMap(movSize:Int=64):InputProcessor =
    {

    case Input.Keys.LEFT=> camera.translate(movSize,0)
    case Input.Keys.RIGHT=>  camera.translate(-movSize,0)
    case Input.Keys.UP=>  camera.translate(0,-movSize)
    case Input.Keys.DOWN=>  camera.translate(0,movSize)

  }

  def entityControl(entity:Entity):InputProcessor=
    {
    case Input.Keys.W=>entity.doOnce(MoveBy(entity,0,entity.speed))

    case Input.Keys.A=>entity.doOnce(MoveBy(entity,-entity.speed,0))
    case Input.Keys.D=>entity.doOnce(MoveBy(entity,entity.speed,0))
    case Input.Keys.S=>entity.doOnce(MoveBy(entity,0,-entity.speed))
  }

  def zoom:InputProcessor={

    case Input.Keys.N=>  {stage.getCamera.asInstanceOf[OrthographicCamera].zoom*=1.1F
      camera.zoom*=1.1F}
    case Input.Keys.M=>  {stage.getCamera.asInstanceOf[OrthographicCamera].zoom*=0.9F
      camera.zoom=camera.zoom*0.9F}
  }

}
