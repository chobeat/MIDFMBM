package org.anacletogames

import com.badlogic.gdx.{Game, Gdx}
import com.badlogic.gdx.graphics.g2d.{Batch, Sprite}
import com.badlogic.gdx.graphics.{Color, GL20, Pixmap, Texture}
import com.badlogic.gdx.math.{Intersector, Rectangle, Vector2}
import com.badlogic.gdx.scenes.scene2d.{Actor, Event, EventListener, Stage}
import org.anacletogames.actions.EntityBehaviour.{DecisionContext, EntityBehaviour}
import org.anacletogames.actions._


abstract class Entity(x: Float, y: Float, val speed: Float=1) extends Actor {

  private var behaviour:EntityBehaviour= (_,_)=>NoAction
  this.setX(x)
  this.setY(y)

  def setBehaviour(e:EntityBehaviour)=behaviour=e

  def doOnce(a:GameAction):Unit=behaviour=(_,_)=>DoOnceAction(a,this)

  def stageCoord = localToStageCoordinates(new Vector2(this.getX, this.getY))

  def stageBounds: Rectangle = sprite.getBoundingRectangle.setPosition(this.getX, this.getY)


  def overlaps(other: Rectangle) = other.overlaps(this.stageBounds)

  def nextStep(decisionContext: DecisionContext):GameAction={
    behaviour(this,decisionContext)
  }

  def sprite:Sprite

  override def draw(batch: Batch, parentAlpha: Float): Unit = {
    batch.draw(sprite, getX, getY, sprite.getWidth, sprite
      .getHeight)

    val bounds = stageBounds

    val pixmapBound = new Pixmap(bounds.getWidth.toInt, bounds.getHeight.toInt, Pixmap.Format.RGBA8888)

    //Fill it red
    //Draw a circle about the middle
    pixmapBound.setColor(Color.RED)
    pixmapBound.drawRectangle(0, 0, pixmapBound.getWidth, pixmapBound.getHeight)

    val spriteBound = new Sprite(new Texture(pixmapBound))
    batch.draw(spriteBound, bounds.getX, bounds.getY, bounds.getWidth, bounds.getHeight)
  }

}




class Midfmbm extends Game {
  var simulation: Simulation = null

  override def create(): Unit = {
    simulation = new Simulation
  }

  override def render(): Unit = {
    simulation.nextStep()

    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    simulation.draw()
  }
}
