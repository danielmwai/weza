/*
 * The MIT License
 *
 * Copyright 2013 Daniel Mwai <naistech.gmail.com>.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.tunaweza.core.business.utils;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * @version $Revision: 1.1.1.1 $
 * @since Build {3.0.0.SNAPSHOT} (06 2013)
 * @author Daniel mwai
 */
public class FileUtils {
/* remove leading whitespace */
	public static String ltrim(String source) {
		return source.replaceAll("^\\s+", "");
	}

	/* remove trailing whitespace */
	public static String rtrim(String source) {
		return source.replaceAll("\\s+$", "");
	}

	/* replace multiple whitespaces between words with single blank */
	public static String itrim(String source) {
		return source.replaceAll("\\b\\s{2,}\\b", " ");
	}

	/* remove all superfluous whitespaces in source string */
	public static String trim(String source) {
		return itrim(ltrim(rtrim(source)));
	}

	public static String lrtrim(String source) {
		return ltrim(rtrim(source));
	}

	public static void createOrAppendFile(File f, String text) throws IOException {
		BufferedWriter bw = new BufferedWriter(new FileWriter(f, true));
		bw.write(text);
		bw.newLine();
		bw.flush();
		bw.close();
	}

	public static void createFile(File f, String text) throws IOException {
		BufferedWriter bw = new BufferedWriter(new FileWriter(f, false));
		bw.write(text);
		bw.newLine();
		bw.flush();
		bw.close();
	}

	public void createFile(String fileName, String content) throws IOException {
		File f = new File(fileName);
		createOrAppendFile(f, content);
	}

	/**
	 * Read text file
	 * 
	 * @return
	 */
	public static String readTextFile(String filename) {
		FileInputStream fstream;
		String outputPassword = "";
		try {
			fstream = new FileInputStream(filename);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			// Read File Line By Line
			while ((strLine = br.readLine()) != null) {
				outputPassword += strLine;
			}
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return outputPassword;
	}

	public void createDocTextFile(String fileName) throws IOException {
		// path to create
		FileWriter ryt = new FileWriter(fileName);
		BufferedWriter out = new BufferedWriter(ryt);
		out.write("");
		out.close();
	}

	public boolean deleteDir(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					System.out
							.println("Cannot delete " + dir.getAbsolutePath());
					return false;
				}
			}
		}

		// The directory is now empty so delete it
		return dir.delete();
	}
	
	public static void streamCharacterData(String urlstr, String format,
			PrintWriter outstr, HttpServletResponse resp, HttpServletRequest request) {
		String ErrorStr = null;
		try {
			// find the right mime type and set it as contenttype
			InputStream in = null;
			try {
				URL url = new URL(request.getScheme(), request.getServerName(),
						request.getServerPort(), urlstr);
				URLConnection urlc = url.openConnection();
				int length = urlc.getContentLength();
				in = urlc.getInputStream();
				resp.setContentType(format);
				resp.setContentLength(length);
				int ch;
				while ((ch = in.read()) != -1) {
					outstr.print((char) ch);
				}
			} catch (Exception e) {
				e.printStackTrace();
				ErrorStr = "Error Streaming the Data";
				outstr.print(ErrorStr);
			} finally {
				if (in != null) {
					in.close();
				}
				if (outstr != null) {
					outstr.flush();
					outstr.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static String getExtension(String fileName) {
		int indexOfDot;
		int fromIndex = 0;
		String extension = null;
		while ((indexOfDot = fileName.indexOf(".", fromIndex)) != -1) {
			extension = fileName.substring(indexOfDot);
			fromIndex = indexOfDot + 1;
		}
		return extension.substring(1).toUpperCase();
	}
}
