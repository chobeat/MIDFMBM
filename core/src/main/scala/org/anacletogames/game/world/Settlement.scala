package org.anacletogames.game.world

import com.badlogic.gdx.math.GridPoint2
import org.anacletogames.battle.GameMap
import org.anacletogames.game.skills.{SkillSet, Trait}
import org.anacletogames.game.world.buildings.Building

case class CharacterProfile(name: String,
                            age: Int,
                            traits: Seq[Trait],
                            skillSet: SkillSet)

trait Profession

case class Inhabitant(profile: CharacterProfile,
                      professions: Seq[Profession],
                      count: Int = 1) {

  def doStep(): Inhabitant = {
    copy(count = count + 1)
  }

}

case class Settlement(name: String,
                      position: GridPoint2,
                      population: List[Inhabitant],
                      buildings: List[Building]) {

  def doStep(gameMap: GameMap): Settlement = {
    val nextPop = population.map(_.doStep())
    val c = nextPop.foldRight(0)((c, i) => c.count + i)
    copy(population = population.map(_.copy(count = c)))
  }

}
