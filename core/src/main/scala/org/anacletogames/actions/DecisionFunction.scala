package org.anacletogames.actions

import com.badlogic.gdx.math.{GridPoint2, Vector2}
import org.anacletogames.entities.Entity

/**
  * Created by simone on 06.11.16.
  */
object EntityBehaviour {
    type DecisionContext=List[Entity]
    type EntityBehaviour=(Entity,DecisionContext)=>GameAction



      def reachPointBehaviour(destination:Vector2)=
      (self:Entity,entities: List[Entity])=>{

        val destX = 100F
        val destY = 100F
        val (nextStepX, nextStepY) = MoveUtil.projectStep(self, destination)
        if (entities.exists(entity => (!entity.equals(self)) && entity.overlaps(self.stageBounds.setPosition(
          self.getX + nextStepX, self.getY + nextStepY)
        )))
          NoAction
        else
          MoveTo(self, destination)

      }

  def doOnce(action:GameAction)=
    (self:Entity,entities: List[Entity])=> action



}
