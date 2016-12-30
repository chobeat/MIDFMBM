package org.anacletogames.modes

import com.badlogic.gdx.scenes.scene2d.Stage
/**
  * Created by simone on 06.11.16.
  */
trait WithStage {

  lazy val stage:Stage=new Stage()
  lazy val guiStage:Stage = new Stage()


}
