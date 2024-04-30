package com.bsva.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * @author SalehaR
 *
 */
public class ZipUtil {
	private static Logger log = LogManager.getLogger(ZipUtil.class);

	public static void zipFolder(final Path sourceFolderPath, final Path zipPath) throws Exception 
	{	
       final ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipPath.toFile()));
        
        Files.walkFileTree(sourceFolderPath, new SimpleFileVisitor<Path>() {
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            	ZipEntry ze = new ZipEntry(sourceFolderPath.relativize(file).toString());
            
            	ze.setTime(attrs.lastModifiedTime().toMillis());
                zos.putNextEntry(ze);          
                
                Files.copy(file, zos);
                Files.delete(file);
                zos.closeEntry();
                return FileVisitResult.CONTINUE;
            }
        });
        zos.close();
    }
}

