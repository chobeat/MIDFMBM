package main

import entities.HumanComponent
import sgl._
import sgl.geometry._
import sgl.scene._
import sgl.util._
trait StartingScreenComponent {
  this: StandardScreenComponent with BattleScreenComponent =>
  class StartingScreen extends GameScreen {
    private implicit val Tag = Logger.Tag("starting")

    private val hud = new StartingHud

    override def update(dt: Long): Unit = {
      processInputs()

      hud.update(dt)
    }

    override def render(canvas: Canvas) = {

      hud.sceneGraph.render(canvas)
    }

    def processInputs(): Unit = Input.processEvents {

      case ev => hud.sceneGraph.processInput(ev)
    }
  }

  class StartingHud {
    val sceneGraph = new SceneGraph(WindowWidth, WindowHeight)
    private val group = new SceneGroup(0, 0, WindowWidth, WindowHeight)
    private val groupBackground = new StartingBackground
    group.addNode(groupBackground)
    sceneGraph.addNode(group)

    def update(dt: Long): Unit = {

      sceneGraph.update(dt)
    }
  }
  class StartingBackground extends SceneNode(0, 0, WindowWidth, WindowHeight) {

    private implicit val Tag = Logger.Tag("starting-background")
    override def render(canvas: Canvas): Unit = {
      canvas.drawRect(0,
                      0,
                      WindowWidth,
                      WindowHeight,
                      defaultPaint.withColor(Color.Blue))
    }
    override def notifyClick(x: Int, y: Int): Boolean = {
      logger.warning("Start click")
      gameLoop.newScreen(new BattleScreen)
      true
    }
    override def update(dt: Long): Unit = ()
  }
}
