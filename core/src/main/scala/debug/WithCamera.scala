package debug

import com.badlogic.gdx.graphics.OrthographicCamera

/**
  * Created by simone on 06.11.16.
  */
trait WithCamera {
  val camera = new OrthographicCamera();
}
