package entity;

import java.awt.Toolkit;
import main.Game;

public class Npc1 extends Entity {
    //Game gp;

    public Npc1(Game game, int wX, int wY) {
        super(game); // Explicitly invoke the constructor of the superclass Entity
        this.worldX = wX;
        this.worldY = wY;
        getNpc1Image();
        setDialogue();//如果忘記加這行會很慘
    }

    private void getNpc1Image() {
        try {
            image = Toolkit.getDefaultToolkit().getImage("assets/images/npc/npc1/npc1_front.png");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setDialogue() {
        this.dialogues[0] = "Hello, I'm NPC1!";
        dialogues[1] = "I'm here to help you.";
        dialogues[2] = "You can ask me anything.";
    }

    public void speak() {
        // Do this  character's specific stuff
        super.speak();
    }
}