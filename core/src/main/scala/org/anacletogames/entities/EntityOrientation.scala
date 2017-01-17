package org.anacletogames.entities

/**
  * Created by simone on 23.12.16.
  */
sealed trait EntityOrientation

case object LookingUp extends EntityOrientation
case object LookingDown extends EntityOrientation
case object LookingLeft extends EntityOrientation
case object LookingRight extends EntityOrientation
case object NoOrientation extends EntityOrientation
