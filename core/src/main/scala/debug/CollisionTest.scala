package debug

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.GridPoint2
import com.badlogic.gdx.scenes.scene2d.Stage
import org.anacletogames.battle.BattleMap
import org.anacletogames.behaviour.ReachPointBehaviour
import org.anacletogames.entities.{DoNothingByDefault, RectEntity, WithStackable}

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
  var battleMap:BattleMap = null
  var myChar: RectEntity = null
  var shapeRenderer: ShapeRenderer = null
  var myChar2: RectEntity = null
  override def render(): Unit = {
    super.render()
    battleMap.updateForDraw()
    stage.draw()
    myChar.nextStep().executeStep
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

    battleMap=new BattleMap(mapWidth, mapHeight, tiledMap)
    myChar = new RectEntity( 1, battleMap) with WithStackable with DoNothingByDefault
    myChar.setBehaviour(ReachPointBehaviour(myChar,new GridPoint2(10,10)))
    shapeRenderer = new ShapeRenderer()
    myChar2 = new RectEntity( 1, battleMap) with WithStackable with DoNothingByDefault

    inputProcessor = zoom orElse arrowMovMap(64) orElse entityControl(myChar)
    stage = new Stage()


    battleMap.addEntity(myChar,new GridPoint2(3,3))
    stage.addActor(myChar)

    battleMap.addEntity(myChar2,new GridPoint2(1,1))
    stage.addActor(myChar2)
  }
}
