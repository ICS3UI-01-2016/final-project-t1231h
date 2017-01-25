
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import javax.swing.JComponent;
import javax.swing.JFrame;

/**
 *
 * @author annt0773
 */
public class FinalProject extends JComponent implements KeyListener {

    // Height and Width of our game
    static final int WIDTH = 1000;
    static final int HEIGHT = 600;
    // sets the framerate and delay for our game
    // you just need to select an approproate framerate
    long desiredFPS = 60;
    long desiredTime = (1000) / desiredFPS;

    //set the color for the ground
    Color groundcolor = new Color(131, 183, 113);

    //wait to start
    boolean start = false;
    //boolean to say dead
    boolean dead = false;

    //key actions
    boolean right = false;
    boolean left = false;
    boolean up = false;
    boolean down = false;

    //boolean to check if the chs hits the road
    boolean hitting = false;

    //win
    Font safeFont = new Font("Arial", Font.BOLD, 42);
    
    //character
    Rectangle cha = new Rectangle(485, 500, 50, 50);

    //rect array
    Rectangle[] blockT = new Rectangle[5];
    Rectangle[] blockB = new Rectangle[5];

    //the width of a single block
    int blockWidth = 120;
    //the height of a single block
    int blockHeight = 120;
    //minimum distance from block to block
    int mindisT = 100;
    int mindisB = 100;
    
    //create a home
    Rectangle home = new Rectangle(25, 25, 100, 50);
    //create a fake home
    Rectangle homeF = new Rectangle(850, 25, 100, 50);

    //speed of the game
    int speed = 2;

    // drawing of the game happens in here
    // we use the Graphics object, g, to perform the drawing
    // NOTE: This is already double buffered!(helps with framerate/speed)
    @Override
    public void paintComponent(Graphics g) {
        // always clear the screen first!
        g.clearRect(0, 0, WIDTH, HEIGHT);

        // GAME DRAWING GOES HERE 
        //change to color the ground
        g.setColor(groundcolor);
        //draw a ground background
        g.fillRect(0, 0, WIDTH, HEIGHT);

        //draw the blocks
        g.setColor(Color.DARK_GRAY);
        for (int i = 0; i < blockT.length; i++) {
            g.fillRect(blockT[i].x + mindisT, 100, blockWidth, blockHeight);
            g.fillRect(blockB[i].x + mindisB, 300, blockWidth, blockHeight);
        }
        //draw the homes
        g.setColor(Color.MAGENTA);
        g.fillRect(home.x, home.y, home.width, home.height);
        g.fillRect(homeF.x, homeF.y, homeF.width, homeF.height);
        //draw the cha
        g.setColor(Color.ORANGE);
        g.fillRect(cha.x, cha.y, cha.width, cha.height);
        if(cha.intersects(home)) {                   
        g.setColor(Color.WHITE);
        g.setFont(safeFont);
        g.drawString("You are SAFE",WIDTH/2,25);           
                       }
        if(cha.intersects(homeF)){
            g.setColor(Color.WHITE);
        g.setFont(safeFont);
        g.drawString("You are DEAD",WIDTH/2,25);
        }
        
        // GAME DRAWING ENDS HERE
    }

    public void reset() {
        int blockXT = 50;
        int blockXB = 40;
        for (int i = 0; i < blockT.length; i++) {
            // generating a random x position of blocks
            mindisT = (int) (Math.random() * (300 - 65 + 1) + 65);
            mindisB = (int) (Math.random() * (300 - 70 + 1) + 70);
            blockT[i] = new Rectangle(blockXT, 80, blockWidth, blockHeight);
            blockB[i] = new Rectangle(blockXB, 270, blockWidth, blockHeight);
            // move the pipeX value over
            blockXT = blockXT + blockWidth + mindisT;
            blockXB = blockXB + blockWidth + mindisB;
// reset the whole thing
            cha.y = 500;
            home.y = 25;
            homeF.y = 25;
            start = false;
            dead = false;
        }
    }

    public void setBlock(int blockPosition) {

        //generate random X position
        mindisT = (int) (Math.random() * (300 - 65 + 1) + 65);
        mindisB = (int) (Math.random() * (300 - 70 + 1) + 70);

        int blockNXT = blockT[blockPosition].x;
        blockNXT = blockNXT + (blockWidth + mindisT) * blockT.length;
        int blockNXB = blockB[blockPosition].x;
        blockNXB = blockNXB + (blockWidth + mindisB) * blockB.length;

        blockT[blockPosition].setBounds(blockNXT, 80, blockWidth, blockHeight);
        blockB[blockPosition].setBounds(blockNXB, 270, blockWidth, blockHeight);

    }
    // The main game loop
    // In here is where all the logic for my game will go

    public void run() {
        // Used to keep track of time used to draw and update the game
        // This is used to limit the framerate later on
        long startTime;
        long deltaTime;

        int blockXT = 50;
        int blockXB = 40;
        for (int i = 0; i < blockT.length; i++) {
            // generating a random x position of blocks
            //random minimum distance for Top blocks
            mindisT = (int) (Math.random() * (300 - 65 + 1) + 65);
            //for bottom blocks
            mindisB = (int) (Math.random() * (300 - 70 + 1) + 70);
            //new rectangles
            blockT[i] = new Rectangle(blockXT, 80, blockWidth, blockHeight);
            //new rectangles
            blockB[i] = new Rectangle(blockXB, 270, blockWidth, blockHeight);
            // move the pipeX value over
            blockXT = blockXT + blockWidth + mindisT;
            blockXB = blockXB + blockWidth + mindisB;

        }

        // the main game loop section
        // game will end if you set done = false;
        boolean done = false;
        while (!done) {
            // determines when we started so we can keep a framerate
            startTime = System.currentTimeMillis();

            // all your game rules and move is done in here
            // GAME LOGIC STARTS HERE         
            if (start) {
                if (!dead) {
                    // get the blocks moving
                    for (int i = 0; i < 5; i++) {
                        blockT[i].x = blockT[i].x + speed;
                        blockB[i].x = blockB[i].x - speed;
                        // check if a pipe is off the screen
                        if (blockT[i].x + blockWidth < 1000) {
                            // move the pipe
                            setBlock(i);
                        }
                        if (cha.intersects(blockT[i])) {
                            dead = true;
                            reset();

                        }else{
                        if (cha.intersects(blockB[i])) {
                            dead = true;
                            reset();
                        }
                        if(cha.intersects(home)){
                            reset();
                        }else{
                            
                        }
                        
                        }
                      
                    }
                }
                //right key variable
                if (right) {
                    cha.x = cha.x + 4;
                }
                //left key variable
                if (left) {
                    cha.x = cha.x - 4;
                }
                //up key variable
                if (up) {
                    cha.y = cha.y - 4;
                    System.out.println("up");
                }
                if (down) {
                    cha.y = cha.y + 4;
                }

            }
            // GAME LOGIC ENDS HERE 
            // update the drawing (calls paintComponent)
            repaint();

            // SLOWS DOWN THE GAME BASED ON THE FRAMERATE ABOVE
            // USING SOME SIMPLE MATH
            deltaTime = System.currentTimeMillis() - startTime;
            try {
                if (deltaTime > desiredTime) {
                    //took too much time, don't wait
                    Thread.sleep(1);
                } else {
                    // sleep to make up the extra time
                    Thread.sleep(desiredTime - deltaTime);
                }
            } catch (Exception e) {
            };

        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // creates a windows to show my game
        JFrame frame = new JFrame("My Game");

        // creates an instance of my game
        FinalProject game = new FinalProject();
        // sets the size of my game
        game.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        // adds the game to the window
        frame.add(game);

        // sets some options and size of the window automatically
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        // shows the window to the user
        frame.setVisible(true);
        frame.addKeyListener(game);
        // starts my game loop
        game.run();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_ENTER) {
            start = true;
        }
        if (key == KeyEvent.VK_DOWN) {
            down = true;
        }
        if (key == KeyEvent.VK_UP) {
            up = true;
            System.out.println("hit key");
        }
        if (key == KeyEvent.VK_RIGHT) {
            right = true;
        }
        if (key == KeyEvent.VK_LEFT) {
            left = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_DOWN) {
            down = false;
        }
        if (key == KeyEvent.VK_UP) {
            up = false;
        }
        if (key == KeyEvent.VK_RIGHT) {
            right = false;
        }
        if (key == KeyEvent.VK_LEFT) {
            left = false;
        }
    }
}
