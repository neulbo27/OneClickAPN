package kr.PLAZMADev.KORAPN.free;

import android.app.*;
import android.os.*;
import android.widget.*;

public class OneClickAPNHolo extends Activity {
	ArrayAdapter<CharSequence> adspin;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Spinner spin = (Spinner)findViewById(R.layout.main);
        spin.setPrompt("설정하실 이통사의 국가를 선택하세요.");
        
        adspin = ArrayAdapter.createFromResource(this, R.array.country,
        		android.R.layout.simple_spinner_item);
        adspin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adspin);
    }
}