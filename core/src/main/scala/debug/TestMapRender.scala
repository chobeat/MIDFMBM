package debug

import org.anacletogames.modes.{BaseRenderer, MovementControllers, WithStage}

/**
  * Created by lara on 10/29/16.
  */
abstract class TestMapRender
    extends BaseRenderer
    with WithStage
    with MovementControllers {

  override val inputProcessor = zoom orElse arrowMovMap(64)
}
