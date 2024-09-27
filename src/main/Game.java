package main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import javax.swing.JPanel;
import entity.Entity;
import entity.Player;
import object.SuperObject;
import tile.Map;
//import java.awt.Font;

public class Game extends JPanel implements Runnable{

    // Screen settings
    final int originTileSize = 16; // 16 x 16  per tile
    final int scale = 4;
    public final int tileSize = originTileSize * scale; // 64 x 64 per tile
    final int maxScreenCol = 16;
    final int maxScreenRow = 9;
    public final int screenWidth = tileSize * maxScreenCol; // 1024 pixels
    public final int screenHeight = tileSize * maxScreenRow; // 576 pixels
    //public final int maxWorldCol = 42;
    //public final int maxWorldRow = 26;

    // UI
    public UI ui = new UI(this);
    
    //NPC
    public Entity npc[] = new Entity[10];

    // Game state
    public int gameState;
    public final int playState = 1;
    public final int menuState = 2;
    public final int dialogueState = 3;
    public final int battleState = 4;

    //FPS
    int FPS = 60;
    Thread gameThread;

    public CollisionChecker cChecker = new CollisionChecker(this);
    public KeyHandler keyH = new KeyHandler(this);
    public Player myPlayer = new Player(this, keyH);
    public Map mapBg = new Map(this);
    //public Map mapBg2 = new Map(this);
    public SuperObject object[] = new SuperObject[10];

    public Game() {
        AssetSettter assetSetter = new AssetSettter(this);
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
        assetSetter.setNPC();
        //assetSetter.setObject();
    }
    
    public void startGameThread() {
        gameState = playState;
        gameThread = new Thread(this);
        gameThread.start();
    }
    
    
    int drawCount = 0;

    @Override
    public void run() {
        double drawInterval = 1000000000/FPS; // 0.01666 seconds
        //double nextDrawTime = System.nanoTime() + drawInterval;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime = 0;
        long timer = 0;
        
        while(gameThread != null) {
            //System.out.println("Game is running");
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;
            if(delta >= 1) {
                if(gameState == playState){
                    myPlayer.update();
                }
                if(gameState == menuState) {
                    //remember to deal with this
                }
                if(gameState == battleState) {
                    //remember to deal with this
                }
                repaint();
                delta--;
                drawCount++;
            }
            if(timer >= 1000000000){
                //System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }
            /*
            // Another way to control FPS
            update();// Step1
            repaint();//Step2
            try {
                double waitTime = nextDrawTime - System.nanoTime();
                if(waitTime > 0) {
                    Thread.sleep((long)(waitTime/1000000));
                }
                else if(waitTime < 0) {
                    waitTime = 0;
                }
                nextDrawTime += drawInterval;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
        }
    }

    public JPanel getMainPanel() {
        return this;
    }

    public void paintComponent(Graphics g) {//不能刪
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        mapBg.drawVision(g2);
        mapBg.drawVision2(g2);
        //mapBg2.drawVision(g2);
        for(int i = 0; i < npc.length; i++) {
            if(npc[i] != null) {
                npc[i].draw(g2);
            }
        }
        myPlayer.draw(g2);
        ui.draw(g2);
        g2.dispose();
    }
}