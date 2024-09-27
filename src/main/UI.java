package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Button;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import entity.MonsterInfo;

public class UI {//彈出視窗的java檔，如對話框、暫停等
    Game game;
    Graphics2D g2;
    Font arial_40, arial_80B;
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    public boolean gameFinished = false;
    public String currentDialogue = "";
    private Frame battleFrame;
    public MonsterInfo WildMonsterNow;
    

    public UI(Game ga) {
        this.game = ga;
        arial_40 = new Font("Arial", Font.PLAIN, 40);
        arial_80B = new Font("Arial", Font.BOLD, 80);
    }

    public void showMessage(String msg) {
        message = msg;
        messageOn = true;
        //messageCounter = 0;
    }

    public void draw(Graphics2D g2){
        this.g2 = g2;

        g2.setFont(arial_40);
        g2.setColor(Color.WHITE);

        // message
        if(messageOn == true){
            g2.setFont(g2.getFont().deriveFont(Font.PLAIN,30.0f));
            g2.drawString(message, game.tileSize / 2, game.tileSize * 5);

            messageCounter++;
            if(messageCounter > 120){
                messageCounter = 0;
                messageOn = false;
            }
        }

        // play state
        if(game.gameState == game.playState){}

        // menu state
        if(game.gameState == game.menuState){
            drawPauseScreen();
        }

        // dialogue state
        if(game.gameState == game.dialogueState){
            drawDialogueScreen();
        }

        // battle state
        if(game.gameState == game.battleState) {
            drawBattleScene(g2);
        }
    }



    public void drawBattleScene(Graphics2D g2) {
        if (battleFrame != null) {
            return;
        }
        // 戰鬥場景繪製邏輯
        int x = 100;
        int y = 100;
        int width = 800;
        int height = 400;

        // Color c = new Color(255, 255, 255, 250);
        // g2.setColor(c);
        // g2.fillRect(x, y, width, height);

        // 創建戰鬥子視窗
        battleFrame = new Frame() {
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                Graphics2D g2 = (Graphics2D) g;
                // 繪製怪物圖片在戰鬥視窗的上方中心
                if (game.myPlayer.monsterImage != null) {
                    int imgWidth = game.myPlayer.monsterImage.getWidth(this);
                    //int imgHeight = game.myPlayer.monsterImage.getHeight(this);
                    int imgX = (width - imgWidth) / 2;
                    int imgY = 20; // 顯示在上方，距離頂部20像素
                    g.drawImage(game.myPlayer.monsterImage, imgX, imgY, this);

                    // 繪製怪物信息在圖片的左方
                    g2.setColor(Color.BLACK);
                    g2.setFont(new Font("Arial", Font.PLAIN, 18));
                    int textX = imgX - 300;
                    int textY = imgY + 30;
                    
                    g2.drawString(game.myPlayer.MonsterNowName, textX, textY);//名字
                    g2.drawString("Level " + String.valueOf(game.myPlayer.MonsterNowLevel), textX, textY + 30);//等級
                    g2.drawString(game.myPlayer.MonsterNowType, textX, textY + 60);//屬性
                }
                if (game.myPlayer.playersMonImage != null) {
                    int imgWidth = game.myPlayer.playersMonImage.getWidth(this);
                    int imgX = (width - imgWidth) / 3 * 2;
                    g.drawImage(game.myPlayer.playersMonImage, imgX, 230, this);
                }
            }
        };
        battleFrame.setLayout(null); // 使用絕對佈局
        battleFrame.setSize(width, height);
        battleFrame.setLocation(x, y);

        // 設置背景顏色
        // battleFrame.setBackground(new Color(255, 255, 255, 250));這行很危

        // 創建面板來包含按鈕
        Panel buttonPanel = new Panel();
        buttonPanel.setLayout(null);
        buttonPanel.setBounds(50, height - 150, 300, 100); // 放置在左下角
        battleFrame.add(buttonPanel);

        // 創建4個按鈕
        Button button1 = new Button("Fireball");
        Button button2 = new Button("FirePunch");
        Button button3 = new Button("Flamethrower");
        Button button4 = new Button("Blast Burn");

        // 設置按鈕位置和大小
        button1.setBounds(0, 0, 140, 40);
        button2.setBounds(160, 0, 140, 40);
        button3.setBounds(0, 50, 140, 40);
        button4.setBounds(160, 50, 140, 40);

        // 添加按鈕到面板
        buttonPanel.add(button1);
        buttonPanel.add(button2);
        buttonPanel.add(button3);
        buttonPanel.add(button4);

        // 創建並添加逃跑按鈕
        Button runButton = new Button("Run");
        runButton.setBounds(width - 160, height - 60, 140, 40); // 放置在右下角
        runButton.addActionListener(new ActionListener()  {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.gameState = game.playState; // 回到遊戲狀態
                battleFrame.dispose(); // 關閉戰鬥視窗
                battleFrame = null; // 重置變量
            }
        });
        battleFrame.add(runButton);
        battleFrame.setVisible(true);
    }
    

    public void drawPauseScreen(){
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,80.0f));
        String text = "PAUSED";
        int x = getXforCenteredText(text);
        int y = game.screenHeight/2;
        g2.drawString("Game Paused", x, y);
    }

    public void drawDialogueScreen(){//對話視窗有關
        //window
        int x = game.screenWidth / 5;
        int y = game.screenHeight / 3*2;
        int width = game.screenWidth / 5 *3;
        int height = game.screenHeight / 3;//參數調一下，看看效果
        drawSubWindow(x, y, width, height);
        
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,28.0f));
        x += game.tileSize;
        y += game.tileSize;
        for(String line : currentDialogue.split("\n")){
            g2.drawString(line, x, y);
            y += 40;
        }
        //g2.drawString(currentDialogue, x, y);
    }

    public void drawSubWindow(int x, int y, int width, int height){//對話視窗有關
        Color c = new Color(0, 0, 0, 210);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height,35,35);
        //g2.fillRect(x, y, width, height);

        c= new Color(255, 255, 255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x + 5, y + 5, width - 10, height - 10,25,25);

    }

    public int getXforCenteredText(String text){//移動中間的Pause字樣
        int length = (int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();
        return (game.screenWidth - length) / 3;
        //return (game.screenWidth - length) / 2;
    }

    
}