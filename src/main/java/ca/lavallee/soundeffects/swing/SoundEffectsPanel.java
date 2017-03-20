package ca.lavallee.soundeffects.swing;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import ca.lavallee.config.AppConfig;

@SuppressWarnings("serial")
public class SoundEffectsPanel extends JPanel {
	private AppConfig appConfig;

	public SoundEffectsPanel(AppConfig appConfig) {
		this.appConfig = appConfig;
		initComponents();
	}

	public void initComponents() {
		setLayout(new BorderLayout());
		LogoPanel logo = new LogoPanel();
		this.add(logo, BorderLayout.NORTH);
		
		PlaySoundsPanel playSounds = new PlaySoundsPanel("/sounds/birds");
		this.add(playSounds, BorderLayout.CENTER);
		
		FolderChooserPanel folderChooser = new FolderChooserPanel(appConfig, playSounds);
		this.add(folderChooser, BorderLayout.WEST);
		
	}
}
