package org.anacletogames.entities

import org.anacletogames.behaviour.DoNothingBehaviour

/**
  * Created by simone on 22.11.16.
  */
trait DoNothingByDefault {
  def getDefaultBehaviour = DoNothingBehaviour
}
