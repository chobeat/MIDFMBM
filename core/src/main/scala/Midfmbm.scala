package org.anacletogames

import com.badlogic.gdx.{Game, Gdx}
import com.badlogic.gdx.graphics.g2d.{Batch, Sprite}
import com.badlogic.gdx.graphics.{Color, GL20, Pixmap, Texture}
import com.badlogic.gdx.math.{Intersector, Rectangle, Vector2}
import com.badlogic.gdx.scenes.scene2d.{Actor, Event, EventListener, Stage}
import org.anacletogames.actions.EntityBehaviour.{
  DecisionContext,
  EntityBehaviour
}
import org.anacletogames.actions._
/*
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
*/