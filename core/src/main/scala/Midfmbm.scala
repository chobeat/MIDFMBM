package org.anacletogames

import com.badlogic.gdx.{Game, Gdx}
import com.badlogic.gdx.graphics.g2d.{Batch, Sprite}
import com.badlogic.gdx.graphics.{Color, GL20, Pixmap, Texture}
import com.badlogic.gdx.math.{Intersector, Rectangle, Vector2}
import com.badlogic.gdx.scenes.scene2d.{Actor, Event, EventListener, Stage}
import org.anacletogames.actions._


abstract class Entity(x: Float, y: Float, val speed: Float) extends Actor {

  this.setX(x)
  this.setY(y)

  def stageCoord = localToStageCoordinates(new Vector2(this.getX, this.getY))

  def stageBounds: Rectangle = sprite.getBoundingRectangle.setPosition(this.getX,this.getY)


  def overlaps(other: Rectangle) = other.overlaps(this.stageBounds)


  def nextStep(entities: List[Entity]): GameAction = {

    val destX=100F
    val destY=100F
    val (nextStepX,nextStepY)=MoveUtil.projectStep(this,destX,destY)  
    if (entities.exists(entity => (!entity.equals(this)) && entity.overlaps(this.stageBounds.setPosition(
       getX+nextStepX,getY+nextStepY)
    )))
      NoAction
    else
      MoveTo(this, destX, destY)

  }

  val pixmap = new Pixmap(128, 128, Pixmap.Format.RGBA8888)

  //Fill it red
  //Draw a circle about the middle
  pixmap.setColor(Color.YELLOW)
  pixmap.drawCircle(pixmap.getWidth/2-1,pixmap.getHeight/2-1,pixmap.getWidth/2-1)

  val sprite = new Sprite(new Texture(pixmap))



  pixmap.dispose()

  override def draw(batch: Batch, parentAlpha: Float): Unit = {
    batch.draw(sprite, getX, getY, sprite.getWidth, sprite
        .getHeight)

    val bounds=stageBounds

    val pixmapBound = new Pixmap(bounds.getWidth.toInt, bounds.getHeight.toInt, Pixmap.Format.RGBA8888)

    //Fill it red
    //Draw a circle about the middle
    pixmapBound.setColor(Color.RED)
    pixmapBound.drawRectangle(0,0,pixmapBound.getWidth,pixmapBound.getHeight)

    val spriteBound = new Sprite(new Texture(pixmapBound))
      batch.draw(spriteBound,bounds.getX,bounds.getY,bounds.getWidth,bounds.getHeight)
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
