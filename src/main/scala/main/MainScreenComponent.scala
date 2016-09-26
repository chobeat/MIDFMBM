package main

import entities.HumanComponent
import sgl._
import geometry._
import scene._
import util._

trait BattleScreenComponent {
  this: StandardScreenComponent=>
  class BattleScreen extends GameScreen {

    private implicit val Tag = Logger.Tag("main")

    val human = new Human(20, 20, 20)

    private val hud = new BattleHud

    def processInputs(): Unit = Input.pollEvent() match {
      case None => ()
      case Some(ev) => {
          ev match {
            case Input.TouchDownEvent(_, _, _) |
                Input.MouseDownEvent(_, _, _) =>
            case _ => ()
          }
          processInputs()
        }
    }

    private var accumulatedDelta = 0l
    private val FixedDelta = 5l
    override def update(dt: Long): Unit = {
      processInputs()

      accumulatedDelta += dt

      while (accumulatedDelta / FixedDelta != 0) {
        accumulatedDelta -= FixedDelta
        fixedUpdate(FixedDelta)
      }
    }
    def fixedUpdate(dt: Long): Unit = {
      hud.sceneGraph.update(dt)
      human.update
    }
  }

  class StartingHud {
    val sceneGraph = new SceneGraph(WindowWidth, WindowHeight)
    private val group = new SceneGroup(0, 0, WindowWidth, WindowHeight)
    private val groupBackground = new StartingBackground
    private val titleLabel = new TitleLabel
    group.addNode(groupBackground)
    group.addNode(titleLabel)
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

  class TitleLabel extends SceneNode(dp2px(15), dp2px(20), 0, 0) {
    override def update(dt: Long): Unit = {}
    override def render(canvas: Canvas): Unit = {
      canvas.drawString("Scalavator",
                        x.toInt,
                        y.toInt,
                        defaultPaint.withColor(Color.White))
    }
  }
  class BattleHud {

    val sceneGraph = new SceneGraph(WindowWidth, WindowHeight)

    private val group = new SceneGroup(0, 0, WindowWidth, dp2px(40))
    private val groupBackground = new GroupBackground
    private val titleLabel = new TitleLabel
    val scoreLabel = new ScoreLabel
    group.addNode(groupBackground)
    group.addNode(titleLabel)
    group.addNode(scoreLabel)
    sceneGraph.addNode(group)

    class GroupBackground extends SceneNode(0, 0, 0, 0) {
      override def update(dt: Long): Unit = {}
      override def render(canvas: Canvas): Unit = {
        canvas.drawColor(Color.Red)
      }
    }

    class ScoreLabel
        extends SceneNode(WindowWidth - dp2px(25), dp2px(20), 0, 0) {
      var score: Int = 0
      override def update(dt: Long): Unit = {}
      override def render(canvas: Canvas): Unit = {
        canvas.drawString(score.toString,
                          x.toInt,
                          y.toInt,
                          defaultPaint
                            .withColor(Color.White)
                            .withAlignment(Alignments.Right))
      }
    }
  }
}
