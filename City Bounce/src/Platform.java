import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;
import java.util.Random;


public class Platform {

	
	private int dx;
	private int x, y, width, height;
	Image plat;
	URL url;
	float frame = 0;
	
	public Platform() {
		// TODO Auto-generated constructor stub
		dx = -1;
		x = 450;
		y = 300;
		width = 120;
		height = 40;
	}
	
	public Platform(int x, int y){
		this.x = x;
		this.y = y;
		width = 120;
		height = 40;
		dx = -1;
		plat = Pictures.platform; 
		
	}
	
	public void update (StartingPoint sp, Ball b){
		int tester = (int)(frame + .1);
		if (tester < 3)
			frame += .1;
		else
			frame = 0;
		
		x += - (Pictures.level);
		checkForCollision(b);
		if (x < 0 - width){
			Random r = new Random();
			y = sp.getHeight()- 40 - r.nextInt(400);		
			x = sp.getWidth() + r.nextInt(300);
		}
		
		
	}
	
	private void checkForCollision(Ball b) {
		// TODO Auto-generated method stub
		int ballX = b.getX();
		int ballY = b.getY();
		int radius = b.getRadius();		
		if (ballY + radius > y && ballY+radius < y + height){
			if (ballX > x && ballX < x + width) {
			double newDY = b.getGameDy();
			b.setY(y-radius);
			b.setDy(newDY);
			Pictures.bounce.play();
			
			}
		}
		}
	
	
	

	public void paint (Graphics g){
		
		//g.drawImage(plat, x, y, Pictures.sp);
		g.drawImage(plat, x, y, x + width, y + height, 0, 40 *(int)frame, 120, 40*(int)frame + 40, Pictures.sp);
	}
	
}	



	
	


