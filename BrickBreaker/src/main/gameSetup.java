package main;

import display.Display;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.util.logging.Level;
import java.util.logging.Logger;

public class gameSetup implements Runnable, KeyListener{
    
    private Display display;
    private String title;
    private int width, height;
    private Thread thread;
    private BufferStrategy buffer;
    private Graphics graphics;
    private int score,score1;
    private int life;
    
    private int ballX = 200, ballY = 410;
    int batX = 200, batY = 430;
    boolean left,right;
    boolean Game_Over;
    //array for bricks
    private Rectangle[] Bricks;
    int brickX = 70, brickY = 60;
    int moveX = 1, moveY = -1;
    
    Rectangle Ball = new Rectangle(ballX, ballY, 15, 15);
    Rectangle Bat = new Rectangle(batX, batY, 40, 10);
    
    public gameSetup(String title, int width, int height){  //Constructor
        
        this.title = title;
        this.width = width;
        this.height = height;
        score = 0;
        score1=0;
        life =3;
        Game_Over = false;
      
}
    public void init(){
        
        display = new Display(title, width, height);
        display.frame.addKeyListener(this);
        Bricks = new Rectangle[13];
        
        for (int i = 0; i <Bricks.length; i++) {
            
            Bricks[i] = new Rectangle(brickX, brickY, 60, 20);
            if(i == 5){
                brickX = 60;
                brickY = 60+25;
            }
            if(i == 9){
                brickX = 80;
                brickY = 60+25+25;
            }
            brickX = brickX+60;
               
        }
    }
    
    public void drawBall(Graphics graphics){
        
        graphics.setColor(Color.red);
        graphics.fillOval(Ball.x, Ball.y, 15, 15);
        
    }
    
    public void drawBat(Graphics graphics){
        
        graphics.setColor(Color.blue);
        graphics.fillRect(Bat.x, Bat.y, 40, 10);
    }
    
    public void drawBricks(Graphics graphics){
        
        for (int i = 0; i <Bricks.length; i++) {
            if(Bricks[i]!= null){
            graphics.setColor(Color.red);
            graphics.fillRect(Bricks[i].x-2, Bricks[i].y-2, 60+2, 20+2);
            graphics.setColor(Color.green);
            graphics.fillRect(Bricks[i].x, Bricks[i].y, 60, 20);
            
        }
    }
    }
    
    public void drawScore(Graphics graphics){
        String a =Integer.toString(score);
        graphics.setColor(Color.white);
        graphics.setFont(new Font("arial", Font.PLAIN, 33));
        graphics.drawString("Score: "+ a, 90, 480);
    }   
    public void drawLife(Graphics graphics){
        String l =Integer.toString(life);
        graphics.setColor(Color.white);
        graphics.setFont(new Font("arial", Font.PLAIN, 33));
        graphics.drawString("Life: "+ l, 300, 480);
    }
    
    public void Game_Over(Graphics graphics){
        graphics.setColor(Color.red);
        graphics.setFont(new Font("arial", Font.BOLD, 38));
        graphics.drawString("Game Over", 130, 150);
    }
    
    public void tick(){   //Ball Direction
       
        if(Ball.x >= 430 || Ball.x <= 40){
            
            moveX = -moveX;
        }
        if(Ball.y <= 40){
            
            moveY = -moveY;
        }
        //for (int i = 0; i < 3; i++) {
        if(Ball.y >= 430){
            
            life = life -1;
            moveY = -moveY;
            //Game_Over = true;
        }
        //}
        Ball.x += moveX;
        Ball.y += moveY;
        
         //Bat Direction
        if(right){
            if(Bat.x<=400){
            Bat.x +=1;
        }
        }
        if(left){
            if(Bat.x>=40){
            Bat.x -=1;
        }
        }
        
        //Ball and Bat Collision
        if(Bat.intersects(Ball)){
            
            moveY = -moveY;
        }
        //Ball and Bricks Collision
        for (int i = 0; i <Bricks.length; i++) {
            
            if(Bricks[i]!= null){
            if(Bricks[i].intersects(Ball)){
                
               moveY = -moveY;
                Bricks[i]=null;
                score = score+5;
                
            }
            
        }
    }
    }
    
    public void draw(){
       
        buffer = display.canvas.getBufferStrategy(); //BufferStrategy creates screen behind the fornt screen
        if(buffer==null){
            display.canvas.createBufferStrategy(3);
            return;
        }
        graphics = buffer.getDrawGraphics();
        graphics.clearRect(0, 0, width, height);
        //draw game spot here
        graphics.setColor(Color.white);
        graphics.fillRect(40, 40, 400, 400);
        
        if(life!=0){
        drawBall(graphics);
        drawBat(graphics);
        drawBricks(graphics);
        drawScore(graphics);
        drawLife(graphics);
        }
        
        if(life==0){
        Game_Over(graphics);
        Game_Over = true;
        }
        buffer.show();
        graphics.dispose();
    }
    
    public synchronized void start(){
        
        thread = new Thread(this);
        thread.start();
        
    }
    
    public synchronized void stop(){
        
        try {
            thread.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(gameSetup.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @Override
    public void run() {
         init();
         
         while(true){
             
             thread.currentThread();
             try {
                 
                 thread.sleep(10);
                 
             } catch (InterruptedException ex) {
                 Logger.getLogger(gameSetup.class.getName()).log(Level.SEVERE, null, ex);
             }
            tick();
            draw();
         }
         
    }

    @Override
    public void keyPressed(KeyEvent e) {
       
        int source = e.getKeyCode();
        
        if(source == KeyEvent.VK_LEFT){
            left = true;
        }
        if(source == KeyEvent.VK_RIGHT){
            right = true;
        }
    }


    @Override
    public void keyReleased(KeyEvent e) {
        
        int source = e.getKeyCode();
        
        if(source == KeyEvent.VK_LEFT){
            left = false;
        } 
         if(source == KeyEvent.VK_RIGHT){
           right = false;
        }
    }
    @Override
    public void keyTyped(KeyEvent arg0){
        
    }
}    
