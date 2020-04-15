import java.applet.AudioClip;
import java.awt.Image;
import java.net.URL;


public class Pictures {
	
	static Image platform, ball, score, gravup, gravdown;
	
	URL url;
	static StartingPoint sp;
	static AudioClip music, bounce;
	static int level = 1;
	

	
	public Pictures(StartingPoint sp) {
		// TODO Auto-generated constructor stub
		try{
			url =sp.getDocumentBase();
		}catch (Exception e) {
			// TODO: handle exception
		}
		
		music = sp.getAudioClip(url, "Music/city-bounce.au");
		bounce = sp.getAudioClip(url, "Music/boujou.au");
		
		
		platform = sp.getImage(url, "images/Platforms.png");
		ball = sp.getImage(url, "images/ball.png");
		score = sp.getImage(url, "images/scoreplus.png");
		gravup = sp.getImage(url, "images/gravityup.png");
		gravdown = sp.getImage(url, "images/gravitydown.png");
		this.sp = sp;
	}

}
