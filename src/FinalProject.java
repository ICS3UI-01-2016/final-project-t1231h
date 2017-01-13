
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;
import javax.swing.JComponent;
import javax.swing.JFrame;

/**
 *
 * @author annt0773
 */


public class FinalProject extends JComponent{

    // Height and Width of our game
    static final int WIDTH = 500;
    static final int HEIGHT = 800;
    
    // sets the framerate and delay for our game
    // you just need to select an approproate framerate
    long desiredFPS = 60;
    long desiredTime = (1000)/desiredFPS;
    
    Color groundcolor = new Color(131, 183, 113);
    
    //wait to start
    boolean start = false;
    boolean dead = false;
    
    //character
    Rectangle cha = new Rectangle(225, 740, 50, 50);

    //rect array
    Rectangle[] road = new Rectangle[5];
    boolean[]passedroad=new boolean[5];
    //whole size of pictures
    //the width of a single road
    int rWidth = WIDTH;
    //the height of a road
    int rHeight = 50;
    //minimum distance from road to road
    int roadgap = 200;
    //speed of the game
    int speed = 3;
    
    
    
    
    // drawing of the game happens in here
    // we use the Graphics object, g, to perform the drawing
    // NOTE: This is already double buffered!(helps with framerate/speed)
    @Override
    public void paintComponent(Graphics g)
    {
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
        
        //draw the bird
        g.setColor(Color.ORANGE);
        g.fillRect(cha.x, cha.y, cha.width, cha.height);
        
        // GAME DRAWING ENDS HERE
    }
    
    public void setGround(int pipePosition) {
        //a random number generator
        Random randGen = new Random();
        

    }
    // The main game loop
    // In here is where all the logic for my game will go
    public void run()
    {
        // Used to keep track of time used to draw and update the game
        // This is used to limit the framerate later on
        long startTime;
        long deltaTime;
        
        //set up the roads
        int roadX = 0;
        Random randGen = new Random();
        int roadY = 0;
        for (int i = 0; i < road.length; i++) {
            //generating a random y position
            roadY = roadY + randGen.nextInt(HEIGHT - 2 * roadgap) + roadgap;
            road[i] = new Rectangle(roadX, roadY, rWidth, rHeight);
           
            //move the roadY value over
            roadY = roadY + rHeight + roadgap;
        }
        
        // the main game loop section
        // game will end if you set done = false;
        boolean done = false; 
        while(!done)
        {
            // determines when we started so we can keep a framerate
            startTime = System.currentTimeMillis();
            
            // all your game rules and move is done in here
            // GAME LOGIC STARTS HERE 
            
            

            // GAME LOGIC ENDS HERE 
            
            // update the drawing (calls paintComponent)
            repaint();
            
            
            
            // SLOWS DOWN THE GAME BASED ON THE FRAMERATE ABOVE
            // USING SOME SIMPLE MATH
            deltaTime = System.currentTimeMillis() - startTime;
            try
            {
               if(deltaTime > desiredTime)
               {
                   //took too much time, don't wait
                   Thread.sleep(1);
               }else{
                  // sleep to make up the extra time
                 Thread.sleep(desiredTime - deltaTime);
               }
            }catch(Exception e){};
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
        game.setPreferredSize(new Dimension(WIDTH,HEIGHT));
        // adds the game to the window
        frame.add(game);
         
        // sets some options and size of the window automatically
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        // shows the window to the user
        frame.setVisible(true);
        
        // starts my game loop
        game.run();
    }
}