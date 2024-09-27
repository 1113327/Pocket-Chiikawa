package main;

import entity.Npc1;
import entity.Npc2;
import object.Obj_door;

public class AssetSettter {
    Game ga;
    public AssetSettter(Game ga2){
        this.ga = ga2;
    }
    public void setObject(){
        // 撿起物件
        ga.object[0] = new Obj_door();
    }
    public void setNPC(){
        //System.out.println("Creating NPC1...");
        ga.npc[0] = new Npc1(ga, 1900, 1200);
        //System.out.println("Creating NPC2...");
        ga.npc[1] = new Npc2(ga, 2500, 700);
    }    
}