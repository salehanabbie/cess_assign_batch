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

public class ZipUtil {
  private static Logger log = LogManager.getLogger(ZipUtil.class);


  public static void zipFolder(final Path sourceFolderPath, final Path zipPath) throws Exception
  {
    log.info("In the zipFolder method ");
    final ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipPath.toFile()));

    Files.walkFileTree(sourceFolderPath, new SimpleFileVisitor<Path>() {
      public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        zos.putNextEntry(new ZipEntry(sourceFolderPath.relativize(file).toString()));
//                log.info("File = "+file);
//                log.info("zos = "+zos);
        Files.copy(file, zos);
        Files.delete(file);
        zos.closeEntry();
        return FileVisitResult.CONTINUE;
      }
    });
    zos.close();
  }



//	SalehaR	- 2020/04/28 - Easier Method to Zip File
//    List<String> fileList;
//    private static final String OUTPUT_ZIP_FILE = "C:\\home\\MyFile.zip";
//    private static final String SOURCE_FOLDER = "C:\\testzip";
//
//    public ZipUtil() {
//        fileList = new ArrayList<String>();
//
//    }
//
//    public boolean zipFiles() {
//    	generateFileList(new File(SOURCE_FOLDER));
//    	return zipIt(OUTPUT_ZIP_FILE);
//    }
//
//    /**
//     * Zip it
//     *
//     * @param zipFile
//     *            output ZIP file location
//     */
//    public boolean zipIt(String zipFile) {
//	    boolean zipComplete = false;
//    	log.info("In the zipIt method");
//        byte[] buffer = new byte[1024];
//
//        try {
//
//            FileOutputStream fos = new FileOutputStream(zipFile);
//            ZipOutputStream zos = new ZipOutputStream(fos);
//
//            log.info("Output to Zip : " + zipFile);
//
//            for (String file : this.fileList) {
//
//                log.info("File Added : " + file);
//                ZipEntry ze = new ZipEntry(file);
//                zos.putNextEntry(ze);
//
//                FileInputStream in = new FileInputStream(SOURCE_FOLDER + File.separator + file);
//
//                int len;
//                while ((len = in.read(buffer)) > 0) {
//                    zos.write(buffer, 0, len);
//                }
//
//                in.close();
//            }
//
//            zos.closeEntry();
//            // remember close it
//            zos.close();
//            zipComplete = true;
//            log.info("Done");
//        } catch (IOException ex) {
//            ex.printStackTrace();
//            zipComplete = false;
//        }
//        return zipComplete;
//    }
//
//    /**
//     * Traverse a directory and get all files, and add the file into fileList
//     *
//     * @param node
//     *            file or directory
//     */
//    public void generateFileList(File node) {
//    	log.info("In the generateFileList method");
//        // add file only
//        if (node.isFile()) {
//            fileList.add(generateZipEntry(node.getAbsoluteFile().toString()));
//        }
//
//        if (node.isDirectory()) {
//            String[] subNote = node.list();
//            for (String filename : subNote) {
//                generateFileList(new File(node, filename));
//            }
//        }
//
//    }
//
//    /**
//     * Format the file path for zip
//     *
//     * @param file
//     *            file path
//     * @return Formatted file path
//     */
//    private String generateZipEntry(String file) {
//        return file.substring(SOURCE_FOLDER.length() + 1, file.length());
//    }
}

