import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class PlayerShip {
    public BufferedImage playerShipImage;

    //Initial Positioning (Center of bottom-ish)
    private int xPos = 480;
    private int yPos = 750;
    private int width = 62;
    private int height = 48;

    PlayerShip(){
        // Attempt to load in the image of the player ship.
        try {
            BufferedImage temp = ImageIO.read(getClass().getResource("Resources/Ships.png"));
            playerShipImage= temp.getSubimage(62, 0, 62, 48);
            System.out.println("Player Ship Image read");

        } catch (IOException ex) {
            System.out.println("Error in read, exiting.");
            System.exit(0);
        }
    }

    public void paintShip(Graphics g){
        g.drawImage(playerShipImage,getxPos(),getyPos(),null);
    }

    //Setters and Getters

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

    public void setHeight(int height) {
        this.height = height;
    }

    public int getHeight() {
        return height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getWidth() {
        return width;
    }
}
