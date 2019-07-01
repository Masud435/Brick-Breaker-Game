package display;

import java.awt.*;
import javax.swing.JFrame;


public final class Display {
    
    private String title;
    private int width, height;
    
    
    public JFrame frame;
    public Canvas canvas;
    public Display(String title, int width, int height){
       
        this.title = title;
        this.width = width;
        this.height = height;
        
        createDisplay();
    }

    public Display() {
       
    }

    public void createDisplay(){
        
        frame = new JFrame(title);
        frame.setSize(width, height);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //for close the game
        frame.setResizable(false);
        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(width, height));
        canvas.setBackground(new Color(180,123,133));
        canvas.setFocusable(false);
        frame.add(canvas);
        frame.pack(); //for stopping canvas increasing more than frame
        
    }
}
