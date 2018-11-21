import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class PlayerShip extends Ship {
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
        setxPos(this.xPos);
        setyPos(this.yPos);
        setWidth(width);
        setHeight(height);
    }

    public void paintShip(Graphics g){
        g.drawImage(playerShipImage,getxPos(),getyPos(),null);
    }

    //Setters and Getters

}
