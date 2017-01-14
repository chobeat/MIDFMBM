package org.anacletogames.game.world

import com.badlogic.gdx.math.GridPoint2
import org.anacletogames.game.skills.{Skill, SkillCategory, SkillSet, Trait}
import org.anacletogames.game.world.buildings.Building
import monocle.{Lens, Traversal}
import monocle.macros.GenLens
import Settlement._
import org.anacletogames.battle.GameMap
import scalaz.std.list._

case class CharacterProfile(name: String,
                            age: Int,
                            traits: Seq[Trait],
                            skillSet: SkillSet)

trait Profession

case class Inhabitant(profile: CharacterProfile,
                      professions: Seq[Profession],
                      count: Int = 1) {

  def doStep(): Inhabitant = {
    println(this)
    copy(count = 1)
  }

}

case class Settlement(name: String,
                      position: GridPoint2,
                      population: List[Inhabitant],
                      buildings: List[Building]) {

  def doStep(gameMap:GameMap): Settlement = {
    inhabitantsLens.modify(_.doStep)(this)
  }

}

object Settlement {

  def popTravLens = Traversal.fromTraverse[List, Inhabitant]
  def popLens = GenLens[Settlement](_.population)
  def inhabitantsLens = popLens.composeTraversal(popTravLens)
}
