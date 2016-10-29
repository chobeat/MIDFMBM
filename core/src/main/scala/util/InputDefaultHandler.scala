package util

/**
  * Created by lara on 10/29/16.
  */
trait InputDefaultHandler {
  def keyUp(keycode: Int): Boolean = false

  def keyDown(keycode: Int): Boolean = false

  def keyTyped(character: Char): Boolean = false

  def touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean = false

  def touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean = false

  def touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean = false

  def mouseMoved(screenX: Int, screenY: Int): Boolean = false

  def scrolled(amount: Int): Boolean = false
}
