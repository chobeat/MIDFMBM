package debug

/**
  * Created by lara on 10/29/16.
  */
class TestMapRender extends DebugRenderer(32,32) with MovementControllers{


  inputProcessor = zoom orElse  arrowMovMap(64)
}
