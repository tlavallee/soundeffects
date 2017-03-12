package ca.lavallee.soundeffects.swing;

import java.io.File;

import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;

import ca.lavallee.config.AppConfig;

@SuppressWarnings("serial")
public class FolderChooserPanel extends JPanel implements TreeSelectionListener {
	private AppConfig appConfig;
    private JTree tree;
    private final PlaySoundsPanel playSounds;

	public FolderChooserPanel(AppConfig appConfig, PlaySoundsPanel playSoundsPanel) {
		this.appConfig = appConfig;
		this.playSounds = playSoundsPanel;
		initComponents();
	}

	private void initComponents() {
        //Create the nodes.
        DefaultMutableTreeNode top =
            new DefaultMutableTreeNode("Available Sound Folders");

        //Create a tree that allows one selection at a time.
        tree = new JTree(top);
        tree.getSelectionModel().setSelectionMode
                (TreeSelectionModel.SINGLE_TREE_SELECTION);

        //Listen for when the selection changes.
        tree.addTreeSelectionListener(this);
//        tree.setRootVisible(false);
 
        
		File folder = new File(this.getClass().getResource("/sounds").getPath());
		addSoundFolder(folder, top);

		for (String folderName: appConfig.getStringValues("folders")) {
			folder = new File(folderName);
	        DefaultMutableTreeNode folderNode = new DefaultMutableTreeNode(new SoundFolderNode(folder, folder.getName()));
	        top.add(folderNode);
			addSoundFolder(new File(folderName), top);
		}
		
        this.add(tree);
	}

	private void addSoundFolder(File folder, DefaultMutableTreeNode parent) {
		for (File file: folder.listFiles()) {
			if (file.isDirectory()) {
		        DefaultMutableTreeNode folderNode = new DefaultMutableTreeNode(new SoundFolderNode(file, file.getName()));
		        parent.add(folderNode);
		        addSoundFolder(file, folderNode);
			}	
		}
	}
	
	@Override
	public void valueChanged(TreeSelectionEvent arg0) {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode)
				tree.getLastSelectedPathComponent();

		if (node == null) return;

		Object nodeInfo = node.getUserObject();
		if (node.isLeaf()) {
			System.out.println("Leaf");
			SoundFolderNode soundFolder = (SoundFolderNode) node.getUserObject();
			playSounds.setSoundFolder(soundFolder.getFolder());
		} else {
	        tree.expandRow(0);
			System.out.println("Not leaf");
		}
		System.out.println(nodeInfo.toString());
	}
	
	class SoundFolderNode {
		private final File folder;
		private final String name;
		public SoundFolderNode(File folder, String name) {
			this.folder = folder;
			this.name = name;
		}
		public File getFolder() {
			return folder;
		}
		public String getName() {
			return name;
		}
		@Override
		public String toString() {
			return name;
		}
		
	}

}
