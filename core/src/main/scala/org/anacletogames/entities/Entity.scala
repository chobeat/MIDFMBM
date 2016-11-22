package org.anacletogames.entities

import com.badlogic.gdx.graphics.g2d.{Batch, Sprite}
import com.badlogic.gdx.graphics.{Color, Pixmap, Texture}
import com.badlogic.gdx.math.{GridPoint2, Rectangle, Vector2}
import com.badlogic.gdx.scenes.scene2d.Actor
import org.anacletogames.actions.GameAction.ActionContext
import org.anacletogames.actions._
import org.anacletogames.battle.BattleMap
import org.anacletogames.behaviour.{Decision, DoOnceBehaviour, EntityBehaviour}

/**
  * Created by simone on 12.11.16.
  */
abstract class SingleTileEntity(speed: Int = 1, battleMap: BattleMap)
    extends Entity(speed, battleMap) {
  override def canIMoveThere(destination: GridPoint2): Boolean = {
    stackable && battleMap.isTileAccessible(destination)
  }

}

abstract class Entity(val speed: Int = 1, battleMap: BattleMap) extends Actor {

  def getDefaultBehaviour: EntityBehaviour

  def getPosition = battleMap.getEntityPosition(this)

  def canIMoveThere(destination: GridPoint2): Boolean

  val stackable: Boolean

  private var behaviour: EntityBehaviour = getDefaultBehaviour

  def setBehaviour(e: EntityBehaviour) = behaviour = e

  def doOnce(a: GameAction): Unit =
    this.setBehaviour(DoOnceBehaviour(this,a,behaviour))

  def stageCoord = localToStageCoordinates(new Vector2(this.getX, this.getY))

  def stageBounds: Rectangle =
    sprite.getBoundingRectangle.setPosition(this.getX, this.getY)

  def getActionContext: ActionContext = battleMap.getActionContext

  def overlaps(other: Rectangle) = other.overlaps(this.stageBounds)

  def nextStep(): GameAction = {

    val Decision(newBehaviour, nextAction) =
      behaviour.decideNextAction(this.battleMap)
    this.setBehaviour(newBehaviour)
    nextAction
  }

  override def moveBy(x: Float, y: Float): Unit = {
    battleMap.moveEntity(this, GridMovement(x.toInt, y.toInt))
  }

  def sprite: Sprite

  override def draw(batch: Batch, parentAlpha: Float): Unit = {
    val position = getPosition
    if (position.nonEmpty) {
      this.setX(position.get.x * render.Constants.TileWidth)
      this.setY(position.get.y * render.Constants.TileHeigth)
      batch.draw(sprite, getX, getY, sprite.getWidth, sprite.getHeight)

      val bounds = stageBounds

      val pixmapBound = new Pixmap(bounds.getWidth.toInt,
                                   bounds.getHeight.toInt,
                                   Pixmap.Format.RGBA8888)

      //Fill it red
      //Draw a circle about the middle
      pixmapBound.setColor(Color.RED)
      pixmapBound
        .drawRectangle(0, 0, pixmapBound.getWidth, pixmapBound.getHeight)

      val spriteBound = new Sprite(new Texture(pixmapBound))
      batch.draw(spriteBound,
                 bounds.getX,
                 bounds.getY,
                 bounds.getWidth,
                 bounds.getHeight)
    }
  }
}
