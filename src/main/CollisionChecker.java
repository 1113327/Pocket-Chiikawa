package main;

import entity.Entity;
import entity.Player;

// Check NPC or Monsters
public class CollisionChecker {
    Game gp;
    public CollisionChecker(Game gp) {
        this.gp = gp;
    }
    /*gp.tileSize==64
    target[i].worldX==1900
    target[i].worldY==1200*/

    public int checkEntity(Player me, Entity[] target) {
        int index = 999;
        for (int i = 0; i < target.length; i++) {
            if(target[i] != null){
                if((target[i].worldX-450) - me.worldX < 0)//在npc右方
                    continue;
                if((target[i].worldY-250) - me.worldY > 0)//在npc上方
                    continue;
                if ((target[i].worldX-450) - me.worldX < gp.tileSize && me.worldY - (target[i].worldY-250) < gp.tileSize) {
                    me.collisionOn = true;
                    index = i;
                    //System.out.println(i);
                    //System.out.println(me.worldX);
                    //System.out.println(target[i].worldX);
                    //System.out.println(me.worldY);
                    break;
                }
            }
        }
        return index;
    }
}