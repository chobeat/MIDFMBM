package org.anacletogames.game.world

import com.badlogic.gdx.math.GridPoint2
import org.anacletogames.game.skills.{Skill, SkillCategory, SkillSet}

import scala.collection.immutable.HashMap







trait Trait

case class CharacterProfile(age:Int,traits:Seq[Trait],skillSet:SkillSet)

case class Inhabitant(profile:CharacterProfile)

trait Building

case class Settlement(position:GridPoint2, population:Seq[Inhabitant], buildings:Seq[Building]) {

}
