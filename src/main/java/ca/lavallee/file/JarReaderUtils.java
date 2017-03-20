package ca.lavallee.file;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class JarReaderUtils {
	public static List<String> listFoldersUsingClass(Class<?> clazz, String folderName) throws IOException {
		List<String> files = new ArrayList<>();
		if (isInJar(clazz, folderName)) {
			if (folderName.startsWith("\\") || folderName.startsWith("/")) {
				folderName = folderName.substring(1);
			}
			URL url = clazz.getProtectionDomain().getCodeSource().getLocation();
			ZipInputStream zip = new ZipInputStream(url.openStream());
			while(true) {
				ZipEntry e = zip.getNextEntry();
				if (e == null)
					break;
				String name = e.getName();
				if (!name.endsWith("/")) {
					continue;
				}
				String zipFolder = getFolder(name);
				if (zipFolder.equals(folderName)) {
					continue;
				}
				if (zipFolder.startsWith(folderName)) {
					name = "/" + name.substring(0, name.length() - 1);
//					System.out.println("Found folder " + name);
					files.add(name);
				}
			}
		} else {
			File folder = null;
			if (folderName.contains(":")) {
				folder = new File(folderName);
			} else {
				folder = new File(clazz.getResource(folderName).getPath());
			}
			for (File file: folder.listFiles()) {
				if (file.isDirectory()) {
					String subFolder = file.getPath().replaceAll("\\\\", "/");
					subFolder = subFolder.substring(subFolder.indexOf(folderName));
//					System.out.println(subFolder);
					files.add(subFolder);
				}
			}	
		}
		return files;
	}
	
	private static boolean isInJar(Class<?> clazz, String folderName) {
		if (folderName.contains(":")) {
			return false;
		}
		URL url = clazz.getProtectionDomain().getCodeSource().getLocation();
		return url.getProtocol().equals("file") && url.getPath().endsWith("jar");
	}

	public static List<String> listFilesUsingClass(Class<?> clazz, String folderName, String extension) throws IOException {
//		System.out.println("listFilesUsingClass(" + folderName +", " + extension + ")");
		List<String> files = new ArrayList<>();
		if (isInJar(clazz, folderName)) {
			if (folderName.startsWith("\\") || folderName.startsWith("/")) {
				folderName = folderName.substring(1);
			}
			URL url = clazz.getProtectionDomain().getCodeSource().getLocation();			
			ZipInputStream zip = new ZipInputStream(url.openStream());
			while(true) {
				ZipEntry e = zip.getNextEntry();
				if (e == null)
					break;
				String name = e.getName();
				if (name.endsWith("/")) {
					continue;
				}
				String zipFolder = getFolder(name);
//				System.out.println("Checking file " + name + " in folder " + zipFolder);
				if (zipFolder.equals(folderName)) {
					name = "/" + name;
//					System.out.println("Found file " + zipFolder + getFileName(name));
					files.add(name);
				}
			}
		} else {
			File folder = new File(clazz.getResource(folderName).getPath());
			for (File file: folder.listFiles()) {
				if (!file.isDirectory()) {
					if (getExtension(file.getName()).equalsIgnoreCase(extension)) {
						String subFolder = getFolder(file.getPath().replaceAll("\\\\", "/")) + "/";
						subFolder = subFolder.substring(subFolder.indexOf(folderName));
//						System.out.println(subFolder + file.getName());
						files.add(subFolder + file.getName());
					}
				}
			}	
		}
		return files;
	}
	
	public static String getFolder(String fullFileName) {
		String folder = "";
		String fileName = fullFileName;
		while (fileName.contains("/")) {
			folder += fileName.substring(0, fileName.indexOf("/") + 1);
			fileName = fileName.substring(fileName.indexOf("/") + 1);
		}
		return folder.substring(0, folder.length() - 1);
	}
	
	public static String getExtension(String file) {
		String extension = file;
		while (extension.contains(".")) {
			extension = extension.substring(extension.indexOf(".") + 1);
		}
		return extension;
	}
	
	public static String getFileName(String fullPath) {
		String fileName = fullPath;
		while (fileName.contains("\\") || fileName.contains("/")) {
			int index = fileName.indexOf('\\');
			if (index < 0) {
				index = fileName.indexOf('/');
			}
			fileName = fileName.substring(index + 1);
		}
		return fileName;
	}


}
