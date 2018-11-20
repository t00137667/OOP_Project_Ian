import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.util.ArrayList;

public class SpaceGame extends JComponent {
    public static void main(String[] args) {
        showGUI();
    }

    private static void showGUI(){
        JFrame jFrame = new JFrame("Space Game");
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.add(new GamePanel());
        jFrame.pack();
        jFrame.setResizable(false);
        jFrame.setVisible(true);
    }

}

class GamePanel extends JPanel implements ActionListener {


    private Timer gameTimer = new Timer(10,this);
    private Image background;
    PlayerShip playerShip = new PlayerShip();
    ArrayList<EnemyShip> enemyShips = new ArrayList();
    EnemyShip enemyShip;

    private int counter = 0;
    private int delay = 0;
    private int lastSpawn = 0;
    boolean onDelay = false;

    private boolean isAPressed = false;
    private boolean isDPressed = false;
    private boolean isWPressed = false;
    private boolean isSPressed = false;
    private boolean isSpacePressed = false;

    GamePanel(){
        //Adding a border to the panel
        setBorder(BorderFactory.createLineBorder(Color.black));
        setFocusable(true);

        addKeyListener(new KeyAdapter() {
            public void keyPressed (KeyEvent e){
                input(e);
            }
        });

        addKeyListener(new KeyAdapter() {
            public void keyReleased (KeyEvent e){
                inputStop(e);
            }
        });


        // Loading the Background Image
        try {
            background = ImageIO.read(getClass().getResource("Resources/skybox/1.png"));
            System.out.println("Background Loaded");

        } catch (IOException ex) {
            System.out.println("Error in read, exiting.");
            System.exit(0);
        }

        gameTimer.start();
    }


    private void input(KeyEvent e){
        //System.out.println("Testing Input");
        if (e.getKeyCode() == KeyEvent.VK_W){
            //System.out.println("W Pressed");
            isWPressed = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_A){
            //System.out.println("A Pressed");
            isAPressed = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_S){
            //System.out.println("S Pressed");
            isSPressed = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_D){
            //System.out.println("D Pressed");
            isDPressed = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_SPACE){
            //System.out.println("Space Pressed");
            isSpacePressed = true;
        }
    }

    private void inputStop(KeyEvent e){
        //System.out.println("Testing InputStop");
        if (e.getKeyCode() == KeyEvent.VK_W){
            //System.out.println("W Pressed");
            isWPressed = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_A){
            //System.out.println("A Released");
            isAPressed = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_S){
            //System.out.println("S Pressed");
            isSPressed = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_D){
            //System.out.println("D Pressed");
            isDPressed = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_SPACE){
            //System.out.println("Space Pressed");
            isSpacePressed = false;
        }
    }

    private void movement(){
        /* This controls the logic of the direction of movement
        *  Then calls the appropriate movement
        *  */

        //No movement
        if (!isAPressed && !isDPressed && !isWPressed && !isSPressed)
            dontMove();

        //Move Left Only
        if (isAPressed && !isDPressed && !isWPressed && !isSPressed && playerShip.getxPos()>50)
            moveLeft();

        //Move Right Only
        if (isDPressed && !isAPressed && !isWPressed && !isSPressed && playerShip.getxPos()<930)
            moveRight();

        //Move Forward Only
        if (!isAPressed && !isDPressed && isWPressed && !isSPressed && playerShip.getyPos()>512)
            moveUp();

        //Move Backwards Only
        if (!isAPressed && !isDPressed && !isWPressed && isSPressed && playerShip.getyPos()<930)
            moveDown();

        // Both Sideways Movement UP
        if (isAPressed && isDPressed && isWPressed && !isSPressed && playerShip.getyPos()>512)
            moveUp();

        //Both Sideways Movement DOWN
        if (isAPressed && isDPressed && !isWPressed && isSPressed && playerShip.getyPos()<930)
            moveDown();

        //Both Vertical Movement LEFT
        if (isAPressed && !isDPressed && isWPressed && isSPressed && playerShip.getxPos()>50)
            moveLeft();

        //No Vertical Movement RIGHT
        if (!isAPressed && isDPressed && isWPressed && isSPressed && playerShip.getxPos()<930)
            moveRight();

        //Move Up Left
        if (isAPressed && !isDPressed && isWPressed && !isSPressed && playerShip.getxPos()>50 && playerShip.getyPos()>512)
            moveUpLeft();

        //Move Up Right
        if (!isAPressed && isDPressed && isWPressed && !isSPressed && playerShip.getxPos()<930 && playerShip.getyPos()>512)
            moveUpRight();

        //Move Down Left
        if (isAPressed && !isDPressed && !isWPressed && isSPressed && playerShip.getxPos()>50 && playerShip.getyPos()<930)
            moveDownLeft();

        //Move Down Right
        if (!isAPressed && isDPressed && !isWPressed && isSPressed && playerShip.getxPos()<930 && playerShip.getyPos()<930)
            moveDownRight();

        //All Pressed
        if (isAPressed && isDPressed && isWPressed && isSPressed)
            dontMove();
    }

    private void moveUp(){
        movePlayer(0,-2);
    }
    private void moveDown(){
        movePlayer(0,2);
    }
    private void moveLeft(){
        movePlayer(-2,0);
    }
    private void moveRight(){
        movePlayer(2,0);
    }
    private void moveUpLeft(){
        movePlayer(-1,-1);
    }
    private void moveUpRight(){
        movePlayer(1,-1);
    }
    private void moveDownLeft(){
        movePlayer(-1,1);
    }
    private void moveDownRight(){
        movePlayer(1,1);
    }
    private void dontMove(){
        movePlayer(0,0);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        //System.out.println("Timer tick: ");

        //Update the player ship location.
        //Callable method that parses the logic and moves as required.
        movement();

        spawnEnemies();

        moveEnemies();

    }

    private boolean canSpawn(){
        counter++;

        if (counter < delay){
            onDelay = true;
        }
        else{
            onDelay = false;
            delay = (int)Math.floor(Math.random()*300);
            counter = 0;
        }

        if (onDelay){
            return false;
        }


        return true;
    }

    private void spawnEnemies(){

        if (canSpawn())
        {
            System.out.println("Enemy spawned");
            int ship = (int)(Math.random()*4);
            int spawn = (int)(Math.random()*10);
            enemyShip = new EnemyShip(ship,spawn);
            enemyShips.add(enemyShip);
        }



    }

    //Method to control the movement of the Player Ship
    private void movePlayer(int xDirection, int yDirection){

        int moveHorizontal = xDirection;
        int moveVertical = yDirection;
        final int posX = playerShip.getxPos();
        final int posY = playerShip.getyPos();
        final int width = playerShip.getWidth();
        final int height = playerShip.getHeight();
        final int OFFSET = 1;
        //System.out.println("?");
        repaint(posX,posY,width+OFFSET,height+OFFSET);
        //System.out.println(xDirection);
        playerShip.setxPos(posX+moveHorizontal);
        //System.out.println(yDirection);
        playerShip.setyPos(posY+moveVertical);

        repaint(posX,posY,width+OFFSET,height+OFFSET);
    }

    private void moveEnemies(){
        final int OFFSET = 1;
        for(EnemyShip e : enemyShips){
            int y = e.getyPos();
            repaint(e.getxPos(),e.getyPos(),e.getWidth()+OFFSET,e.getHeight()+OFFSET);
            y--;
            e.setyPos(y);
            repaint(e.getxPos(),e.getyPos(),e.getWidth()+OFFSET,e.getHeight()+OFFSET);
        }
    }

    public Dimension getPreferredSize(){
        return new Dimension(1024,1024);
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(background,0,0,null);
        g.drawString("This is my custom Panel",10,20);
        playerShip.paintShip(g);
        for(EnemyShip e : enemyShips){
            e.paintShip(g);
        }
    }
}
