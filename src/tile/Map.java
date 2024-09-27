package tile;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;

import main.Game;

public class Map {
    Game ga;
    Image background;
    Image background2;

    public boolean collision = false;
    
    public Map(Game another) {
        this.ga = another;
        getMapImage();
        getMapImage2();
    }
    
    public int getWidth() {//還沒用到
        return ga.getWidth(); // Replace 'gp' with the appropriate variable if needed
    }

    public int getHeight() {
        return ga.getHeight(); // Replace 'gp' with the appropriate variable if needed
    }

    public void getMapImage() {
        try {
            background = Toolkit.getDefaultToolkit().getImage("assets/images/maps/tumblr_inline_o47751nWfc1tmx71p_540_2.jpg");
            if (background != null) {
                //System.out.println("background loaded successfully.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void getMapImage2() {
        try {
            background2 = Toolkit.getDefaultToolkit().getImage("assets/images/maps/azalea_town_by_uhlinyaface_d5t3z55-414w-2x (3)_2.jpg");
            if (background2 != null) {
                //System.out.println("background2 loaded successfully.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void drawVision(Graphics2D g2){
        if (background != null) {
            //int mapWidth = 3240;
            //int mapHeight = 1992;

            // 根據主角的位置計算地圖的可見區域
            int visibleX = ga.myPlayer.worldX - ga.screenWidth / 2;
            int visibleY = ga.myPlayer.worldY - ga.screenHeight / 2;

            // 確保可見區域不會超出地圖的邊界，但是這個確保機制有點問題
            //visibleX = Math.max(0, Math.min(mapWidth - ga.screenWidth, visibleX));
            //visibleY = Math.max(0, Math.min(mapHeight - ga.screenHeight, visibleY));

            // 繪製地圖
            g2.drawImage(background, 0, 0, ga.screenWidth, ga.screenHeight,
                        visibleX, visibleY, visibleX + ga.screenWidth, visibleY + ga.screenHeight,
                        ga);
        }
    }

    public void drawVision2(Graphics2D g2){
        if (background2 != null) {
            //int mapWidth = 3240;
            //int mapHeight = 1992;

            // 根據主角的位置計算地圖的可見區域
            int visibleX = ga.myPlayer.worldX - ga.screenWidth / 2;
            int visibleY = ga.myPlayer.worldY - ga.screenHeight / 2;

            // 繪製地圖
            g2.drawImage(background2, 0, 0, ga.screenWidth, ga.screenHeight,
                        visibleX-3240, visibleY, visibleX-3240 + ga.screenWidth, visibleY + ga.screenHeight,
                        ga);
        }
    }
}