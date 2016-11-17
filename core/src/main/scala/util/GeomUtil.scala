package util

import com.badlogic.gdx.math.{Circle, Rectangle, Shape2D, Vector2}
import org.anacletogames.actions.GameAction.ActionContext
import org.anacletogames.entities.{CustomIntersector, Entity}

/**
  * Created by simone on 14.11.16.
  */
/*
object GeomUtil {
   def overlapsInContext(context:ActionContext,s:Shape2D):Boolean={
     val shapes = context.collidables - s
     !shapes.exists(shape =>CustomIntersector.overlaps(shape,s))

   }
  def overlappingShapesInContext(context:ActionContext,s:Shape2D):Iterable[Shape2D]={
    val shapes = context.collidables - s
    shapes.filter(shape=>CustomIntersector.overlaps(shape,s))
  }
  def containsPointInContext(context: ActionContext,p:Vector2):Boolean={

    !context.collidables.exists(shape=>CustomIntersector.contains(shape,p))
  }

  def getSuggestedAdjacent(shape:Shape2D):Seq[Vector2]={

    shape match{
      case r:Rectangle=>
        val center=new Vector2(0,0)
        r.getCenter(center)
        List(
          new Vector2(center.x+r.getWidth/2+1,center.y),
          new Vector2(center.x-(r.getWidth/2+1),center.y),
          new Vector2(center.x,center.y+r.getWidth/2+1),
          new Vector2(center.x,center.y-(r.getWidth/2+1)))

      case c:Circle=>
        List(
          new Vector2(c.x+c.radius/2+1,c.y),
          new Vector2(c.x-(c.radius/2+1),c.y),
          new Vector2(c.x,c.y+c.radius/2+1),
          new Vector2(c.x,c.y-(c.radius/2+1)))
    }
  }
}
*/