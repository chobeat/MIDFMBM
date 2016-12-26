package debug

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.FPSLogger
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.GridPoint2
import com.badlogic.gdx.scenes.scene2d.{Actor, Stage}
import com.badlogic.gdx.utils.viewport.FitViewport
import org.anacletogames.battle.BattleMap
import org.anacletogames.behaviour.ReachPointBehaviour
import org.anacletogames.entities.{
  DoNothingByDefault,
  RectEntity,
  WithEntityMovement,
  WithStackable
}
import render.{EntityWithAnimation, WithDelta}

import scala.util.Random
import scala.collection.JavaConversions._

/**
  * Created by simone on 05.11.16.
  */
class CollisionTest
    extends DebugRenderer(32, 32)
    with MovementControllers
    with WithStage
    with WithDelta {
  val tileSize = 32
  val mapWidth = 32
  val mapHeight = 32
  var battleMap: BattleMap = null
  var shapeRenderer: ShapeRenderer = null
  val fpsLogger = new FPSLogger

  override def render(): Unit = {

    updateDelta()
    if (isTimeToAct) {
      accumulatedRender = 0
      battleMap.getAllEntities.foreach(_.act())
    }

    if (isTimeToRender) {
      accumulatedRender += 1

      super.render()
      fpsLogger.log()
      //to draw actors ordered by position
      val actors =
        stage.getActors.toList.sortBy(_.getY)(Ordering[Float].reverse)
      stage.clear()
      actors.foreach(stage.addActor)

      stage.draw()
      stage.setViewport(new FitViewport(800, 480, camera))
    }
  }

  def createDummy(x: Int) = {
    val myChar = new RectEntity(1, battleMap, this) with WithStackable
    with EntityWithAnimation with WithEntityMovement with DoNothingByDefault
    myChar.setBehaviour(ReachPointBehaviour(myChar, new GridPoint2(3, 22)))
    battleMap
      .addEntity(myChar, new GridPoint2(x, x))
    stage.addActor(myChar)
  }

  override def create(): Unit = {

    super.create()

    battleMap = new BattleMap(mapWidth, mapHeight, tiledMap)
    shapeRenderer = new ShapeRenderer()

    stage = new Stage()
    (0 until 5).foreach(x => createDummy(x))

    inputProcessor = zoom orElse arrowMovMap(64)

  }
}
