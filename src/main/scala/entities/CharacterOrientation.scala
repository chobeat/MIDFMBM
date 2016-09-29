package entities


case class CharacterOrientation(orientation:Orientation, movement:MovementType)

trait Orientation

case object South extends Orientation
case object North extends Orientation
case object East extends Orientation
case object West extends Orientation

trait MovementType

case object Standing extends MovementType

case object RunningLeftLeg extends MovementType

case object RunningRightLeg extends MovementType