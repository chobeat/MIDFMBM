package org.anacletogames.odl.debug

import org.anacletogames.odl.SkillDSL

/**
  * Created by simone on 10.01.17.
  */
object SkillDSLDebug extends SkillDSL {
  def main(args: Array[String]): Unit = {

    def printParsed[T](r:ParseResult[T]):Unit=r match{
      case Success(result,next)=>println(result)
      case Failure(msg,next)=>println(msg)
      case Error(msg,next)=>println(msg)

    }

    val s = parse(parseSkill, "Blacksmithing is a smithing skill")
    printParsed(s)

    val s2 = parse(parseSkill, "Plowing is a farming skill")
    printParsed(s2)


  }

}
