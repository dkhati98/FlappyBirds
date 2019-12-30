
import javax.swing.*;


//import javazoom.jl.decoder.JavaLayerException; //Needed to play mp3 files
//import javazoom.jl.player.Player; //Libraries to play mp3 files in game

import java.awt.event.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;



//**********************************PANEL FOR THE FRAME******************

public class FlappyPanel extends JPanel{ //Inheriting from JAVA's pre-available JPanel class
	
	//private static final long serialVersionUID = 1L;
	
	public final int WIDTH = 800;  //Width of the Game Window
	public final int HEIGHT = 600;  //Height of the Game Window
	public final int speed = 6;
	public final int DELAY = 22;  //Set the timer
	
    
	private Image flappy = new ImageIcon("flappy.png").getImage();  //character image
    private Image rock = new ImageIcon("obs.png").getImage();  //rock image inside the coloumn
    private Image background = new ImageIcon("background.png").getImage();  //background image
    private Image foreground = new ImageIcon("bottompanel.jpg").getImage();//for ground image
    
   
	private int score;  //Store Current player score
	private int temp;  //For comparing highscore
	private int highScore = 0;  //Keeps track of the player's highest score
	private int fall;  //For bird to fall
    private int Movebird;  //Used to make the bird move
	
    private boolean start, gameOver;  //To start the game and record the end of game
	
	private Timer timer;  //Producing motion as time changes
	private Random rand;  //Generating random height for the obstacles
	
	private Rectangle bird;  //Player 
	private ArrayList<Rectangle> obstacles;
	
	public FlappyPanel()
	{
		timer = new Timer(DELAY, new jumpListener());  //Creating a new timer object and then making it accessible by jumpListener ActionListener
		rand = new Random();  
		
		bird = new Rectangle(WIDTH/2-200, HEIGHT/2-10, 60, 60);  //Sets the dimensions of the character
		obstacles = new ArrayList<Rectangle>();  //Creates a column object
		
		
		addMouseListener(new touchlistener());  //Adds a listener
		addKeyListener(new birdlistener());  //Adds a listener
		
		
		setPreferredSize(new Dimension(WIDTH-10, HEIGHT-10));  //Setting the size of the panel
		setFocusable(true); //need for key listener,focus on the current frame
		
		//ADDING OBSTACLES TO THE GAME 
		addObstacles(true);  
		addObstacles(true);  
		addObstacles(true);  
		addObstacles(true); 
		timer.start();  //Starts the timer
	}
	
	//***********************PAINT COMPONENT(ADDING DESIGN TO THE GAME FRAME/WINDOW************************
	
	
	
		public void paintComponent(Graphics page)//MAIN METHOD FOR ADDING ALL GRAPHICS TO THE FRAME
		{
		super.paintComponent(page);//passing the graphics object to the constructor of super(mother) class
		
			page.drawImage(background, 0, 0, WIDTH, HEIGHT, this);  //Places the background image on the background
	        
	        
	        page.drawImage(flappy, bird.x, bird.y, bird.width, bird.height, this);  //Places the flappy image on the character
	        
	        page.drawImage(foreground, 0, HEIGHT-100, WIDTH, 200, this);
	        
	        for(Rectangle box : obstacles)
	        {
	        	paintbox(page, box);
	        }
	        
	        page.setColor(Color.black);  //Sets the font to orange
	        
	        page.setFont(new Font("Monospaced", 3, 60));
	        if(!start)
	        {
	        	page.drawString("ENTER SPACE", 250, HEIGHT/4);
	        	page.drawString("OR", 450, HEIGHT/2-100);
	        	page.drawString("Press to Play", 200, HEIGHT/2 -50);
	        }
	        
	        page.setFont(new Font("Helvitica", 4, 100));
	        if(gameOver)
	        {
	        	page.drawString("GAME OVER!", 100, HEIGHT/2 - 50);  //Displays "GAME OVER!"
	        	
	        	page.setFont(new Font("Arial",1,30));
	        	page.drawString("HIT SPACE TO BEGIN AGAIN", 170, HEIGHT/2+50);
	        	
	        }
	        page.setFont(new Font("Arial", 1, 30));//SETS FONT AND SIZE FOR CURRENT SCORE
	        if(!gameOver && start)
	        {
	        	page.setColor(Color.BLACK);  //Font color for score
	        	page.drawString("Your Score: "+score, 100, HEIGHT/15);  //Displays the score in the game
	        }
	        page.setFont(new Font("Arial", 1, 30));
	        if(gameOver || (!gameOver && start))
	        {
	        	page.setColor(Color.orange);  //Sets the font to back to orange for the high score 
	        	page.drawString("HighScore: ", 550, HEIGHT/15);  //Displays the "HighScore: "
	        	        			
	        	page.drawString(String.valueOf(highScore), 725, HEIGHT/15);  //Displays the number for high score
	        }
	        
	        
		}
		
		//THIS METHOD IS CALLED FROM PAINTCOMPONENT TO DRAW ROCKS
		public void paintbox(Graphics page, Rectangle box)
		{
			page.drawImage(rock, box.x, box.y, box.width, box.height, this);  //Places the rock image over the column 
		}

	
/*	public void music()//method for adding music for the game.
	{
		/*while(start || gameOver || !gameOver) //Plays the background music until the game is closed
		{
			try{
				FileInputStream BGM = new FileInputStream("background.mp3");
				Player player = new Player(BGM);
				player.play();
			}catch(FileNotFoundException e){
				e.printStackTrace();
			}catch(JavaLayerException e){
				e.printStackTrace();
			}
		}
	}
*/
	
	
	
	
		
	
	public void addObstacles(boolean start)  //Function to add rectangle inside which we add rocks or obstacles
	{
		int width = 100;//width of obstacles
		int gap = 350;// the distance between the upper and lower obstacles
		int height = 70 + rand.nextInt(200);//height of obstacles

		
		if(start)//IF GAME IS STARTED IT ADDS TWO OBSTACLES ONE ON TOP AND ONE BELOW
		{
			obstacles.add(new Rectangle(WIDTH + width + obstacles.size()*300, HEIGHT-height-120, width, height));
			obstacles.add(new Rectangle(WIDTH + width + ((obstacles.size()-1)*300), 0, width, HEIGHT-height-gap));
		}
		else//
		{
			obstacles.add(new Rectangle(obstacles.get(obstacles.size()-1).x + 600, HEIGHT-height-120, width, height));
			obstacles.add(new Rectangle(obstacles.get(obstacles.size()-1).x, 0, width, HEIGHT-height-gap));
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public void jump()  //Function to make the character jump
	{
		
		
		if(gameOver)// if the game is over
		{
		    bird = new Rectangle(WIDTH/2-200, HEIGHT/2-10, 60, 60);//IT DRAWS NEW CHARACTER
			obstacles.clear(); //OBSTACLES ARE CLEARED
			Movebird = 0;//BIRD STOPS TO MOVE
			score = 0;//SCORE IS RESET TO 0
			
			addObstacles(true);//CLOUDS ARE AGAIN ADDED
			addObstacles(true);
			addObstacles(true);
			addObstacles(true);
			gameOver = false;//AND GAMEOVER IS RESET TO FALSE
		}
		
		
		if(!start)//FORCES GAME TO START
		{
			start = true;
		}
		
		
		else if(!gameOver)//THIS IS WHAT MOVES THE CHARACTER
		{
			if(Movebird > 0)
			{
				Movebird = 0;
			}
			Movebird -= 10;
		}
	}
	

	
	
	
	
	
	public class jumpListener implements ActionListener// Actionlistener to bring movement to the game
	{
		public void actionPerformed(ActionEvent event)
		{
			
			if(start)//IF GAME IS STARTED FALL IS INCREASED
			{
				fall ++;
			
				
				for(int counter = 0; counter < obstacles.size(); counter++)//THIS HELPS TO MOVE OUR BOXES
				{
					Rectangle box = obstacles.get(counter);
					box.x -= speed;
					
				}
				
			
				if(fall % 2 == 0 && Movebird < 15)//THIS CHECKS FALL 
				{
					Movebird += 2;//AND MOVES BIRD
				}
				bird.y += Movebird;//Y CORDINATE OF THE BIRD IS CHANGED
			
				
				
			    for(int counter = 0; counter < obstacles.size(); counter++)
				{
					Rectangle box = obstacles.get(counter);
					if(box.x + box.width < 0)//IF CORDINTATES ARE NEGATIVE NEW CREATION
					{
						obstacles.remove(box);
					
						if(box.y == 0)
						{
							addObstacles(false);//THIS IS TO MAKE SURE BOXES ARE FORMED CONTINUOUSLY
						}
					}
				}
				for(Rectangle box : obstacles)//THIS FUNCITON HELPS TO INCREASE THE SCORE
				{
					if(box.y == 0 && bird.x + bird.width/2 > box.x + box.width/2-speed && bird.x + bird.width/2 < box.x + box.width/2 + speed)
					{
						score++;//IF OBSTACLE IS PASSED SCORE IS INCREASED
						temp = score;//STORES THIS VALUE
						if(temp > highScore)
						{
							highScore = temp;//CHECKS FOR HIGHSCORE
						}
					}
					if(box.intersects(bird))//INTERSECTION CHECKER. TO SEE IF GAME IS OVER
					{
						gameOver = true;
						bird.x = box.x - bird.width;
					}
					if(bird.y > HEIGHT-180 || bird.y <0)//AGAIN IF GAME IS OVER.
					{
						gameOver = true;
					}
					if(bird.y + Movebird >= HEIGHT - 150)
					{
						bird.y = HEIGHT - 80 - bird.height;
					}
				
				}
			
				repaint();//FINALLY REPAINTS ALL THE THINGS
			}
		}
	}
	
	
	
	
		
	//****************************************LISTENER*************************************
	
	public class touchlistener implements MouseListener
	{
		public void mouseClicked(MouseEvent m) {
			jump();
			//jumpSound();
		}

		public void mouseEntered(MouseEvent m) {
				
		}

		public void mouseExited(MouseEvent m) {
			
		}

		public void mousePressed(MouseEvent m) {
			
		}

		public void mouseReleased(MouseEvent m) {
			
		}
	}
	
	
	
	
	
	public class birdlistener implements KeyListener
	{

		public void keyPressed(KeyEvent k) {
			
		}

		public void keyReleased(KeyEvent k) {
			if(k.getKeyCode() == KeyEvent.VK_SPACE)
			{
				jump();
				//jumpSound();
			}
		}

		public void keyTyped(KeyEvent k) {
			
		}
		
	}
}