package ca.lavallee.soundeffects.swing;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class LogoPanel extends JPanel {
	Logger logger = Logger.getLogger(this.getClass().getName());
	
    private JEditorPane htmlPane;
    private URL splashUrl;
    
	public LogoPanel() {
		initComponents();
	}

	private void initComponents() {
        //Create the HTML viewing pane.
        htmlPane = new JEditorPane();
        htmlPane.setEditable(false);
        initHelp();
        JScrollPane htmlView = new JScrollPane(htmlPane);


        Dimension minimumSize = new Dimension(100, 50);
        htmlView.setPreferredSize(new Dimension(600, 125));
        htmlView.setMinimumSize(minimumSize);
        add(htmlView);
	}
	
    private void initHelp() {
        String s = "/html/Splash.html";
        splashUrl = getClass().getResource(s);
        if (splashUrl == null) {
            logger.log(Level.WARNING, "Couldn't open splash file: " + s);
            return;
        }
        try {
			htmlPane.setPage(splashUrl);
		} catch (IOException e) {
			logger.log(Level.WARNING, "Unable to read splash file: " + s);
		}
    }

}
