package com.example.jpgtovideo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class FileUtils {
	private String SDCardRoot;
	private static boolean isCardExist;
	
	public FileUtils() throws NoSdcardException {
		getSDCardRoot();
	}
	
	public String getSDCardRoot() throws NoSdcardException{
		if(isCardExist()){
			SDCardRoot = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;
		}else{
			throw new NoSdcardException();
		}
		return SDCardRoot;
	}
	
	public static boolean isCardExist(){
		isCardExist = Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)?true:false;
		return isCardExist;	
	}
	public File createFileInSDCard(String fileName, String dir)
			throws IOException {
		File file = new File(SDCardRoot + dir + File.separator + fileName);
		if(!file.exists()){	
			file.getParentFile().mkdirs();
			file.createNewFile();
		}
		return file;
	}
	public File creatSDDir(String dir) {
		File dirFile = new File(SDCardRoot + dir + File.separator);
		dirFile.mkdirs();

		return dirFile;
	}
	public boolean filterFileExist(String path, String filter) {
		File file = new File(SDCardRoot + path + File.separator);
		if (file.exists() && file.isDirectory()) {

			String[] fileNames = file.list(new FilenameFilter() {
				public boolean accept(File dir, String filename) {
					return filename.endsWith(".png");
				}
			});
			if (fileNames.length > 0) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	/**
	 * 判断SD卡上的文件是否存�?
	 */
	public boolean isFileExist(String fileName, String path) {
		File file = new File(SDCardRoot + path + File.separator + fileName);
		return file.exists();
	}
	public File getFile(String fileName,String path){
		File file = new File(SDCardRoot + path + File.separator + fileName);
		return file;
	}
	public void deleteFile(String fileName, String path) {
		File file = new File(SDCardRoot + path + File.separator + fileName);
		boolean result = file.delete();
	}
	
	public void closeInputStream(InputStream inputStream){
		if(inputStream!=null){
			try {
				inputStream.close();
			} catch (IOException e) {
				Log.e("error", "close failed");
				e.printStackTrace();
			}
		}
	}
	public class NoSdcardException extends Exception{
		
	}
}
