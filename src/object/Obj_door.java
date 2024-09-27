package object;

import java.awt.Toolkit;

public class Obj_door extends SuperObject{
    public Obj_door() {
        this.name = "door";
        try {
            image = Toolkit.getDefaultToolkit().getImage("assets/images/objects/door.jpg");
            if (image != null) {
                //System.out.println("door image loaded successfully.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.collision = true;
    }
}
