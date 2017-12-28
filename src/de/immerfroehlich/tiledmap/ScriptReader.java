package de.immerfroehlich.tiledmap;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;

class ScriptReader {
	
   public static String readScript(String scriptPath) {
      final File scriptFile = new File(scriptPath);

      try {
         if (Integer.MAX_VALUE < scriptFile.length()) {
            // nicht schön aber irgendwo wollte ich das überprüfen. schönere
            // lösungen sind willkommen.
            throw new Exception() {
               private static final long serialVersionUID = 1357878907593184244L;

               @Override
               public String getMessage() {
                  return "Script File is to long to be proessesed length:" + scriptFile.length() + " max allowed: "
                        + Integer.MAX_VALUE;
               }
            };
         }

         readScript(new FileInputStream(scriptFile));
         
      } catch (Exception e) {
         System.err.println(e.getMessage());
         e.printStackTrace();
      }
      return null;
   }
   
   public static String readScript(InputStream scriptStream) {
	   String script = null;
	   try {
		script = IOUtils.toString(scriptStream);
	   } catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	   }
	   
	   return script;
   }

}
