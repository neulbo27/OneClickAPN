package kr.PLAZMADev.KORAPN.free;

import android.app.*;
import android.content.*;
import android.content.DialogInterface.OnKeyListener;
import android.os.*;
import android.util.Log;
import android.view.KeyEvent;

public class APNSettingActivity extends Activity{
	private int target;
	APNUtil util; //APN 설정 유틸리티 클래스
	AlertDialog dialog1, dialog2;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setapn);
		
		Log.d("APNSettingActivity","onCreate()");
		
		target = getIntent().getIntExtra("Target", 0); //인텐츠에서 대상 정보 꺼내기
		util = new APNUtil(APNSettingActivity.this); //유틸리티 설정
	}
	
	public void onStart(){
		super.onStart();
		
		dialog1 = new AlertDialog.Builder(this)
		.setTitle(R.string.A)
		.setMessage(R.string.warning)
		.setIcon(android.R.drawable.stat_sys_warning)
		.setPositiveButton("Yes", onclick)
		.setNegativeButton("No", onclick)
		.setOnKeyListener(listener).create();
		
		dialog1.show();
	}
	
	public void InsertAPN(){
		int id;
		ContentValues values = new ContentValues();
		switch (target) {
		case KOREAAPNActivity.SKT:
			values.put("name", "SKT3G"); //APN 이름
			values.put("apn", "web.sktelecom.com"); //APN 접속경로
			values.put("mmsc", "http://omms.nate.com:9082/oma_mms"); //mmsc 서버
			values.put("mmsproxy", "smart.nate.com"); //mms 프록시 서버
			values.put("mcc", "450");
			values.put("mnc", "05");
			values.put("mmsport", "9093");
			values.put("numeric", "45005");
			values.put("type", "default,supl");
			id = util.InsertAPN(values);
			util.SetDefaultAPN(id); //추가한 APN을 기본으로 설정
			break;
		case KOREAAPNActivity.KT:
			values.put("name", "KT-HSDPA");
			values.put("apn", "alwayson.ktfwing.com");
			values.put("mmsc","http://mmsc.ktfwing.com:9082");
			values.put("mcc","450");
			values.put("mnc", "08");
			values.put("numeric", "45008");
			values.put("type", "default,supl");
			id = util.InsertAPN(values);
			util.SetDefaultAPN(id); //추가한 APN을 기본으로 설정
			break;
		case KOREAAPNActivity.SOFTBANK:
			values.put("name", "Open SoftBank");
			values.put("apn", "open.softbank.ne.jp");
			values.put("port", "8080");
			values.put("user", "opensoftbank");
			values.put("password", "ebMNuX1FlHg9d3DA");
			values.put("mcc", "440");
			values.put("mnc", "20");
			values.put("type", "default");
			id = util.InsertAPN(values);
			util.SetDefaultAPN(id);//추가한 APN을 기본으로 설정
			values = new ContentValues();
			values.put("name", "Open SoftBank");
			values.put("apn", "mailwebservice.softbank.ne.jp");
			values.put("user", "softbank");
			values.put("password", "qceffknarlurqgbl");
			values.put("mmsc", "http://mms");
			values.put("mmsproxy", "sbmmsproxy.softbank.ne.jp");
			values.put("mmsport", "8080");
			values.put("mcc", "440");
			values.put("mnc", "20");
			values.put("type", "mms");
			id = util.InsertAPN(values);
			break;
		case KOREAAPNActivity.NTTDOCOMO:
			values.put("name", "NTT Docomo(Bizho)");
			values.put("mcc", "440");
			values.put("mnc", "10");
			values.put("apn", "mpr2.bizho.net");
			values.put("type", "default,supl,mms");
			id = util.InsertAPN(values);
			util.SetDefaultAPN(id);
			values = new ContentValues();
			values.put("name", "NTT Docomo(mopera)");
			values.put("mcc", "440");
			values.put("mnc", "10");
			values.put("apn", "mopera.net");
			values.put("type", "default,supl,mms");
			id = util.InsertAPN(values);
			break;
		case KOREAAPNActivity.SKTLTE:
			values.put("name","SKT LTE APN");
			values.put("apn","lte.sktelecom.com");
			values.put("mmsc","vmms.nate.com");
			values.put("mmsport","8082");
			values.put("mnc","450");
			values.put("mcc","05");
			values.put("server","*");
			values.put("type","*");
			id = util.InsertAPN(values);
			util.SetDefaultAPN(id);
			break;
		case KOREAAPNActivity.LGTLTE:
		values.put("name","LGT LTE APN");
		values.put("apn","internet.lguplus.co.kr");
		values.put("mmsc","http://mmsc.ez-i.co.kr");
		values.put("mmsport","8084");
		values.put("mnc","450");
		values.put("mcc","06");
		values.put("server","*");
		values.put("type","*");
		id = util.InsertAPN(values);
		util.SetDefaultAPN(id);
			break;
		default:
			break;
		}

		dialog2 = new AlertDialog.Builder(this)
		.setTitle(R.string.B)
		.setMessage(R.string.C)
		.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				APNSettingActivity.this.finish();//확인 버튼 클릭시 액티비티 종료
			}
		}).setOnKeyListener(listener).create();
		
		dialog2.show();
	}
	
	OnKeyListener listener = new OnKeyListener(){
		@Override
		public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
			// TODO Auto-generated method stub
			if(keyCode == KeyEvent.KEYCODE_BACK)
			{
				finish();
			}
			return false;
		}
	};
	
	/*
	 * 경고 팝업에 들어가는 온클릭리스너
	 */
	
	DialogInterface.OnClickListener onclick =  new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int which) {
			switch (which) {
			case -1: //팝업에서 예 버튼 클릭됨
				InsertAPN(); //APN 등록 메서드 실행
				break;
			case -2: //팝업에서 아니오 버튼 클릭됨
				APNSettingActivity.this.finish(); //액티비티 종료
				break;
			default:
				break;
			}
		}
	};
}
