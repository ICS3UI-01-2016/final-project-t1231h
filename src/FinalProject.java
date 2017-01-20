
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
    static final int WIDTH = 500;
    static final int HEIGHT = 800;
    // sets the framerate and delay for our game
    // you just need to select an approproate framerate
    long desiredFPS = 60;
    long desiredTime = (1000) / desiredFPS;
    Color groundcolor = new Color(131, 183, 113);
    //wait to start
    boolean start = false;
    boolean dead = false;
    //animation
    boolean jump = false;
    boolean lastJump = false;
    //key actions
    boolean right = false;
    boolean left = false;
    boolean up = false;
    boolean hitting = false;
    int gravity = 1;
    int dy = 0;
    int jumpVelocity = -15;
    //character
    Rectangle cha = new Rectangle(225, 740, 50, 50);
    //counting scores
    int score = 0;
    Font scoreFont = new Font("Arial", Font.BOLD, 42);
    //rect array
    Rectangle[] road = new Rectangle[5];
    boolean[] passedroad = new boolean[5];
    //whole size of pictures
    //the width of a single road
    int rWidth = WIDTH;
    //the height of a road
    int rHeight = 50;
    //minimum distance from road to road
    int roadgap = 200;
    //speed of the game
    int speed = 2;
    int roadlength = road.length;
    //draw random obstacle
    int oWidth = 60;
    int oHeight = 40;
    int ogap = 70;

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

        //draw the road
        g.setColor(Color.DARK_GRAY);
        for (int i = 0; i < road.length; i++) {
            g.fillRect(road[i].x, road[i].y, road[i].width, road[i].height);
        }

        //draw the cha
        g.setColor(Color.ORANGE);
        g.fillRect(cha.x, cha.y, cha.width, cha.height);

        // GAME DRAWING ENDS HERE
    }

    public void reset() {
        //set up the pipes
        int roadY = 600;
        for (int i = 0; i < road.length; i++) {
            int roadX = 0;
            //generating a random y position
            roadgap = (int) (Math.random() * (300 - 50 + 1) + 50);
            road[i] = new Rectangle(roadX, roadY, rWidth, rHeight);
            //move the roadY value over
            score = 0;
            roadY = roadY - rHeight - roadgap;
            passedroad[i] = false;
        }//reset the bird
        cha.y = 740;

        start = false;
        dead = false;
    }

    public void setRoad(int roadPosition) {
        //set up the pipes
        int roadY = 600;
        // for (int i = 0; i < road.length; i++) {
        int roadX = 0;
        //generating a random y position
        roadgap = (int) (Math.random() * (300 - 50 + 1) + 50);
        road[roadPosition] = new Rectangle(roadX, roadY, rWidth, rHeight);
        //move the roadY value over
        roadY = roadY - rHeight - roadgap;

        road[roadPosition].setBounds(roadX, roadY - roadgap - rHeight, rWidth, rHeight);

        passedroad[roadPosition] = false;

        //}
    }
    // The main game loop
    // In here is where all the logic for my game will go

    public void run() {
        // Used to keep track of time used to draw and update the game
        // This is used to limit the framerate later on
        long startTime;
        long deltaTime;

        //set up the pipes
        int roadY = 600;
        for (int i = 0; i < road.length; i++) {
            int roadX = 0;
            //generating a random y position
            roadgap = (int) (Math.random() * (300 - 50 + 1) + 50);
            road[i] = new Rectangle(roadX, roadY, rWidth, rHeight);
            //move the roadY value over
            roadY = roadY - rHeight - roadgap;
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
                System.out.println("doing thing");
                //get the roads moving
                if (!dead) {
                    for (int i = 0; i < road.length; i++) {
                        road[i].y = road[i].y + speed;
                        //check if a road is off the screen
                        if (road[i].y + rHeight > 800) {
                            System.out.println("set road again");
                            //move the road
                            setRoad(i);
                        }
                    }
                }

                //see if we passed a road  
                for (int i = 0; i < road.length; i++) {
                    if (!passedroad[i] && cha.y > road[i].y + rWidth) {
                        score++;
                        passedroad[i] = true;

                    }
                }

                if (right) {
                    cha.x = cha.x + 4;
                }
                if (left) {
                    cha.x = cha.x - 4;
                }
                if (up) {
                    cha.y = cha.y - 4;
                }


                if (jump != false) {
                    for (int i = 0; i < roadlength; i++) {
                        if (jump = true) {
                            System.out.println("jumptrue");
                            speed=1;
                            //apply gravity
                            dy = dy + gravity;
                            //make the cha fly
                            if (jump && !lastJump && !dead) {
                                dy = jumpVelocity;
                            }
                            lastJump = jump;
                            //apply the change in y to the cha
                            cha.y = cha.y + dy;
                        }
                        if (cha.intersects(road[i])) {
                            System.out.println("intersects true");
                            hitting = true;
                        }
                        if (hitting == true) {
                            System.out.println("hitting true");
                            dead = true;
                            reset();
                        }
                        if (hitting == false) {
                            System.out.println("hitting false");
                            gravity = 0;
                            speed = 1;
                        }
                    }
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
        if (key == KeyEvent.VK_SPACE) {
            jump = true;
            System.out.println("hit key");
        }
        if(key==KeyEvent.VK_ENTER){
            start=true;
        }
        if (key == KeyEvent.VK_UP) {
            up = true;
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
        if (key == KeyEvent.VK_SPACE) {
            jump = false;
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
