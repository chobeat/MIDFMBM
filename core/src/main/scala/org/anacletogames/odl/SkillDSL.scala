package org.anacletogames.odl
import org.anacletogames.game.skills.SkillCategory

import scala.util.parsing.combinator.{Parsers, RegexParsers}
import org.anacletogames.game.skills._

/**
  * Created by simone on 10.01.17.
  */
trait SkillDSL extends RegexParsers with Parsers {
  def word: Parser[String] = """[a-zA-Z]+""".r ^^ { _.toString }
  val a = literal("a")
  val skillLiteral = literal("skill")
  val is = literal("is")
  def number: Parser[Int] = """(0|[1-9]\d*)""".r ^^ { _.toInt }
  def itmustbelearnedLiteral = literal("It must be learned")

  def genericSkillCategoryParser(o: SkillCategory): Parser[SkillCategory] = {
    val name = o.getClass.getSimpleName.replace("$", "")
    (literal(name) | literal(name.toLowerCase) | literal(name.toUpperCase)) ^^ (_ =>
                                                                                  o)
  }

  val skillCategoryList: Seq[SkillCategory] =
    List(Smithing, Tailoring, Farming)

  val skillCategory =
    skillCategoryList.map(genericSkillCategoryParser).reduce(_ | _)

  def skillDefition: Parser[(String, SkillCategory)] =
    word ~ is ~ a ~ skillCategory ~ skillLiteral ^^ {
      case name ~ _ ~ _ ~ skillCategory ~ _ => (name, skillCategory)
    }

  def learnTrainingDefinition: Parser[Boolean] =
    itmustbelearnedLiteral ~ word ~ literal("training") ^^ {
      case _ ~ "with" ~ _ => true
      case _ ~ "without" ~ _ => false
    }

  def skillParser: Parser[Skill] =
    skillDefition ~ opt(literal(".")) ~ opt(learnTrainingDefinition) ^^ {
      case (name, skillCategory) ~ _ ~ Some(learnWithTrain) =>
        Skill(name, skillCategory, learnWithTrain)
      case (name, skillCategory) ~ _ ~ _ => Skill(name,skillCategory)
    }
}
