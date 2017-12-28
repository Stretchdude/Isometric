package de.immerfroehlich.tiledmap;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ZipExtractor {
	
	ZipFile zipFile;
	
//    public static void main(String[] args) {
//        try {
//            // Open the ZIP file
//            ZipFile zf = new ZipFile("res/game.zip");
//
//            // Enumerate each entry
//            for (Enumeration<? extends ZipEntry> entries = zf.entries(); entries.hasMoreElements();) {
//                // Get the entry name
//                ZipEntry entry = entries.nextElement();
//                String zipEntryName = entry.getName();
//                System.out.println(zipEntryName);
//            }
//            
//            ZipEntry entry = new ZipEntry("start_map/start_map.tmx");
//            InputStream stream = zf.getInputStream(entry);
//            System.out.println(stream.read());
//        }
//        catch (IOException e) {
//        }
//
//    }
    
    public void openZipFile(String fileName) {
    	try {
			zipFile = new ZipFile("res/game.zip");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void closeZipFile() {
    	try {
			zipFile.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	zipFile = null;
    }
    
    public InputStream getZipEntry(String name) {
    	ZipEntry entry = new ZipEntry(name);
    	InputStream stream = null;
        try {
			stream = zipFile.getInputStream(entry);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		try {
//			int back = stream.read();
//			System.out.println(back);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		return stream;
    }

}