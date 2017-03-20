package ca.lavallee.soundeffects.swing;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import ca.lavallee.file.JarReaderUtils;

@SuppressWarnings("serial")
public class PlaySoundsPanel extends JPanel {
	private JPanel buttonPanel;
	
	public PlaySoundsPanel(String soundFolder) {
//		System.out.println("Sound folder: " + soundFolder);
		initComponents(soundFolder);
	}
	
	private void initComponents(String soundFolder) {
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridBagLayout());
		setSoundFolder(soundFolder);
		JScrollPane pane = new JScrollPane(buttonPanel);
		
		add(pane);
				
	}
	
	public void setSoundFolder(String soundFolder) {
//		try {
//			List<String> names = JarFileUtils.listFoldersUsingClass(getClass(), soundFolder.getPath());
//		} catch (IOException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
		buttonPanel.removeAll();
		
		GridBagConstraints c = new GridBagConstraints();
		
		int y = 0;
		try {
			for (String fullPath: JarReaderUtils.listFilesUsingClass(getClass(), soundFolder, "wav")) {
				String fileName = JarReaderUtils.getFileName(fullPath);
				JButton btn = new JButton(fileName.substring(0, fileName.length() - 4));
				btn.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						playClip(fullPath);
					}
				});
				c = new GridBagConstraints();
				c.fill = GridBagConstraints.HORIZONTAL;
				c.gridx = 0;
				c.gridy = y++;
				c.weightx = 1.0;

				buttonPanel.add(btn, c);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		buttonPanel.revalidate();
		buttonPanel.repaint();
	}	
	
	private void playClip(String waveFile) {
//		System.out.println("playClip(" + waveFile + ")");
		try {
			Clip clip = AudioSystem.getClip();
			AudioInputStream inputStream = AudioSystem.getAudioInputStream(getClass().getResourceAsStream(waveFile));
			clip.open(inputStream);
			clip.start(); 
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

	}


}
