
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.net.URL;
import java.util.Random;
import java.util.Scanner;

import javax.swing.*;

import java.util.*;


public class StartingPoint extends JApplet implements ActionListener, Runnable, KeyListener, MouseMotionListener, MouseListener {

		

		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

		public static void main(String[] args) {
		
		Component applet = new StartingPoint();
		JFrame frame = new JFrame("My applet, as application");
		frame.add(applet);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.getContentPane().add(applet);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setSize(800,600);
		frame.setVisible(true);
		
		
		
		
		
		((Applet) applet).start();
		}

		





	private Image i;
	private Graphics doubleG;
	Ball b;
	Platform p[] = new Platform [7];
	Item item[] = new Item[3];
	private int score;
	double cityX = 0;
	double cityDx = .3;
	URL url;
	Image city;
	int levelcheck = 0;
	boolean gameOver = false;
	boolean mouseIn = false;
	boolean pause = false;
	Thread t = null;
	
	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public void init() {
		setSize(800, 600);	
		
		     
		setFocusable(true);
		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
		try{
			url = getDocumentBase();
		}catch (Exception e) {
			// TODO: handle exception
		}
		city = getImage(url, "images/background.png");
	    new Pictures(this);
	    Pictures.bounce.play();
	    Pictures.music.loop();
	    
	}

		
	    



				{

	   
	    
	}



	

	public void start () {
		b= new Ball();
		score = 0;
		for (int i = 0; i < p.length; i++){
			Random r = new Random();
			p[i] = new Platform(i*120, 300);	
		}
		
		for (int i = 0; i < item.length; i++){
			Random r = new Random();
			switch (r.nextInt(5)){
			case 0:
				item[i] = new GravUp(getWidth() + 2000 * i);
				break;
			case 1:
				item[i] = new gravDown(getWidth() + 2000 * i);
				break;
			case 2:
				item[i] = new AgilUp(getWidth() + 2000 * i);
			case 3:
				item[i] = new AgilDown(getWidth() + 2000 * i);
				break;
			case 4:
				item[i] = new ScorePlus(getWidth() + 2000 * i, this);
				break;
			}
			
		}
		
		Thread thread = new Thread (this);
		thread.start();
	
	
	}
	
	@Override
	public void run(){
		while (true) {
			if (pause == true){
				
			}

			gameOver = b.getGameOver();
			if (levelcheck > 1000){
				Pictures.level++;
				levelcheck = 0;
			}
			
			levelcheck++;
			
			if (cityX > getWidth() * -1){
			cityX -= cityDx;	
			}else{
				cityX = 0;
			}
			
			if(!gameOver){
			score++;
			}
			
			Random r = new Random();
			

		for (int i = 0; i < item.length; i++){
			if (item[i].isCreateNew()){
				item[i] = null;
				switch (r.nextInt(5)){
				case 0:
					item[i] = new GravUp(getWidth() + 10 * r.nextInt(500));
					break;
				case 1:
					item[i] = new gravDown(getWidth() + 10 * r.nextInt(500));
					break;
				case 2:
					item[i] = new AgilUp(getWidth() + 10 * r.nextInt(500));
				case 3:
					item[i] = new AgilDown(getWidth() + 10 * r.nextInt(500));
					break;
				case 4:
					item[i] = new ScorePlus(getWidth() + 10 * r.nextInt(500), this);
					break;
				}
				item[1].setCreateNew(false);
				}
		}
		
		b.update(this);
		
		for (int i =0; i< p.length; i++){
		p[i].update(this, b);
		}
		
		for (int i =0; i< item.length; i++){
			item[i].update(this, b);
			}
		
		
		repaint(20);
		try {
			Thread.sleep(17);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		}
	}	
	
		

	
	public void stop () {
		
		
	}
	

	public void destroy() {
	
	}
	
	@Override
	public void update(Graphics g) {
		if(i == null){
			i = createImage(this.getSize().width, this.getSize().height);
			doubleG = i.getGraphics();
		}
	
		doubleG.setColor(getBackground());
		doubleG.fillRect(0, 0, this.getSize().width, this.getSize().height);
		
		doubleG.setColor(getForeground());
		paint(doubleG);
		
		g.drawImage(i, 0, 0, this);
	
	}
	
	
	
	public void paint(Graphics g) {
		
		
		g.drawImage(city, (int) cityX, 0, this);
		g.drawImage(city, (int) cityX + getWidth(), 0, this);
		
		for (int i =0; i< p.length; i++){
			p[i].paint(g);
			}
		
		for (int i =0; i< item.length; i++){
			item[i].paint(g);
			}
		
		b.paint(g);
		
		
	
		

		
		
		String s = Integer.toString(score);
		Font font = new Font("Cracked", Font.BOLD, 48);
		g.setFont(font);
		g.setColor(Color.BLACK);
		g.drawString(s, getWidth()-150+2, 50+2);
		g.setColor(new Color(198,226,255));
		g.drawString(s, getWidth()-150, 50);
		if(gameOver){
			g.setColor(Color.WHITE);
			g.drawString("GAME OVER", 300, 300);
			g.drawRect(270, 305, 180, 40);

			if (mouseIn){
			g.setColor(Color.RED);
			g.drawString("Play Again?", 280, 340);
			}else{
			g.setColor(Color.WHITE);
			g.drawString("Play Again?", 280, 340);
		}
			
		}
		
	}


	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		switch(e.getKeyCode()){
		case KeyEvent.VK_LEFT:
			 b.moveLeft();
			 break;
		
		case KeyEvent.VK_RIGHT:
			b.moveRight();
			break;
			
		case KeyEvent.VK_ENTER:
			if(gameOver){
				b = null;
				b = new Ball();
				score = 0;
				Pictures.level = 1;
				for (int i = 0; i < p.length; i++){
					Random r = new Random();
					p[i] = new Platform(i*120, 300);	
				}
				
				for (int i = 0; i < item.length; i++){
					Random r = new Random();
					switch (r.nextInt(5)){
					case 0:
						item[i] = new GravUp(getWidth() + 2000 * i);
						break;
					case 1:
						item[i] = new gravDown(getWidth() + 2000 * i);
						break;
					case 2:
						item[i] = new AgilUp(getWidth() + 2000 * i);
					case 3:
						item[i] = new AgilDown(getWidth() + 2000 * i);
						break;
					case 4:
						item[i] = new ScorePlus(getWidth() + 2000 * i, this);
						break;
						
						
					}
					mouseIn = false;
			if(!gameOver);
			break;
			}
			
				

			
		
				}
				}
				}
		
				
	
	
		
	

	
				
			
						

		
		
			
		




	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
			
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
		if (e.getX() > 280 && e.getX() <460){
			if (e.getY() >320 && e.getY() < 360){
				mouseIn = true;
				if (!gameOver){
					mouseIn=false;
				}
			}
		}
		if (e.getX() < 280 || e.getX() >460){
				mouseIn = false;
			}
			if (e.getY() < 270 || e.getX() >480){
					mouseIn = false;
				}
		}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
		if (mouseIn){
			b = null;
			b = new Ball();
			score = 0;
			Pictures.level = 1;
			for (int i = 0; i < p.length; i++){
				Random r = new Random();
				p[i] = new Platform(i*120, 300);	
			}
			
			for (int i = 0; i < item.length; i++){
				Random r = new Random();
				switch (r.nextInt(5)){
				case 0:
					item[i] = new GravUp(getWidth() + 2000 * i);
					break;
				case 1:
					item[i] = new gravDown(getWidth() + 2000 * i);
					break;
				case 2:
					item[i] = new AgilUp(getWidth() + 2000 * i);
				case 3:
					item[i] = new AgilDown(getWidth() + 2000 * i);
					break;
				case 4:
					item[i] = new ScorePlus(getWidth() + 2000 * i, this);
					break;
					
				}
				mouseIn = false;
			
			}
		}	
	
		}
	

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}


	}

	
	




	

