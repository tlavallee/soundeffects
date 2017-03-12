package ca.lavallee.soundeffects.swing;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import ca.lavallee.config.AppConfig;

public class SoundEffects extends JFrame {
	private File soundFolder = new File(this.getClass().getResource("/sounds").getPath());
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
//		JPanel panel = new LogoPanel();
//		getContentPane().add(panel);
		
//		panel = new ButtonPanel();
//		
//		getContentPane().add(panel);
		pack();
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (d.width - getWidth()) / 2; 
		int y = (d.height - getHeight()) / 2; 
		setLocation(x, y);	

	}

	class ButtonPanel extends JPanel {
		
		public ButtonPanel() {
			initComponents();
		}
		
		private void initComponents() {
			setLayout(new GridBagLayout());
			
			GridBagConstraints c = new GridBagConstraints();
			
			int y = 0;
			for (File file: soundFolder.listFiles()) {
				if (file.isFile()) {
					JButton btn = new JButton(file.getName().substring(0, file.getName().length() - 4));
					btn.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							playClip(file);
						}
					});
					c = new GridBagConstraints();
					c.fill = GridBagConstraints.HORIZONTAL;
					c.gridx = 0;
					c.gridy = y++;
					c.weightx = 1.0;
					
					add(btn, c);
				}
			}
			
		}
		private void playClip(File waveFile) {
		      try {
		          Clip clip = AudioSystem.getClip();
		          AudioInputStream inputStream = AudioSystem.getAudioInputStream(waveFile);
		          clip.open(inputStream);
		          clip.start(); 
		        } catch (Exception e) {
		          System.err.println(e.getMessage());
		        }
			
		}


	}
}
