package org.anacletogames

import com.badlogic.gdx.Gdx

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.{
  InputEvent,
  Stage
}
import com.badlogic.gdx.scenes.scene2d.ui.{Skin, TextButton, Table}
import com.badlogic.gdx.scenes.scene2d.utils.{
  ClickListener
}
import com.badlogic.gdx.physics.box2d._
import org.anacletogames.Simulation.WithNextStep
import org.anacletogames.actions.GameAction
import org.anacletogames.entities.{RectEntity, Entity}

import com.badlogic.gdx.physics.box2d._
object Simulation {
  type WithNextStep = { def nextStep(): GameAction }
  type Drawable = { def draw(): Unit }
}

class Simulation {
  var toBeComputed: List[WithNextStep] = List()

  var x = 1
  var myChar = new RectEntity(50,50,1,25,25)
  var myChar2 = new RectEntity(250,250,1,25,25)

  toBeComputed = toBeComputed :+ myChar :+ myChar2
  val world= new World(new Vector2(0, -10), true)
  var battleStage = new Stage()
  Gdx.input.setInputProcessor(battleStage);
  battleStage.addActor(myChar.actor)
  battleStage.addActor(myChar2.actor)
  val battleMenu = new Table()
  battleMenu.setFillParent(true);

  val resetPosition = new ClickListener {
    override def touchDown(event: InputEvent,
                           x: Float,
                           y: Float,
                           pointer: Int,
                           button: Int): Boolean = {
      myChar.actor.setPosition(0,0)
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
    val actions = toBeComputed.map(_.nextStep())
    actions.foreach(_.executeStep)
  }
  def draw() = {
    battleStage.draw()
  }

}
