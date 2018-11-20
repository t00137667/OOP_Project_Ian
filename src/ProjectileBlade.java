import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ProjectileBlade {
    public BufferedImage projectileImage;
    private BufferedImage weapon1;

    private int xPos;
    private int yPos;
    private int width;
    private int height;

    ProjectileBlade(){
        try{
            weapon1 = ImageIO.read(getClass().getResource("Resources/Wpn1.png"));
            projectileImage= weapon1.getSubimage(108, 160, 22, 22);
            System.out.println("Weapon Image read");
        } catch (IOException ex){
            System.out.println("Error in read, exiting.");
            System.exit(0);
        }
    }

    ProjectileBlade(int xPos, int yPos){
        try{
            weapon1 = ImageIO.read(getClass().getResource("Resources/Wpn1.png"));
            projectileImage= weapon1.getSubimage(108, 160, 22, 22);
            System.out.println("Weapon Image read");
        } catch (IOException ex){
            System.out.println("Error in read, exiting.");
            System.exit(0);
        }

        setxPos(xPos);
        setyPos(yPos);

    }

    public void paintWeapon(Graphics g){
        g.drawImage(projectileImage,getxPos(),getyPos(),null);
    }

    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    public int getxPos() {
        return xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public void setyPos(int yPos) {
        this.yPos = yPos;
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
}
