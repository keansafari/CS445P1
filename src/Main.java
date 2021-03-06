/**
 *
 * @author kean_jafari
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import org.lwjgl.LWJGLException;
import static org.lwjgl.opengl.GL11.*;
import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;


public class Main {

    public static final int DISPLAY_HEIGHT = 480;
    public static final int DISPLAY_WIDTH = 640;
    public static final float DEG2RAD = (float)PI / 180;
        
    public Main() {
        
    }
    
    public void start() {
        try {
            Display.setDisplayMode(new DisplayMode(DISPLAY_WIDTH, DISPLAY_HEIGHT));
            Display.create();
        } catch (LWJGLException e) { 
            e.printStackTrace();
            System.exit(0);
        }
        
        // init OpenGL
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, DISPLAY_WIDTH, 0, DISPLAY_HEIGHT, 1, -1);
        glMatrixMode(GL_MODELVIEW);
        
        while (!Display.isCloseRequested() && !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
            // render OpenGL here
            //readConfig();
           
            
            // Clear the screen and depth buffer
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);  
         
            // set the color of the quad (R,G,B,A)
            glColor3f(0.5f,0.5f,1.0f);

            
            
            try {
                File file = new File("src/coordinates.txt");
                FileReader fr = new FileReader(file);
                BufferedReader br = new BufferedReader(fr);
                String line;
                while ((line = br.readLine()) != null) {
                    char shapeType = line.charAt(0);
                    line = line.substring(2, line.length());
                    String delims = "[, ]";
                    String[] tokens = line.split(delims);
                    switch (shapeType) {
                        case 'l': 
                            drawLine(tokens);
                            break;
                        case 'c':
                            drawCircle(tokens);
                            break;
                        case 'e':
                            drawEllipse(tokens);
                            break;
                        default:
                            break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            Display.update();
        }
        Display.destroy();
    }
    
    public void drawLine(String[] tokens) {
        glColor3f(1.0f,0.0f,0.0f);
        glBegin(GL_LINES);
            glVertex2f(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]));
            glVertex2f(Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]));
        glEnd();
    }
    
    private void drawCircle(String[] tokens){
	int i;
	int lineAmount = 100; 
	float x = Float.parseFloat(tokens[0]);
        float y = Float.parseFloat(tokens[1]);
        float radius = Float.parseFloat(tokens[2]);
	
	float twicePi = 2.0f * (float)PI;
	glColor3f(0.0f,0.0f,1.0f);
	glBegin(GL_LINE_LOOP);
		for(i = 0; i <= lineAmount;i++) { 
                    glVertex2f(
                        (float)(x + (radius * cos(i *  twicePi / lineAmount))), 
                        (float)(y + (radius* sin(i * twicePi / lineAmount)))
                    );
		}
	glEnd();
}

    private void drawEllipse(String[] tokens) {
        int x = Integer.parseInt(tokens[0]);
        int y = Integer.parseInt(tokens[1]);
        int width = Integer.parseInt(tokens[2]);
        int height = Integer.parseInt(tokens[3]);
        
        float theta;
        float angle_increment;
        float x1;
        float y1;

        angle_increment = (float) Math.PI / 500;
        glPushMatrix();

        glTranslatef(x+(width/2), y+(height/2), 0);
        glColor3f(0.0f, 1.0f,0.0f);
        glBegin(GL_LINE_LOOP);
        for(theta = 0.0f; theta < Math.PI * 2; theta += angle_increment) {
            x1 = (float) (width/2 * Math.cos(theta));
            y1 = (float) (height/2 * Math.sin(theta));

            glVertex2f(x1, y1);
        }
        glEnd();

        glPopMatrix();
   }
    
    public static void main(String[] args) {
        Main main = new Main();
        main.start();
    } 
}

