package com.jiangcheng.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
public class ContextReplace {
	
	private static String srcStr = "charset=gb2312";
	private static String desStr = "charset=utf-8";
	private static String srcDir = "f:\\test";
	private static FileFilter filter = new FileFilter() {

		public boolean accept(File pathname) {
			if (pathname.isDirectory()|| (pathname.isFile() && pathname.getName().endsWith(".html"))) {
				return true;
			} else {
				return false;
			}
		}
	};

	public static void readDir(File file) {
		File[] files = file.listFiles(filter);
		for (File subFile : files) {
			if (subFile.isDirectory()) {
				readDir(subFile);
			}
			else {
				System.err.println(" \t" + subFile.getAbsolutePath());
				System.err.println("---------------------------");
				try {
					replace(subFile.getAbsolutePath(), srcStr, desStr);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void replace(String infilename, String from, String to)
			throws IOException, UnsupportedEncodingException {
		File infile = new File(infilename);
		BufferedReader in = new BufferedReader(new InputStreamReader(
				new FileInputStream(infile), "utf-8"));
		File outfile = new File(infile + ".tmp");
		PrintWriter out = new PrintWriter(new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream(outfile), "utf-8")));
		String reading;
		while ((reading = in.readLine()) != null) {
			out.println(reading.replaceAll(from, to));
		}
		out.close();
		in.close();
		infile.delete();
		outfile.renameTo(infile);

	}

	public static void main(String[] args) {
		File srcFile = new File(srcDir);
		readDir(srcFile);
		srcFile = null;
	}

}