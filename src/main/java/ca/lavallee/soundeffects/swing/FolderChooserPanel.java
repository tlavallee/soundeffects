package ca.lavallee.soundeffects.swing;

import java.io.IOException;

import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

import ca.lavallee.config.AppConfig;
import ca.lavallee.file.JarReaderUtils;

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
 
        
		addSoundFolder("/sounds", top);

		for (String folderName: appConfig.getStringValues("folders")) {
			String displayName = folderName;
			if (displayName.endsWith("/")) {
				JarReaderUtils.getFileName(folderName.substring(0, folderName.length() - 1));
			}
	        DefaultMutableTreeNode folderNode = new DefaultMutableTreeNode(new SoundFolderNode(folderName, displayName));
	        top.add(folderNode);
			addSoundFolder(folderName, top);
		}
		
        this.add(tree);
	}

	private void addSoundFolder(String folder, DefaultMutableTreeNode parent) {
//		System.out.println("addSoundFolder(" + folder + ")");
		try {
			for (String subFolder: JarReaderUtils.listFoldersUsingClass(getClass(), folder)) {
				String displayName = JarReaderUtils.getFileName(subFolder.substring(0, subFolder.length() - 1));
				DefaultMutableTreeNode folderNode = new DefaultMutableTreeNode(new SoundFolderNode(subFolder, displayName));
				parent.add(folderNode);
				addSoundFolder(subFolder, folderNode);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void valueChanged(TreeSelectionEvent arg0) {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode)
				tree.getLastSelectedPathComponent();

		if (node == null) return;

//		Object nodeInfo = node.getUserObject();
		if (node.isLeaf()) {
//			System.out.println("Leaf");
			SoundFolderNode soundFolder = (SoundFolderNode) node.getUserObject();
			playSounds.setSoundFolder(soundFolder.getFolder());
		} else {
	        tree.expandRow(0);
//			System.out.println("Not leaf");
		}
//		System.out.println(nodeInfo.toString());
	}
	
	class SoundFolderNode {
		private final String folder;
		private final String name;
		public SoundFolderNode(String folder, String name) {
			this.folder = folder;
			this.name = name;
		}
		public String getFolder() {
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
