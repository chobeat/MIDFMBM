package org.anacletogames

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Stage.TouchFocus
import com.badlogic.gdx.scenes.scene2d.{
  InputEvent,
  Event,
  EventListener,
  Stage
}
import com.badlogic.gdx.scenes.scene2d.ui.{Skin, TextButton, Table}
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle
import com.badlogic.gdx.scenes.scene2d.utils.{
  ClickListener,
  TextureRegionDrawable
}
import org.anacletogames.Simulation.WithNextStep
import org.anacletogames.actions.GameAction
import org.anacletogames.entities.Entity

object Simulation {
  type WithNextStep = { def nextStep(): GameAction }
  type Drawable = { def draw(): Unit }
}

class Simulation {
  var toBeComputed: List[WithNextStep] = List()

  var x = 1
  var myChar = new Entity(50, 50)

  toBeComputed = toBeComputed :+ myChar

  var battleStage = new Stage()
  Gdx.input.setInputProcessor(battleStage);
  battleStage.addActor(myChar.actor)
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
