package com.example.jpgtovideo;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import com.zhy.imageloader.MainActivity1;
import com.zhy.imageloader.MyAdapter;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.Config;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
/****
 * 
 * @author yanjiaqi 2013.6.6
 *
 */
public class MainActivity extends Activity {
	public static String START = "开始";
	public static String END = "结束";
	public static String PAUSE = "暂停";
	public static String RESTART = "继续";
	EditText et_path;
	Button start,pause;
	private Button choosefile;
	private EditText editText2;
	private ProgressBar progressHorizontal;
	private TextView mTVmainlength;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_path = (EditText)findViewById(R.id.editText1);
        start = (Button)findViewById(R.id.button1);
        pause = (Button)findViewById(R.id.button2);
         progressHorizontal = (ProgressBar) findViewById(R.id.progress_horizontal);
         mTVmainlength=(TextView)this.findViewById(R.id.mTVmainlength);
        start.setText(START);
        editText2 =(EditText)this.findViewById(R.id.editText2);
        pause.setText(PAUSE);
        choosefile =(Button)this.findViewById(R.id.choosefile);
        choosefile.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
			   startActivity(new Intent(getApplicationContext(),MainActivity1.class));
			}
		});
        
        start.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String text = ((Button)v).getText().toString(); 
				if(START.equals(text)){
					VideoCapture.start(MainActivity.this, et_path.getText().toString(),handler,editText2.getText().toString());
					start.setText(END);
				}else
				if(END.equals(text)){
					VideoCapture.stop();
					start.setText(START);
				}				
			}
		});
        pause.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String text = ((Button)v).getText().toString(); 
				if(PAUSE.equals(text)){
					VideoCapture.pause();
					pause.setText(RESTART);
				}else
				if(RESTART.equals(text)){
					VideoCapture.restart();
					pause.setText(PAUSE);
				}
			}
		});
    }
    private Handler handler =new Handler(){
    	public void handleMessage(android.os.Message msg) {
    		switch (msg.what) {
			case 1:
				progressHorizontal.setProgress(Integer.valueOf((String) msg.obj));
			//	Toast.makeText(getApplicationContext(), msg.obj.toString(), Toast.LENGTH_SHORT).show();
				mTVmainlength.setText(MyAdapter.mSelectedImage.size()-2+"");
				break;

			default:
				break;
			}
    	};
    };
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

	
}
