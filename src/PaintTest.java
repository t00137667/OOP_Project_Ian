import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

//This file is a copy of a tutorial form the Oracle website.
//It was used to gain a better understanding of the paintComponent method.

public class PaintTest extends JComponent {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run() {
                createAndShowGUI();
                Timer t = new Timer(500,new MyListener());
                t.start();
            }
        });
    }

    private static void createAndShowGUI(){
        System.out.println("Created GUI on EDT? " +
                SwingUtilities.isEventDispatchThread());
        JFrame f = new JFrame("Paint Test");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //f.setSize(250,250);
        f.add(new MyPanel());
        f.pack();
        f.setVisible(true);
    }
}
class MyPanel extends JPanel {

    RedSquare redSquare = new RedSquare();

    /*private int squareX = 50;
    private int squareY = 50;
    private int squareW = 20;
    private int squareH = 20;*/

    public MyPanel() {
        setBorder(BorderFactory.createLineBorder(Color.black));

        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                moveSquare(e.getX(), e.getY());
            }
        });

        addMouseListener(new MouseAdapter() {
            public void mouseDragged(MouseEvent e) {
                moveSquare(e.getX(), e.getY());
            }
        });
    }


    private void moveSquare(int x, int y){

        // Current square state, stored as final variables
        // to avoid repeat invocations of the same methods.

        final int CURR_X = redSquare.getX();
        final int CURR_Y = redSquare.getY();
        final int CURR_W = redSquare.getWidth();
        final int CURR_H = redSquare.getHeight();
        final int OFFSET = 1;
        if((CURR_X!=x) || (CURR_Y!=y)){

            //Square is moving, repaint background
            // over the old square location.
            repaint(CURR_X,CURR_Y,CURR_W+OFFSET,CURR_H+OFFSET);

            // Update coordinates
            redSquare.setX(x);
            redSquare.setY(y);

            // Repaint the square at the new location.
            repaint(redSquare.getX(),redSquare.getY(),redSquare.getWidth()+OFFSET,redSquare.getHeight()+OFFSET);
            //System.out.println("X = " + redSquare.getX() + " Y = " + redSquare.getY());
        }
    }

    public Dimension getPreferredSize(){
        return new Dimension(250,200);
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        //Draw Text
        g.drawString("This is my custom Panel",10,20);

        redSquare.paintSquare(g);
    }
}
class RedSquare{
    private int xPos = 50;
    private int yPos = 50;
    private int width = 20;
    private int height = 20;

    public void setX(int xPos){
        this.xPos = xPos;
    }
    public int getX(){
        return xPos;
    }
    public void setY(int yPos){
        this.yPos = yPos;
    }
    public int getY(){
        return  yPos;
    }
    public int getWidth(){
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void paintSquare(Graphics g){
        g.setColor(Color.RED);
        g.fillRect(xPos,yPos,width,height);
        g.setColor(Color.BLACK);
        g.drawRect(xPos,yPos,width,height);
    }
}
class MyListener extends JPanel implements ActionListener{
    RedSquare slidingSquare = new RedSquare();
    @Override
    public void actionPerformed(ActionEvent e) {

        System.out.println("The timer is working");
        slideSquare(5,0);

    }
    public void slideSquare(int moveX, int y){
        int moveHorizontal = moveX;
        int moveVertical = y;
        final int CURR_X = slidingSquare.getX();
        final int CURR_Y = slidingSquare.getY();
        final int CURR_W = slidingSquare.getWidth();
        final int CURR_H = slidingSquare.getHeight();
        final int OFFSET = 1;

        repaint(CURR_X,CURR_Y,CURR_W+OFFSET,CURR_H+OFFSET);

        slidingSquare.setX(CURR_X+moveHorizontal);
        slidingSquare.setY(CURR_Y+moveVertical);

        repaint(CURR_X,CURR_Y,CURR_W+OFFSET,CURR_H+OFFSET);

    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        //Draw Text
        g.drawString("This is my custom Panel",10,20);

        slidingSquare.paintSquare(g);
    }
}
