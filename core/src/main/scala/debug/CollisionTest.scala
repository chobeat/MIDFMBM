package debug

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.GridPoint2
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.FitViewport
import org.anacletogames.battle.BattleMap
import org.anacletogames.behaviour.ReachPointBehaviour
import org.anacletogames.entities.{DoNothingByDefault, RectEntity, WithStackable}

import scala.util.Random

/**
  * Created by simone on 05.11.16.
  */
class CollisionTest
    extends DebugRenderer(32, 32)
    with MovementControllers
    with WithStage
{
  val tileSize = 32
  val mapWidth = 32
  val mapHeight = 32
  var battleMap:BattleMap = null
  var shapeRenderer: ShapeRenderer = null
  override def render(): Unit = {
    super.render()

    battleMap.updateForDraw()
    stage.draw()
    stage.setViewport( new FitViewport(800, 480, camera))
    battleMap.getAllEntities.foreach(_.act())

  }

  def createDummy()={
    val myChar = new RectEntity( 1, battleMap) with WithStackable with DoNothingByDefault
    myChar.setBehaviour(ReachPointBehaviour(myChar,new GridPoint2(Random.nextInt(11),Random.nextInt(11))))
    battleMap.addEntity(myChar, new GridPoint2(Random.nextInt(4),Random.nextInt(5)))
    stage.addActor(myChar)
   }

  override def create(): Unit = {

    super.create()

    battleMap=new BattleMap(mapWidth, mapHeight, tiledMap)
    shapeRenderer = new ShapeRenderer()

    stage = new Stage()
    (0 to 1).foreach(x=>createDummy())

    inputProcessor = zoom orElse arrowMovMap(64) orElse entityControl(battleMap.getAllEntities.toList.head)

  }
}
