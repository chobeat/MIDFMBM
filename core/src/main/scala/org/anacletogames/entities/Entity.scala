package org.anacletogames.entities

import com.badlogic.gdx.graphics.g2d.{Batch, Sprite}
import com.badlogic.gdx.graphics.{Color, Pixmap, Texture}
import com.badlogic.gdx.math.{Rectangle, Vector2}
import com.badlogic.gdx.scenes.scene2d.Actor
import org.anacletogames.{BattleMap, Simulation}
import org.anacletogames.actions.EntityBehaviour._
import org.anacletogames.actions.GameAction.ActionContext
import org.anacletogames.actions.{DoOnceAction, GameAction, NoAction}

import scala.collection.JavaConversions._
/**
  * Created by simone on 12.11.16.
  */
abstract class Entity(x: Float, y: Float, val speed: Float=1, context:BattleMap) extends Actor {

  private var behaviour:EntityBehaviour= (_,_)=>NoAction
  this.setX(x)
  this.setY(y)

  def setBehaviour(e:EntityBehaviour)=behaviour=e

  def doOnce(a:GameAction):Unit=behaviour=(_,_)=>DoOnceAction(a,this)(a.actionContext)

  def stageCoord = localToStageCoordinates(new Vector2(this.getX, this.getY))

  def stageBounds: Rectangle = sprite.getBoundingRectangle.setPosition(this.getX, this.getY)

  def getActionContext:ActionContext=context.getActionContext

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
