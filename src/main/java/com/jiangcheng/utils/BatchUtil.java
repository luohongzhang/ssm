package com.jiangcheng.utils;

import java.io.File;

/**
 * 
 * @author Kivin.Jiang
 *
 */
public class BatchUtil {

	public static boolean rename(String AbsolutePath,String newName,String Suffix){
		File file = new File(AbsolutePath);
		if(!file.exists()){
			System.out.println(AbsolutePath+" does not exist!");
			return false;
		}
		String filename = file.getAbsolutePath();
		if(filename.indexOf(".") >= 0){
			filename = filename.substring(0,filename.lastIndexOf("\\") + 1);
			filename += newName;
		}
		if(!file.renameTo(new File(filename + Suffix))){
			System.out.println(AbsolutePath + " fails to rename!");
			return false;
		}
		return true;
	}
	
	public static void main(String[] args) {
		long starttime = System.currentTimeMillis();
		String path = "D:\\ORL1\\s";
        String suffix = ".bmp";
        for (int i = 1; i <= 40; i++) {
            String pathTemp = path + i + "\\";
            for (int j = 10; j >= 1; j--) {
                rename(pathTemp + j + suffix, "" + (j + 1), suffix);
            }
            rename(pathTemp + "11" + suffix, "1", suffix);
        }
        long endtime = System.currentTimeMillis();
        System.out.println("Time:" + new Long(endtime - starttime));//��ʱ
    }
}
