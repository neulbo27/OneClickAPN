package kr.PLAZMADev.KORAPN.free;

import android.app.*;
import android.content.*;
import android.os.*;

public class SplashActivity extends Activity {
	boolean paused = true;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		handler. sendEmptyMessageDelayed(0, 1500);
        // 박기한 수정 : 스플래시 지속기간이 너무 길다고 판단.
    }

	/* (non-Javadoc)
     * @see android.app.Activity#onPause()
     */
    @Override
    public void onPause() {
    	super.onPause();
    	paused = true;
    };
    
    /** The handler. */
    Handler handler = new Handler(){
    	public void handleMessage(Message message)
        {
    		if(!paused){
    		Intent intent = new Intent(SplashActivity.this, OneClickAPNHolo.class);
    		startActivity(intent);
    		}else{
    			finish();
    		}
        }
    };
}
