package main

import entities.{EntitiesContainer}
import sgl._
import sgl.awt._
import scene._
import sprites.SpriteHandler32
import util._

trait AbstractApp {
  this: GraphicsProvider with InputProvider with WindowProvider with AudioProvider with GameScreensComponent with SystemProvider with StartingScreenComponent with GameLoopComponent with SceneComponent with LoggingProvider =>

  override def startup(): Unit = {}
  override def resume(): Unit = {}
  override def pause(): Unit = {}
  override def shutdown(): Unit = {}

  override def startingScreen: GameScreen = new StartingScreen
}

/** Wire backend to the App here */
object Main
    extends AbstractApp
    with AWTApp
    with SceneComponent
    with VerboseStdErrLoggingProvider
    with ScreenContainer
    with EntitiesContainer
with SpriteHandler32
{

  override val Fps = Some(30)

  override val frameDimension = Some((800, 600))
}
