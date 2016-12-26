package debug

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.FPSLogger
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.GridPoint2
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.FitViewport
import org.anacletogames.battle.BattleMap
import org.anacletogames.behaviour.ReachPointBehaviour
import org.anacletogames.entities.{DoNothingByDefault, RectEntity, WithEntityMovement, WithStackable}
import render.{EntityWithAnimation, WithDelta}

import scala.util.Random

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
      accumulatedRender=0
      battleMap.getAllEntities.foreach(_.act())
    }

    if (isTimeToRender) {
      accumulatedRender+=1

      super.render()
      fpsLogger.log()
      stage.draw()
      stage.setViewport(new FitViewport(800, 480, camera))
    }
  }

  def createDummy() = {
    val myChar = new RectEntity(1, battleMap, this) with WithStackable with EntityWithAnimation with WithEntityMovement
      with DoNothingByDefault
    myChar.setBehaviour(
      ReachPointBehaviour(
        myChar,
        new GridPoint2(Random.nextInt(22), Random.nextInt(22))))
    battleMap
      .addEntity(myChar, new GridPoint2(4, 4))
    stage.addActor(myChar)
  }

  override def create(): Unit = {

    super.create()

    battleMap = new BattleMap(mapWidth, mapHeight, tiledMap)
    shapeRenderer = new ShapeRenderer()

    stage = new Stage()
    (0 until 1).foreach(x => createDummy())

    inputProcessor = zoom orElse arrowMovMap(64)

  }
}
