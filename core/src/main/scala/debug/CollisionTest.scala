package debug

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.math.{Rectangle, Vector2}
import com.badlogic.gdx.scenes.scene2d.{Actor, Stage}
import com.badlogic.gdx.{ApplicationAdapter, InputProcessor}
import org.anacletogames.BattleMap
import org.anacletogames.entities.RectEntity
import util.InputDefaultHandler
import org.anacletogames.maps._

import scala.collection.JavaConversions._

/**
  * Created by simone on 05.11.16.
  */
class CollisionTest
    extends DebugRenderer(32, 32)
    with MovementControllers
    with WithStage {
  val tileSize = 32
  val mapWidth = 32 * tileSize
  val mapHeight = 32 * tileSize
  val battleMap = new BattleMap(mapWidth, mapHeight, tiledMap)
  var myChar: RectEntity = null
  var shapeRenderer: ShapeRenderer = null
  var myChar2: RectEntity = null
  override def render(): Unit = {
    super.render()
    stage.draw()
    myChar.nextStep(List()).executeStep
    /*
    shapeRenderer.begin(ShapeType.Filled)
    shapeRenderer.setColor(Color.RED)

    tiledMap.getSolidShapes.foreach {
      case shape: Rectangle =>
        val v=new Vector2(0,0)
        shape.getPosition(v)
        shapeRenderer.rect(v.x, v.y, shape.width, shape.height)

    }
    shapeRenderer.end()
     */
    // super.render()
  }

  override def create(): Unit = {

    super.create()
    myChar = new RectEntity(300, 300, 32, battleMap)
    shapeRenderer = new ShapeRenderer()
    myChar2 = new RectEntity(150, 150, 32, battleMap)

    inputProcessor = zoom orElse arrowMovMap(64) orElse entityControl(myChar)
    stage = new Stage()

    battleMap.addEntity(myChar)
    battleMap.addCollidableShapes(tiledMap.getSolidShapes)

    stage.addActor(myChar)

    battleMap.addEntity(myChar2)
    stage.addActor(myChar2)
  }
}
