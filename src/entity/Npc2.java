package entity;

import java.awt.Toolkit;
import main.Game;

public class Npc2 extends Entity {
    //Game gp;
    
    public Npc2(Game game, int wX, int wY) {
        super(game); // Explicitly invoke the constructor of the superclass Entity
        this.worldX = wX;
        this.worldY = wY;
        getNpc2Image();
        setDialogue();//如果忘記加這行會很慘
    }

    private void getNpc2Image() {
        try {
            image = Toolkit.getDefaultToolkit().getImage("assets/images/npc/npc2/npc2_front.png");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setDialogue() {
        dialogues[0] = "Hello, I'm NPC2!";
        dialogues[1] = "I'm here.";
        dialogues[2] = "You can ask me.";
    }

    public void speak() {
        // Do this  character's specific stuff
        super.speak();
    }
}