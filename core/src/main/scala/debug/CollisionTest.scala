package debug

import com.badlogic.gdx.scenes.scene2d.{Actor, Stage}
import com.badlogic.gdx.{ApplicationAdapter, InputProcessor}
import org.anacletogames.BattleMap
import org.anacletogames.entities.RectEntity
import util.InputDefaultHandler

/**
  * Created by simone on 05.11.16.
  */
class CollisionTest extends DebugRenderer(32,32) with MovementControllers with WithStage{
  val tileSize=32
  val mapWidth=32*tileSize
  val mapHeight=32*tileSize
  val battleMap=new BattleMap(mapWidth,mapHeight)
  var  myChar:RectEntity=null

  var  myChar2:RectEntity=null
   override def render(): Unit = {
    super.render()
     stage.getCamera.update()
     stage.draw()
     myChar.nextStep(List()).executeStep


    // super.render()
  }

  override  def create(): Unit = {

    super.create()
    myChar=  new RectEntity(250,250,32,battleMap)

    myChar2=  new RectEntity(5,5,32,battleMap)

    inputProcessor = zoom orElse arrowMovMap(64)  orElse entityControl(myChar)
    stage = new Stage()
    battleMap.addEntity(myChar)
    stage.addActor(myChar)

    battleMap.addEntity(myChar2)
    stage.addActor(myChar2)
  }
}
