package entity;

import main.Game;
import main.KeyHandler;
import main.UI;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.awt.Image;

public class Player extends Entity{
    //Game ga;絕對不能加這行，它讓我六日泡湯了
    KeyHandler keyH;
    public Image monsterImage;
    public Image playersMonImage;
    public Map<Integer, MonsterInfo> monsterInfoMap;
    public MonsterInfo WildMonsterNow;
    public int MonsterNowLevel;
    public String MonsterNowName;
    public String MonsterNowType;

    private static final List<Bounds> wildAreasBounds = Arrays.asList(
        new Bounds(69, 15, 13, 7, 4, 8, Arrays.asList(
            new WildMonster(1, 0.0056), new WildMonster(2, 0.0062), new WildMonster(3, 0.0077)
        ))/*,
        new Bounds(78, 15, 2, 7, 4, 8, Arrays.asList(
            new WildMonster(1, 0.01), new WildMonster(2, 0.03), new WildMonster(3, 0.06)
        )),*/
    );

    public Player(Game ga, KeyHandler keyH) {
        super(ga);//this.ga = ga;
        this.keyH = keyH;
        screenX = ga.screenWidth / 2 - ga.tileSize / 2;// 視窗中心點 X座標
        screenY = ga.screenHeight / 2 - ga.tileSize / 2;// 視窗中心點 Y座標
        setDefaultValues();
        getPlayerImage();
        getObstacleMap(); // 初始化障礙物地圖
        //getObstacleMap2();
        initializeMonsterInfo();
        //initializeMonsterImages();
        initializePlayersMonImages();
    }

    public void setDefaultValues() {
        worldX = 1400;
        worldY = 800;
        speed = 3;
        direction = "down";
    }   

    public void getPlayerImage() {
        try {
            up = Toolkit.getDefaultToolkit().getImage("assets/images/mainPlayer/mainPlayer_back.png");
            if (up == null) System.err.println("Failed to load 'up' image.");
            down = Toolkit.getDefaultToolkit().getImage("assets/images/mainPlayer/mainPlayer_front.png");
            if (down == null) System.err.println("Failed to load 'down' image.");
            left = Toolkit.getDefaultToolkit().getImage("assets/images/mainPlayer/mainPlayer_left.png");
            if (left == null) System.err.println("Failed to load 'left' image.");
            right = Toolkit.getDefaultToolkit().getImage("assets/images/mainPlayer/mainPlayer_right.png");
            if (right == null) System.err.println("Failed to load 'right' image.");
            //System.out.println("Player images loaded successfully.");
            /*Wrong way to load images
            //import javax.imageio.ImageIO;
            up = ImageIO.read(getClass().getResourceAsStream("assets/images/Walking sprites/boy_down_1.png"));
            down = ...
            .
            .
            */
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getObstacleMap() {
        // Implement the logic to generate and return the obstacle map
        try {
            File file = new File("assets/images/maps/obstacleMap.txt");
            Scanner scanner = new Scanner(file);

            int row = 0;
            while (row < obstacleMap.length) {
                String line = scanner.nextLine();
                for (int col = 0; col < obstacleMap[0].length; col++) {
                    int obstacleChar = line.charAt(col) - '0';
                    obstacleMap[row][col] = obstacleChar;
                }
                row++;
            }
            scanner.close();
            //System.out.println("Obstacle map loaded successfully.");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public boolean canMove(int dx, int dy, int[][]obMap) {
        int targetX = (worldX + dx) / ga.tileSize;
        int targetY = (worldY + dy) / ga.tileSize;

        // 檢查目標位置是否在地圖邊界內
        if (targetX < 0 || targetY < 0 || targetX >= obMap[0].length || targetY >= obMap.length) {
            //System.out.println("cannot move");
            return false; // 超出地圖邊界，不能移動
        }

        
        /*//重要!障礙地圖Debug
        // 檢查目標位置是否是障礙物
        System.out.println("targetX: " + targetX + '\n');
        System.out.println("targetY: " + targetY+ '\n');
        System.out.println("obstacleMapOth value: " + obMap[targetY][targetX]+ '\n');*/

        //return !obMap[targetY][targetX];
        if(obMap[targetY][targetX] == 1)
            return false;
        else{
            if(obMap[targetY][targetX] == 3){
                if(wildMonMeet(targetX, targetY))
                    return false;
                return true;
            }
            return true;
        }
        // else
        //     return true;
    }

    public boolean wildMonMeet(int targetX, int targetY) {
        Random rand = new Random();
        double encounterChance = rand.nextDouble();
        for (Bounds bounds : wildAreasBounds) {
            if (bounds.contains(targetX, targetY)) {
                for (WildMonster monster : bounds.getMonsters()) {
                    if (encounterChance <= monster.getProbability()) {
                        if(monster.getId() == 1)
                            System.out.println("You meet a wild Chiikawa!");
                        else if(monster.getId() == 2)
                            System.out.println("You meet a wild Hachiware!");
                        else if(monster.getId() == 3)
                            System.out.println("You meet a wild Rabbit!");

                        loadMonsterImage(monster.getId());
                        WildMonsterNow = monsterInfoMap.get(monster.getId());
                        MonsterNowLevel = new Random().nextInt(20 - WildMonsterNow.getMinLevel() + 1) + WildMonsterNow.getMinLevel();
                        MonsterNowName = WildMonsterNow.getName();
                        MonsterNowType = WildMonsterNow.getType();
                        fightWithWild();
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void loadMonsterImage(int monsterId) {
        String imagePath = monsterInfoMap.get(monsterId).getImagePath();
        if (imagePath != null) {
            //System.out.println("a wild");
            monsterImage = Toolkit.getDefaultToolkit().getImage(imagePath);
        }
    }

    public void update() {
        // 檢查按鍵狀態並更新位置
        if (keyH.upPressed && canMove(0, -speed, obstacleMap)) {
            worldY -= speed;
            direction = "up";
        } else if (keyH.downPressed && canMove(0, speed, obstacleMap)) {
            worldY += speed;
            direction = "down";
        } else if (keyH.leftPressed && canMove(-speed, 0, obstacleMap)) {
            worldX -= speed;
            direction = "left";
        } else if (keyH.rightPressed && canMove(speed, 0, obstacleMap)) {
            worldX += speed;
            direction = "right";
        }

        collisionOn = false;
        
        // Check NPC collision
        int npcIndex = ga.cChecker.checkEntity(this, ga.npc);
        interactNPC(npcIndex);
    }

    public void fightWithWild() {
        ga.gameState = ga.battleState;
    }

    public void interactNPC(int npcIndex) {//檢查這檢查這檢查這檢查這檢查這檢查這檢查這檢查這
        if (npcIndex != 999 && ga.keyH.enterPressed) {
            //System.out.println("Hello, I am NPC!");
            ga.gameState = ga.dialogueState;
            //ga.npc[npcIndex].setDialogue();
            ga.npc[npcIndex].speak();
        }
        ga.keyH.enterPressed = false;
    }

    public void draw(Graphics2D g2){//於移動時即時畫出玩家
        Image img = null;
        switch (direction) {
            case "up":
                img = up;
                break;
            case "down":
                img = down;
                break;
            case "left":
                img = left;
                break;
            case "right":
                img = right;
                break;
        }
        if (img != null)
            g2.drawImage(img, screenX, screenY, ga.tileSize, ga.tileSize, null);
        else
            System.err.println("Player image is null for direction: " + direction);
    }

    // 初始化怪物圖片路徑
    public void initializeMonsterInfo() {
        // monsterImagePaths = new HashMap<>();
        // monsterImagePaths.put(1, "assets/images/monsters/Chiikawa.jpg");
        // monsterImagePaths.put(2, "assets/images/monsters/Hachiware.gif");
        // monsterImagePaths.put(3, "assets/images/monsters/Rabbit.jpg");
        monsterInfoMap = new HashMap<>();
        monsterInfoMap.put(1, new MonsterInfo("Chiikawa", "water", 10, "assets/images/monsters/Chiikawa.jpg"));
        monsterInfoMap.put(2, new MonsterInfo("Hachiware", "ground", 15, "assets/images/monsters/Hachiware.gif"));
        monsterInfoMap.put(3, new MonsterInfo("Rabbit", "fire", 12, "assets/images/monsters/Rabbit.jpg"));
    }
    public void initializePlayersMonImages() {
        try {
            playersMonImage = Toolkit.getDefaultToolkit().getImage("assets/images/monsters/RabbitBack.jpg");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}