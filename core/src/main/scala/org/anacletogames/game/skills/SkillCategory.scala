package org.anacletogames.game.skills

/**
  * Created by simone on 12.01.17.
  */
sealed trait SkillCategory

case object Smithing extends SkillCategory
case object Tailoring extends SkillCategory
case object Farming extends SkillCategory
