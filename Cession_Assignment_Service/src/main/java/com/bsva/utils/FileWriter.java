/**
 * 
 */
package com.bsva.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author jeremym
 * 
 */
public class FileWriter {

	/**
	 * Write the object to disk.
	 * 
	 * @param object
	 * @throws IOException
	 */
	public static void write(String file, String fileName) throws IOException {

		Files.write(Paths.get(fileName), file.getBytes());

	}

}
