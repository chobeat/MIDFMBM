package org.anacletogames.game.skills

import scala.collection.immutable.HashMap

/**
  * Created by simone on 12.01.17.
  */
class SkillSet extends HashMap[Skill,Int]{

  def getByCategory(c:SkillCategory): Map[Skill, Int] = this.filterKeys(_.category == c)



}
