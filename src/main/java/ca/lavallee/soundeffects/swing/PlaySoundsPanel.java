package ca.lavallee.soundeffects.swing;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

@SuppressWarnings("serial")
public class PlaySoundsPanel extends JPanel {
	private JPanel buttonPanel;
	
	public PlaySoundsPanel(File soundFolder) {
		initComponents(soundFolder);
	}
	
	private void initComponents(File soundFolder) {
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridBagLayout());
		setSoundFolder(soundFolder);
		JScrollPane pane = new JScrollPane(buttonPanel);
		
		add(pane);
				
	}
	
	public void setSoundFolder(File soundFolder) {
		buttonPanel.removeAll();
		
		GridBagConstraints c = new GridBagConstraints();
		
		int y = 0;
		for (File file: soundFolder.listFiles()) {
			if (file.isFile() && getExtension(file).equalsIgnoreCase("wav")) {
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
				
				buttonPanel.add(btn, c);
			}
		}
		buttonPanel.revalidate();
		buttonPanel.repaint();
	}
	
	
	private String getExtension(File file) {
		String extension = file.getName();
		while (extension.contains(".")) {
			extension = extension.substring(extension.indexOf(".") + 1);
		}
		return extension;
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
