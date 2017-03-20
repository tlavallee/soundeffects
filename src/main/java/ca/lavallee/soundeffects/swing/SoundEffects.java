package ca.lavallee.soundeffects.swing;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import ca.lavallee.config.AppConfig;

@SuppressWarnings("serial")
public class SoundEffects extends JFrame {
	private final AppConfig config;
	
	public SoundEffects(AppConfig config) {
		this.config = config;
		initComponents();
	}
	private void initComponents() {
		this.setTitle("Sound Effects");
		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		SoundEffectsPanel mainPanel = new SoundEffectsPanel(config);
		getContentPane().add(mainPanel);
		pack();
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (d.width - getWidth()) / 2; 
		int y = (d.height - getHeight()) / 2; 
		setLocation(x, y);	

		InputStream is = this.getClass().getResourceAsStream("/html/icons/moustache48.png");
		try {
			setIconImage(ImageIO.read(is));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
