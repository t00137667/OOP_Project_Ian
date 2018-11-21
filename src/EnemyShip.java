import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class EnemyShip extends Ship {


    BufferedImage enemyShipImage;
    BufferedImage temp;

    EnemyShip(){

        chooseShip(0);
        setSpawn(0);
    }

    EnemyShip(int ship, int spawn){
        chooseShip(ship);
        setSpawn(spawn);
    }

    private void chooseShip(int ship){
        // Attempt to load in the image of the enemy ships.
        try {
            temp = ImageIO.read(getClass().getResource("Resources/Corrupted1.png"));
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

        setyPos(-50);
        switch (spawn){
            case 1:
                setxPos(92);
                break;
            case 2:
                setxPos(185);
                break;
            case 3:
                setxPos(304);
                break;
            case 4:
                setxPos(386);
                break;
            case 5:
                setxPos(486);
                break;
            case 6:
                setxPos(632);
                break;
            case 7:
                setxPos(744);
                break;
            case 8:
                setxPos(844);
                break;
            case 9:
                setxPos(928);
                break;
            default:
                setxPos(554);
                break;

        }
    }

    public void setEnemyShipImage(BufferedImage enemyShipImage) {
        this.enemyShipImage = enemyShipImage;
    }

    public void paintShip(Graphics g){
        g.drawImage(enemyShipImage,getxPos(),getyPos(),null);
    }
}
