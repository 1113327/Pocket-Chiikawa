package entity;

import java.awt.Graphics2D;
import java.awt.Image;
//import java.awt.image.BufferedImage;wrong

import main.Game;

public class Entity {
    Game ga;
    public int x = 0, y = 0, speed = 0;
    public int worldX = 0, worldY = 0;
    public String direction;

    public Image image;
    // Declare instance variables
    public Image up, down, left, right;
    /*Wrong way to Declare images
    public BufferedImage up, down, left, right;*/

    public int screenX = 0;
    public int screenY = 0;
    public int[][] obstacleMap = new int[29][100];
    //public int[][] obstacleMap2 = new int[30][48];
    public boolean collisionOn = false;//new
    public int actionLockCounter = 0;//new
    String dialogues[] = new String[20];
    int dialogueIndex = 0;

    public Entity(Game ga2){
        this.ga =ga2;//abstract class
    }

    public void setAction() {}

    public void draw(Graphics2D ga2){
        // Implement the logic to draw the entity
        if (worldX >= ga.myPlayer.worldX && worldX < ga.myPlayer.worldX + ga.screenWidth &&
            worldY >= ga.myPlayer.worldY && worldY < ga.myPlayer.worldY + ga.screenHeight) {
                int screenX = worldX - ga.myPlayer.worldX;
                int screenY = worldY - ga.myPlayer.worldY;
            ga2.drawImage(image, screenX, screenY, ga.tileSize, ga.tileSize, null);
        }
    }

    public void speak() {
        if(dialogues[dialogueIndex] == null) {
            dialogueIndex = 0;
        }
        ga.ui.currentDialogue = dialogues[dialogueIndex];
        //System.out.println(dialogues[dialogueIndex]);debug
        dialogueIndex++;
    }
    /*public boolean isCollidingWith(Entity other) {
        return this.worldX == other.worldX && this.worldY == other.worldY;
    }*/
}