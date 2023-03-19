package com.vikramdesh1.organizer;

import java.io.File;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.HashSet;
import java.util.Set;

public class Organizer {

	public static void main(String[] args) {
		Set<String> fileExtensions = new HashSet<>();
		int directoryCounter = 0, fileCounter = 0, movedPhotos = 0, movedVideos = 0, movedLivePhotos = 0,
				movedRAWPhotos = 0;
		try {
			File outputLog = new File("D:\\photoMoverEOST8iRAW.txt");
			PrintStream ps = new PrintStream(outputLog);
			System.setOut(ps);
			// Scan base directory
			File directory = new File("C:\\Canon EOS T8i\\RAW");
			String contents[] = directory.list();
			directoryCounter = 0;
			fileCounter = 0;
			for (String d : contents) {
				// Scan subdirectories
				File subDirectory = new File("C:\\Canon EOS T8i\\RAW\\" + d);
				if (subDirectory.isDirectory()) {
					directoryCounter++;
					System.out.println("Dir : " + subDirectory.getName());
					String subContents[] = subDirectory.list();
					for (String f : subContents) {
						// Scan files
						File file = new File("C:\\Canon EOS T8i\\RAW\\" + d + "\\" + f);
						if (file.isFile()) {
							fileCounter++;
							System.out.print("File : " + f + " --> ");
							String extension = f.substring(f.lastIndexOf(".") + 1);
							System.out.println(extension + " : " + (file.length() / 1000L) + " kilobytes");
							fileExtensions.add(extension);
							// Move files
							if (extension.equalsIgnoreCase("jpg") || extension.equalsIgnoreCase("jpeg")
									|| extension.equalsIgnoreCase("heic")) {
								movedPhotos++;
								Files.copy(file.toPath(),
										new File(
												"D:\\EOS T8i\\Photos\\" + String.valueOf(movedPhotos) + "." + extension)
												.toPath(),
										StandardCopyOption.REPLACE_EXISTING);
							}
							if (extension.equalsIgnoreCase("cr3")) {
								movedRAWPhotos++;
								Files.copy(file.toPath(),
										new File(
												"D:\\EOS T8i\\RAW\\" + String.valueOf(movedRAWPhotos) + "." + extension)
												.toPath(),
										StandardCopyOption.REPLACE_EXISTING);
							}
							if (extension.equalsIgnoreCase("mov")) {
								if (file.length() < 10000000L) {
									movedLivePhotos++;
									Files.copy(
											file.toPath(), new File("D:\\EOS T8i\\Live Photos\\"
													+ String.valueOf(movedLivePhotos) + "." + extension).toPath(),
											StandardCopyOption.REPLACE_EXISTING);
								} else {
									movedVideos++;
									Files.copy(
											file.toPath(), new File("D:\\EOS T8i\\Videos\\"
													+ String.valueOf(movedVideos) + "." + extension).toPath(),
											StandardCopyOption.REPLACE_EXISTING);
								}

							}
							if (extension.equalsIgnoreCase("mp4")) {
								movedVideos++;
								Files.copy(file.toPath(),
										new File(
												"D:\\EOS T8i\\Videos\\" + String.valueOf(movedVideos) + "." + extension)
												.toPath(),
										StandardCopyOption.REPLACE_EXISTING);
							}
						}
					}
					System.out.println();
				}
			}
			System.out.println("Found the following set of file extensions - " + fileExtensions);
			System.out.println("Iterated through " + directoryCounter + " directories and " + fileCounter + " files");
			System.out.println("Moved " + movedPhotos + " photos, " + movedRAWPhotos + " RAW photos, " + movedLivePhotos
					+ " live photos and " + movedVideos + " videos" + " - Total : "
					+ (movedPhotos + movedLivePhotos + movedVideos));
		} catch (Exception e) {
			System.err.println(e);
		}
	}

}
