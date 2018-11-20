import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class EnemyShip {

    private int xPos;
    private int yPos;
    private int width;
    private int height;

    BufferedImage enemyShipImage;
    BufferedImage temp;

    EnemyShip(){
        // Attempt to load in the image of the player ship.
        chooseShip(0);
    }

    private void chooseShip(int ship){
        try {
            temp = ImageIO.read(getClass().getResource("resources/Corrupted1.png"));
            System.out.println("Enemy Ship Image read");

        } catch (IOException ex) {
            System.out.println("Error in read, exiting.");
            System.exit(0);
        }
        switch (ship)
        {
            case 1:setEnemyShipImage(temp.getSubimage(0, 0, 62, 58));
                setWidth(62);setHeight(58);
                break;
            case 2:setEnemyShipImage(temp.getSubimage(134, 0, 58, 58));
                setWidth(58);setHeight(58);
                break;
            default:setEnemyShipImage(temp.getSubimage(68, 0, 58, 58));
                setWidth(58);setHeight(58);
                break;
        }
    }

    public void setSpawn(int spawn){
 
    }

    public void paintShip(Graphics g){
        g.drawImage(enemyShipImage,getxPos(),getyPos(),null);
    }

    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    public int getxPos() {
        return xPos;
    }

    public void setyPos(int yPos) {
        this.yPos = yPos;
    }

    public int getyPos() {
        return yPos;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getWidth() {
        return width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getHeight() {
        return height;
    }

    public void setEnemyShipImage(BufferedImage enemyShipImage) {
        this.enemyShipImage = enemyShipImage;
    }
}
