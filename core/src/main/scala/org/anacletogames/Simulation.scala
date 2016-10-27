package org.anacletogames

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.{Rectangle, Vector2}
import com.badlogic.gdx.scenes.scene2d.{Actor, InputEvent, Stage}
import com.badlogic.gdx.scenes.scene2d.ui.{Skin, Table, TextButton}
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import org.anacletogames.actions.GameAction
import org.anacletogames.entities.RectEntity
import com.badlogic.gdx.physics.box2d._

import scala.collection.JavaConversions._
object Simulation {
  type Drawable = { def draw(): Unit }
}

class Simulation {
  var toBeComputed: List[Entity] = List()
  var toBeCollided: List[Entity] = List()

  var x = 1
  var myChar = new RectEntity(250,250,1)
  var myChar2 = new RectEntity(10,10,1)


  toBeComputed = toBeComputed :+ myChar :+ myChar2
  toBeCollided = toBeCollided :+ myChar :+ myChar2
  var battleStage = new Stage()
  Gdx.input.setInputProcessor(battleStage);
  battleStage.addActor(myChar)
  battleStage.addActor(myChar2)
  val battleMenu = new Table()
  battleMenu.setFillParent(true);

  val resetPosition = new ClickListener {
    override def touchDown(event: InputEvent,
                           x: Float,
                           y: Float,
                           pointer: Int,
                           button: Int): Boolean = {
      myChar.setPosition(0,0)
      true
    }
  }
  val button1 =
    new TextButton("Button 1", new Skin(Gdx.files.internal("uiskin.json")))
  val button2 =
    new TextButton("Button 2", new Skin(Gdx.files.internal("uiskin.json")))


  button1.addCaptureListener(resetPosition)

val table= new Table()
  table.add(button1).top()
  table.add(button2).bottom()

  battleMenu.add(table)
  battleMenu.left().top()


  battleStage.addActor(battleMenu);

  def resize(width: Int, height: Int) {
    battleStage.getViewport().update(width, height, true)
  }

  def nextStep() = {
    val actions = toBeComputed.map(_.nextStep(toBeCollided))
    actions.foreach(_.executeStep)
  }
  def draw() = {

    battleStage.draw()
  }

}
