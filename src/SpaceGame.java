import javafx.embed.swing.JFXPanel;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class SpaceGame extends JComponent {
    static JFrame jFrame;
    static JPanel mPanel = new JPanel();
    static Dimension frameDimension = new Dimension(1024,1024);
    static Score score = new Score();

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run() {
                showGUI();
                JFXPanel fxPanel = new JFXPanel();
            }
        });

    }

    private static void showGUI(){
        jFrame = new JFrame("Space Game");
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setMinimumSize(frameDimension);

        mPanel.setBackground(Color.black);
        jFrame.add(mPanel);
        jFrame.pack();
        jFrame.setResizable(false);
        jFrame.setVisible(true);
        displayMenu();

    }

    public static void displayMenu(){
        mPanel.removeAll();
        mPanel.add(new MenuPanel());
        mPanel.revalidate();
        mPanel.repaint();
    }

    public static void startGame(){

        mPanel.removeAll();
        mPanel.add(new GamePanel());
        mPanel.revalidate();
        mPanel.repaint();

    }

}

class MenuPanel extends JPanel implements ActionListener {

    Image background;
    MenuPanel(){
        setBorder(BorderFactory.createLineBorder(Color.black));
        setFocusable(false);
        try{
            background = ImageIO.read(getClass().getResource("Resources/skybox/5.png"));
            System.out.println("Menu Background loaded");


        }catch (IOException ex){
            System.out.println("Error in background read.");
            setBackground(Color.LIGHT_GRAY);
        }

        FlowLayout flowLayout = new FlowLayout();
        this.setLayout(flowLayout);

        JLabel name = new JLabel("The Pillars of Oop");
        Font font = new Font("sans-serif",Font.BOLD,80);
        name.setFont(font);
        Color space = new Color(60,28,49);
        name.setForeground(space);
        name.setLocation(0,0);
        name.setMinimumSize(buttonSize());
        name.setPreferredSize(buttonSize());
        name.setMaximumSize(buttonSize());
        name.setHorizontalAlignment(0);
        name.setVisible(true);
        this.add(name);

        JLabel start = new JLabel("Start");
        Font buttonFont = new Font("sans-serif",Font.BOLD,40);
        start.setFont(buttonFont);
        Color buttons = new Color(20,80,81);
        start.setForeground(buttons);
        start.setLocation(0,201);
        start.setMinimumSize(buttonSize());
        start.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("Start Button Clicked");
                //setVisible(false);
                SpaceGame.startGame();
            }

        });
        start.setVisible(true);
        this.add(start);

        JLabel scores = new JLabel("High Scores");



    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public Dimension buttonSize(){
        return new Dimension(1000,200);
    }

    public Dimension getPreferredSize(){
        return new Dimension(1024,1024);
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(background,0,0,null);
        //System.out.println("Background Painted");

    }
}

/**
 *
 */
class GamePanel extends JPanel implements ActionListener {


    private Timer gameTimer = new Timer(10,this);
    private Image background;
    PlayerShip playerShip = new PlayerShip();
    ArrayList<EnemyShip> enemyShips = new ArrayList();
    EnemyShip enemyShip;
    ArrayList<ProjectileBlade> projectileBlades = new ArrayList();
    ProjectileBlade projectileBlade;

    private int spawnCounter = 0;
    private int delay = 0;
    private int lastSpawn = 0;
    boolean onDelay = false;

    private int fireCounter = 0;

    private int score = 0;
    JLabel scoreLabel;

    private boolean isAPressed = false;
    private boolean isDPressed = false;
    private boolean isWPressed = false;
    private boolean isSPressed = false;
    private boolean isSpacePressed = false;

    GamePanel(){
        //Adding a border to the panel
        setBorder(BorderFactory.createLineBorder(Color.black));
        setFocusable(true);
        grabFocus();
        requestFocusInWindow();

        //Assign a KeyBind to W
        Action wKeyAction = new WKeyAction();
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_W,0),"w pressed");
        getActionMap().put("w pressed",wKeyAction);

        Action wReleaseKeyAction = new WReleaseKeyAction();
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_W,0, true),"w released");
        getActionMap().put("w released",wReleaseKeyAction);

        //Assign a KeyBind to A
        Action aKeyAction = new AKeyAction();
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_A,0),"a pressed");
        getActionMap().put("a pressed",aKeyAction);

        Action aReleaseKeyAction = new AReleaseKeyAction();
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_A,0, true),"a released");
        getActionMap().put("a released",aReleaseKeyAction);

        //Assign a KeyBind to S
        Action sKeyAction = new SKeyAction();
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_S,0),"s pressed");
        getActionMap().put("s pressed",sKeyAction);

        Action sReleaseKeyAction = new SReleaseKeyAction();
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_S,0, true),"s released");
        getActionMap().put("s released",sReleaseKeyAction);

        //Assign a KeyBind to D
        Action dKeyAction = new DKeyAction();
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_D,0),"d pressed");
        getActionMap().put("d pressed",dKeyAction);

        Action dReleaseKeyAction = new DReleaseKeyAction();
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_D,0, true),"d released");
        getActionMap().put("d released",dReleaseKeyAction);

        //Assign a KeyBind to Space
        Action spaceKeyAction = new SpaceKeyAction();
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE,0),"space pressed");
        getActionMap().put("space pressed",spaceKeyAction);

        Action spaceReleaseKeyAction = new SpaceReleaseKeyAction();
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE,0, true),"space released");
        getActionMap().put("space released",spaceReleaseKeyAction);


        // Loading the Background Image
        try {
            background = ImageIO.read(getClass().getResource("Resources/skybox/1.png"));
            System.out.println("Background Loaded");

        } catch (IOException ex) {
            System.out.println("Error in read, exiting.");
            System.exit(0);
        }

        scoreLabel = new JLabel();
        Font font = new Font("sans-serif",Font.BOLD,20);
        scoreLabel.setFont(font);
        scoreLabel.setForeground(Color.RED);
        scoreLabel.setSize(100,50);
        scoreLabel.setLocation(0,0);
        this.add(scoreLabel);
        scoreLabel.setVisible(true);

        setMinimumSize(getPreferredSize());

        gameTimer.start();
    }

    class WKeyAction extends AbstractAction{
        @Override
        public void actionPerformed(ActionEvent e) {

            setW(true);
        }
    }
    class WReleaseKeyAction extends AbstractAction{
        @Override
        public void actionPerformed(ActionEvent e) {

            setW(false);
        }
    }
    class AKeyAction extends AbstractAction{
        @Override
        public void actionPerformed(ActionEvent e) {

            setA(true);
        }
    }
    class AReleaseKeyAction extends AbstractAction{
        @Override
        public void actionPerformed(ActionEvent e) {

            setA(false);
        }
    }
    class SKeyAction extends AbstractAction{
        @Override
        public void actionPerformed(ActionEvent e) {

            setS(true);
        }
    }
    class SReleaseKeyAction extends AbstractAction{
        @Override
        public void actionPerformed(ActionEvent e) {

            setS(false);
        }
    }
    class DKeyAction extends AbstractAction{
        @Override
        public void actionPerformed(ActionEvent e) {

            setD(true);
        }
    }
    class DReleaseKeyAction extends AbstractAction{
        @Override
        public void actionPerformed(ActionEvent e) {

            setD(false);
        }
    }
    class SpaceKeyAction extends AbstractAction{
        @Override
        public void actionPerformed(ActionEvent e) {

            setSpace(true);
        }
    }
    class SpaceReleaseKeyAction extends AbstractAction{
        @Override
        public void actionPerformed(ActionEvent e) {

            setSpace(false);
        }
    }

    private void setW(boolean isWPressed){
        this.isWPressed = isWPressed;
    }
    private void setA(boolean isAPressed){
        this.isAPressed = isAPressed;
    }
    private void setS(boolean isSPressed){
        this.isSPressed = isSPressed;
    }
    private void setD(boolean isDPressed){
        this.isDPressed = isDPressed;
    }
    private void setSpace(boolean isSpacePressed){
        this.isSpacePressed = isSpacePressed;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        //System.out.println("Timer tick: ");

        //Update the player ship location.
        //Callable method that parses the logic and moves as required.
        movement();

        fireWeapons();

        spawnEnemies();

        moveEnemies();

        moveProjectiles();

        checkForCollisions();

        destroyDestroyed();

        checkForMissedEnemies();

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

    private boolean canShoot(){
        //Checks if enough time has passed before firing again
        if(isSpacePressed && fireCounter > 14){
            fireCounter=0;
            return true;
        }
        else{
            return false;
        }
    }

    private void fireWeapons(){
        fireCounter++;
        if(canShoot()){
            projectileBlade = new ProjectileBlade(playerShip.getxPos()+((playerShip.getWidth()/2)-11),playerShip.getyPos());
            projectileBlades.add(projectileBlade);
            AudioFilePlayer.playAudio("src/Resources/Laser Sounds/laser4.wav");
        }
    }

    private boolean canSpawn(){
        //Compares the counter to the delay to see if a ship is allowed to spawn
        spawnCounter++;

        if (spawnCounter < delay){
            onDelay = true;
        }
        else{
            onDelay = false;
            delay = (int)Math.floor(Math.random()*300);
            spawnCounter = 0;
        }

        if (onDelay){
            return false;
        }


        return true;
    }

    private void spawnEnemies(){

        // Checks if the spawn delay has passed. If attempts a spawn in a random spawn location. Does not spawn if it was the same location as the last spawn
        if (canSpawn())
        {
            //System.out.println("Enemy spawned");
            int ship = (int)(Math.random()*4);
            int spawn = (int)(Math.random()*10);

            if (lastSpawn != spawn){
                enemyShip = new EnemyShip(ship,spawn);
                enemyShips.add(enemyShip);
            }

            lastSpawn = spawn;
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
        //Update enemyship locations and repaint
        final int OFFSET = 1;
        for(EnemyShip e : enemyShips){
            int y = e.getyPos();
            repaint(e.getxPos(),e.getyPos(),e.getWidth()+OFFSET,e.getHeight()+OFFSET);
            y++;
            e.setyPos(y);
            repaint(e.getxPos(),e.getyPos(),e.getWidth()+OFFSET,e.getHeight()+OFFSET);
        }
    }
    private void moveProjectiles(){
        // For Each Loops removed in favour of an iterator to allow for concurrent modification
        // Referenced from https://www.baeldung.com/java-concurrentmodificationexception
        final int OFFSET= 1;
        Iterator<ProjectileBlade> iterator = projectileBlades.iterator();
        while(iterator.hasNext()){
            ProjectileBlade e = iterator.next();
            int y = e.getyPos();
            if (y < -30){
                e.setDestroyed(true);
            }
            else{
                repaint(e.getxPos(),e.getyPos(),e.getWidth()+OFFSET,e.getHeight()+OFFSET);
                y -= 4;
                e.setyPos(y);
                repaint(e.getxPos(),e.getyPos(),e.getWidth()+OFFSET,e.getHeight()+OFFSET);
            }
        }
        while(iterator.hasNext()){
            ProjectileBlade p = iterator.next();
            if (p.isDestroyed())
                projectileBlades.remove(p);
        }
    }

    private void checkForCollisions(){
        //Runs a collision check for all enemyships against the current projectiles
        Rectangle eS;
        Rectangle pB;
        Rectangle pS;

        for (EnemyShip e : enemyShips){
            eS = new Rectangle(e.getxPos(),e.getyPos(),e.getWidth(),e.getHeight());

            for(ProjectileBlade p : projectileBlades){
                pB = new Rectangle(p.getxPos(),p.getyPos(),p.getWidth(),p.getHeight());

                if (eS.contains(pB)){
                    //System.out.println("Collision Detected");
                    e.setIsDestroyed(true);
                    p.setDestroyed(true);
                    displayHits(pB.getLocation());
                    SpaceGame.score.increaseScore(1);
                    //System.out.println(SpaceGame.score.getScoreAsString());
                    scoreLabel.setText("Score: " + SpaceGame.score.getScoreAsString());
                }
            }
        }
        
    }

    private void checkForMissedEnemies(){
        for (EnemyShip e : enemyShips){
            if (e.getyPos()>1024){
                System.out.println("Enemy ship escaped");
                gameOver();
            }
        }
    }

    private void displayHits(Point location){

        //System.out.println(location);
    }

    private void destroyDestroyed(){
        Iterator<EnemyShip> enemyShipIterator = enemyShips.iterator();
        Iterator<ProjectileBlade> projectileBladeIterator = projectileBlades.iterator();
        while(enemyShipIterator.hasNext()){
            EnemyShip e = enemyShipIterator.next();
            if(e.isDestroyed()){
                enemyShipIterator.remove();
                System.out.println("Ship Destroyed");
            }
        }
        while(projectileBladeIterator.hasNext()){
            ProjectileBlade e = projectileBladeIterator.next();
            if(e.isDestroyed()){
                projectileBladeIterator.remove();
                //System.out.println("Projectile Destroyed");
            }
        }
    }

    private void gameOver(){
        gameTimer.stop();
        scoreLabel.setText("!!Game Over!!\nYour score was: " + SpaceGame.score.getScoreAsString());
        SpaceGame.score.compareScore(SpaceGame.score.getScore());
        SpaceGame.score.saveScores();
    }

    public Dimension getPreferredSize(){
        return new Dimension(1024,1024);
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(background,0,0,null);
        //SpaceGame.score.paintScore(g);
        playerShip.paintShip(g);

        for(EnemyShip e : enemyShips){
            e.paintShip(g);
        }
        for(ProjectileBlade p : projectileBlades){
            p.paintWeapon(g);
        }
    }
}

