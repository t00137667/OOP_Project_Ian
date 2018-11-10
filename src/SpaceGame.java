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

class GamePanel extends JPanel implements ActionListener, KeyListener {


    Timer gameTimer = new Timer(10,this);
    Image background;
    PlayerShip playerShip = new PlayerShip();
    int xDirection = 0;
    int yDirection = 0;

    GamePanel(){
        //Adding a border to the panel
        setBorder(BorderFactory.createLineBorder(Color.black));
        setFocusable(true);

        addKeyListener(new KeyAdapter() {
            public void keyPressed (KeyEvent e){
                direction(e);
            }
        });

        addKeyListener(new KeyAdapter() {
            public void keyReleased (KeyEvent e){
                xDirection = 0;
                yDirection = 0;
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
    @Override
    public void keyTyped (KeyEvent e){
        //direction(e);
    }
    @Override
    public void keyPressed (KeyEvent e){
        direction(e);
    }
    @Override
    public void keyReleased (KeyEvent e){

        xDirection = 0;
        yDirection = 0;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        //System.out.println("Timer tick: ");

        movePlayer(xDirection,yDirection);

    }

    public void direction(KeyEvent e){
        System.out.println("Testing Input");
        if (e.getKeyCode() == KeyEvent.VK_W){
            System.out.println("W Pressed");
            xDirection = 0;
            yDirection = -1;
        }
        if (e.getKeyCode() == KeyEvent.VK_A){
            System.out.println("A Pressed");
            xDirection = -1;
            yDirection = 0;
        }
        if (e.getKeyCode() == KeyEvent.VK_S){
            System.out.println("S Pressed");
            xDirection = 0;
            yDirection = 1;
        }
        if (e.getKeyCode() == KeyEvent.VK_D){
            System.out.println("D Pressed");
            xDirection = 1;
            yDirection = 0;
        }
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
        System.out.println(xDirection);
        playerShip.setxPos(posX+moveHorizontal);
        System.out.println(yDirection);
        playerShip.setyPos(posY+moveVertical);

        repaint();
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
