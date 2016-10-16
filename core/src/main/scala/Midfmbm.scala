package org.anacletogames

import com.badlogic.gdx.{Gdx, Game}
import com.badlogic.gdx.graphics.g2d.{Batch, Sprite}
import com.badlogic.gdx.graphics.{GL20, Color, Pixmap, Texture}
import com.badlogic.gdx.math.Intersector
import com.badlogic.gdx.scenes.scene2d.{Event, EventListener, Actor, Stage}
import org.anacletogames.Simulation.WithNextStep
import org.anacletogames.actions.{MoveBy, GameAction}

import scala.util.Random

class TestActor extends Actor {
  val pixmap = new Pixmap(256, 128, Pixmap.Format.RGBA8888)

  //Fill it red
  //Draw a circle about the middle
  pixmap.setColor(Color.YELLOW)
  pixmap.drawCircle(pixmap.getWidth / 2,
                    pixmap.getHeight / 2,
                    pixmap.getHeight / 2 - 1)

  val sprite = new Sprite(new Texture(pixmap))

  pixmap.dispose()
  override def draw(batch: Batch, parentAlpha: Float): Unit = {
    batch.draw(sprite, getX, getY, 256, 128)
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
