package render

import com.badlogic.gdx.Gdx

/**
  * Created by simone on 04.12.16.
  */
trait WithDelta {


  var accumulatedDelta=0f
  var accumulatedRender=0

  def updateDelta()= {
    if(isTimeToAct)
      accumulatedDelta=0

    accumulatedDelta += Gdx.graphics.getDeltaTime
  }

  def isTimeToRender:Boolean=accumulatedDelta>Constants.renderTime

  def isTimeToAct:Boolean = accumulatedRender>=Constants.actTime

}
