import javax.swing.*;
import java.awt.*;

public class PaintTest extends JComponent {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run() {
                createAndShowGUI();
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

    private int squareX = 50;
    private int squareY = 50;
    private int squareW = 20;
    private int squareH = 20;

    public MyPanel(){
        setBorder(BorderFactory.createLineBorder(Color.black));

        addMouseListener(new MouseAdapter(){
            public void mousePressed(MouseEvent e){
                moveSquare(e.getX(),getY());
            }
        });
    }
    public Dimension getPreferredSize(){
        return new Dimension(250,200);
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        //Draw Text
        g.drawString("This is my custom Panel",10,20);
    }
}
