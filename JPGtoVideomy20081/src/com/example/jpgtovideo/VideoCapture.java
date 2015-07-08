package com.example.jpgtovideo;

import static com.googlecode.javacv.cpp.opencv_highgui.cvLoadImage;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.Config;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.TextureView;
import android.widget.Toast;

import com.googlecode.javacv.FFmpegFrameRecorder;
import com.googlecode.javacv.FrameRecorder.Exception;
import com.googlecode.javacv.cpp.opencv_core;
import com.zhy.imageloader.MainActivity1;
import com.zhy.imageloader.MyAdapter;
/**
 *	
 */
public class VideoCapture {
		private static int switcher = 0;//录像键
		private static boolean isPaused = false;//暂停键
		private static String filepath = "";  
		private static String filename = null; 
		private static Handler handlerv;
		private static Context context;
		private static Double arandom;
		public static  int INDEX_MAX =3;
		public static void start(Context context,String path,final Handler handler,String a){
			//init
			 INDEX_MAX = MyAdapter.mSelectedImage.size()-2;
			VideoCapture.context = context;
			if(path!=null){
				filepath = path;
			}else{
				filepath = "1111";
			}
		  handlerv =handler;
		  if(!TextUtils.isEmpty(a)){
		  arandom =Double.valueOf(a);
		  }else{
			  arandom=(double) 5;
		  }
			switcher = 1;
			new Thread(){
				private FFmpegFrameRecorder recorder;
				private File file;

				public void run(){
					
					OutputStream os = null;
					try {
					new FileUtils().creatSDDir(filepath);
					filename ="test.mp4";
					if(MyAdapter.mSelectedImage==null){
						return;
					}
					try{
					if(!new FileUtils().isFileExist("video.jpg",filepath)){					
						file = new FileUtils().createFileInSDCard("video.jpg", filepath);
					}else{
						file = new FileUtils().getFile("video.jpg",filepath);
					}
					os = new FileOutputStream(file);
					
					Bitmap frame =BitmapFactory.decodeFile(MyAdapter.mSelectedImage.get(0));
					Bitmap frame1 =comp(frame);
					frame1.compress(Bitmap.CompressFormat.JPEG, 100, os);
					os.flush();
					os.close();
					 recorder = new FFmpegFrameRecorder(
							new FileUtils().getSDCardRoot()+filepath+File.separator+filename,
							frame1.getWidth(),
							frame1.getHeight());
					
					recorder.setFormat("mp4");
					recorder.setFrameRate(arandom);//录像帧率
					recorder.start();

					}
					catch(java.lang.Exception e){
						e.printStackTrace();
					}
/*
					//Bitmap testBitmap = getImageFromAssetsFile("1.jpg");
					Bitmap	testBitmap=  BitmapFactory.decodeFile(MyAdapter.mSelectedImage.get(0));
					*/
					
					
					
					int index = 0;
					while(switcher!=0){
							if(!isPaused){	
								try{
								os = new FileOutputStream(file);
								Bitmap frame2 =BitmapFactory.decodeFile(MyAdapter.mSelectedImage.get(index));
								Bitmap frame3 =comp(frame2);
								//Bitmap frame3 = compressImage(frame2);
								frame3.compress(Bitmap.CompressFormat.JPEG, 100, os);
								Log.i("VIDEOCAPTURE","index:"+index+"");
								os.flush();
								os.close();
								frame3.recycle();
								frame3 = null;
								frame2.recycle();
								frame2 = null;
								opencv_core.IplImage image = cvLoadImage(new FileUtils().getSDCardRoot()+filepath+File.separator+"video.jpg");
								recorder.record(image);
								Log.i("VIDEOCAPTURE","!!! time++++:");
								}catch(java.lang.Exception e){
									
								}
	
							/*	File file = null;
								if(!new FileUtils().isFileExist("video.jpg",filepath)){					
									file = new FileUtils().createFileInSDCard("video.jpg", filepath);
								}else{
									file = new FileUtils().getFile("video.jpg",filepath);
								}
								os = new FileOutputStream(file);
								Bitmap frame = getImageFromAssetsFile(index+".jpg");
								
								list.add("/storage/emulated/0/AndroidData/LJPPFF_20150427111833");
								list.add("/storage/emulated/0/AndroidData/LJPPFF_20150427112024");
								list.add("/storage/emulated/0/AndroidData/LJPPFF_20150427112344");
								list.add("/storage/emulated/0/AndroidData/LJPPFF_20150427112355");
								list.add("/storage/emulated/0/AndroidData/LJPPFF_20150427112405");
								list.add("/storage/emulated/0/AndroidData/LJPPFF_20150427113504");

								
								frame.compress(Bitmap.CompressFormat.JPEG, 100, os);
								Log.i("VIDEOCAPTURE","index:"+index+"");
								os.flush();
								os.close();
								frame.recycle();
								frame = null;*/
								
							//	opencv_core.IplImage image = cvLoadImage(new FileUtils().getSDCardRoot()+filepath+File.separator+"video.jpg");
							//	opencv_core.IplImage image =null;
								/* int n = (int)(Math.random()*6+1);
								if(n==1){
									 image = cvLoadImage("/storage/emulated/0/AndroidData/LJPPFF_20150427113504.png");

								}else if(n==2){
									 image = cvLoadImage("/storage/emulated/0/AndroidData/LJPPFF_20150427112024.png");

								}else if(n==3){
									 image = cvLoadImage("/storage/emulated/0/AndroidData/LJPPFF_20150427112344.png");

								}else if(n==4){
									 image = cvLoadImage("/storage/emulated/0/AndroidData/LJPPFF_20150427112355.png");

								}else if(n==5){
									 image = cvLoadImage("/storage/emulated/0/AndroidData/LJPPFF_20150427112405.png");

								}else if(n==6){
									 image = cvLoadImage("/storage/emulated/0/AndroidData/LJPPFF_20150427113504.png");

								}else{
								 image = cvLoadImage("/storage/emulated/0/AndroidData/LJPPFF_20150427113504.png");
								}*/
							//	Log.i("VIDEOCAPTURE",MyAdapter.mSelectedImage.get(index));
							//	Log.i("VIDEOCAPTURE","!!! time++++:"+index);
							//	 image = cvLoadImage(MyAdapter.mSelectedImage.get(index));

								
							}
							if(index>INDEX_MAX){
								index = 0;
								Log.i("VIDEOCAPTURE","index = 1");
								switcher=0;
								Log.i("VIDEOCAPTURE","recorder.stop();");
							}else{
								index++;
								Log.i("VIDEOCAPTURE","	index++;"+INDEX_MAX);
								Message msg =new Message();
								msg.what=1;
								msg.obj=index*100/INDEX_MAX +"";  
								if(index*100/INDEX_MAX<=100)
                                 handler.sendMessage(msg);
							}
						}
					try {
						recorder.stop();
						recorder=null;
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
						Log.i("VIDEOCAPTURE","	index++;recorder.stop");
					}catch(FileUtils.NoSdcardException e){
						e.printStackTrace();
						Log.i("VIDEOCAPTURE","eeeeFileUtils.NoSdcardException "+e.toString());
					}
					
				}
			}.start();
		}
		public static void stop(){
			switcher = 0;
			isPaused = false;
		}
		public static void pause(){
			if(switcher==1){
				isPaused = true;
			}
		}
		public static void restart(){
			if(switcher==1){
				isPaused = false;
			}
		}
		public static boolean isStarted(){
			if(switcher==1){
				return true;
			}else{
				return false;
			}
		}
		public static boolean isPaused(){
			return isPaused;
		}
		private static Bitmap getImageFromAssetsFile(String filename){
			Bitmap image = null;
			AssetManager am = context.getResources().getAssets();
			try{			
				InputStream is = am.open(filename);
				image = BitmapFactory.decodeStream(is);
				is.close();
			}catch(IOException e){
				e.printStackTrace();
			}
			return image;
		}
		
		/**
		 * 压缩图片
		 * @param image
		 * @return
		 */
		public static   Bitmap comp(Bitmap image) {
			 ByteArrayOutputStream baos = new ByteArrayOutputStream();         
			    image.compress(Bitmap.CompressFormat.JPEG, 100, baos);  
			    if( baos.toByteArray().length / 1024>513) {//判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出    
			        baos.reset();//重置baos即清空baos  
			        image.compress(Bitmap.CompressFormat.JPEG, 50, baos);//这里压缩50%，把压缩后的数据存放到baos中  
			    }  
			    ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());  
			    BitmapFactory.Options newOpts = new BitmapFactory.Options();  
			    //开始读入图片，此时把options.inJustDecodeBounds 设回true了  
			    newOpts.inJustDecodeBounds = true;  
			    Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);  
			    newOpts.inJustDecodeBounds = false;  
			    int w = newOpts.outWidth;  
			    int h = newOpts.outHeight;  
			    //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为  
			    float hh =800f;//这里设置高度为800f  
			    float ww = 480f;//这里设置宽度为480f  
			    //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可  
			    int be = 1;//be=1表示不缩放  
			    if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放  
			        be = (int) (newOpts.outWidth / ww);  
			    } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放  
			        be = (int) (newOpts.outHeight / hh);  
			    }  
			    if (be <= 0)  
			        be = 1;  
			    newOpts.inSampleSize = be;//设置缩放比例  
			    newOpts.inPreferredConfig = Config.RGB_565;
			    //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了  
			    isBm = new ByteArrayInputStream(baos.toByteArray());  
			    bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
		        try {
					isBm.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

		        return compressImage(bitmap);//压缩好比例大小后再进行质量压缩  
		        //  return bitmap;
		}
		/**
		 * 质量压缩
		 * @param image
		 * @return 
		 */
		public static   Bitmap compressImage(Bitmap image) {  
	        ByteArrayOutputStream baos = new ByteArrayOutputStream();  
	        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中  
	        int options = 90;  
	        while ( baos.toByteArray().length / 1024>100) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩         
	            baos.reset();//重置baos即清空baos  
	            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中  
	            options -= 10;//每次都减少10  
	        }  
	        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中  
	        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片  
	        try {
				isBm.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        return bitmap;  
	    } 
		
		
		
		
		
}
