package org.anacletogames.modes

import org.anacletogames.events.GameEvent

/**
  * Created by simone on 07.02.17.
  */
trait GameLoopCommons {

  var eventsFromLastFrame: Seq[GameEvent] = Seq()
}
