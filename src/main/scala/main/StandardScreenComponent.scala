package main

import entities.{CharacterComponent}
import sgl._
import sgl.scene.SceneComponent
import sgl.util.LoggingProvider

trait StandardScreenComponent
  extends GraphicsProvider
  with InputProvider
  with GameLoopComponent
  with GameScreensComponent
  with WindowProvider
  with SystemProvider
  with AudioProvider
  with SceneComponent
  with LoggingProvider
  with CharacterComponent