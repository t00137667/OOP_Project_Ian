import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;

public class SpaceGame extends JComponent {
    public static void main(String[] args) {
        showGUI();
    }

    private static void showGUI(){
        JFrame jFrame = new JFrame("Space Game");
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.add(new GamePanel());
        jFrame.pack();
        jFrame.setVisible(true);
    }

}

class GamePanel extends JPanel implements ActionListener {


    Timer gameTimer = new Timer(10,this);
    Image background;
    PlayerShip playerShip = new PlayerShip();
    int xDirection = 0;
    int yDirection = 0;
    boolean isAPressed = false;
    boolean isDPressed = false;

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
            background = ImageIO.read(getClass().getResource("skybox/1.png"));
            System.out.println("Image read");

        } catch (IOException ex) {
            System.out.println("Error in read, exiting.");
            System.exit(0);
        }

        gameTimer.start();


    }


    public void input(KeyEvent e){
        //System.out.println("Testing Input");
        if (e.getKeyCode() == KeyEvent.VK_W){
            System.out.println("W Pressed");
        }
        if (e.getKeyCode() == KeyEvent.VK_A){
            System.out.println("A Pressed");
            isAPressed = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_S){
            System.out.println("S Pressed");
        }
        if (e.getKeyCode() == KeyEvent.VK_D){
            System.out.println("D Pressed");
            isDPressed = true;
        }
    }

    public void inputStop(KeyEvent e){
        //System.out.println("Testing InputStop");
        if (e.getKeyCode() == KeyEvent.VK_W){
            System.out.println("W Pressed");
        }
        if (e.getKeyCode() == KeyEvent.VK_A){
            System.out.println("A Released");
            isAPressed = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_S){
            System.out.println("S Pressed");
        }
        if (e.getKeyCode() == KeyEvent.VK_D){
            System.out.println("D Pressed");
            isDPressed = false;
        }
    }

    public void movement(){
        //This controls the logic of the direction of movement
        //Then calls the appropriate movement
        if (!isAPressed && !isDPressed)
            movePlayer(0,0);
        if (isAPressed && !isDPressed)
            movePlayer(-2,0);
        if (isDPressed && !isAPressed)
            movePlayer(2,0);
        if(isAPressed && isDPressed)
            movePlayer(0,0);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        //System.out.println("Timer tick: ");

        //Update the player ship location.
        //Callable method that parses the logic and moves as required.
        movement();

    }

    //Method to control the movement of the Player Ship
    public void movePlayer(int xDirection, int yDirection){

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

    public Dimension getPreferredSize(){
        return new Dimension(1024,1024);
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(background,0,0,null);
        g.drawString("This is my custom Panel",10,20);
        playerShip.paintShip(g);
    }
}
