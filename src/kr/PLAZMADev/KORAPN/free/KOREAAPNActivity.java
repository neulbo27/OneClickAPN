package kr.PLAZMADev.KORAPN.free;

import java.util.*;

import android.app.*;
import android.content.*;
import android.content.pm.*;
import android.net.*;
import android.os.*;
import android.view.*;
import android.widget.*;

import com.google.ads.*;

public class KOREAAPNActivity extends Activity
{
    static final int SKT = 0;
    static final int KT = 1;
    static final int LGT = 2;
    static final int SOFTBANK = 3;
    static final int NTTDOCOMO = 4;
    static final int SKTLTE = 5;
    static final int LGTLTE = 6;

    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        
        Display display =
        		((WindowManager)getSystemService("window")).getDefaultDisplay();//디스플레이 객체 요청
        
        if(display.getHeight() > display.getWidth())//디스플레이의 높이가 넓이보다 클경우
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//화면 방향을 세로로 설정
        else
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//화면 방향을 가로로 설정
        
        setContentView(R.layout.main);//레이아웃 설정
        
        AdView adview = new AdView(this, AdSize.BANNER, "a14e676e2c53591");//광고 위젯 생성
        LinearLayout linearlayout = (LinearLayout)findViewById(R.id.adlayout);//리니어레이아웃 요청
        linearlayout.addView(adview);//리니어에 광고 위젯 추가
        adview.loadAd(new AdRequest());//광고 불러오기
        
        ArrayList<listitem> arraylist = new ArrayList<listitem>();//ListView에 들어갈 객체 배열 생성
        
        ListView listview = (ListView)findViewById(R.id.list);//리스트뷰 객체 가져오기
        
        arraylist.add(new listitem(R.drawable.t, "SKT(KR)"));
        arraylist.add(new listitem(R.drawable.olleh, "Olleh(KR)"));
        arraylist.add(new listitem(R.drawable.sklte, "SKT LTE(KR)"));
        arraylist.add(new listitem(R.drawable.lgtlte, "LGT LTE(KR)"));
        arraylist.add(new listitem(R.drawable.softbank, "Softbank(JP)"));
        arraylist.add(new listitem(R.drawable.docomo, "NTT Docomo(JP)"));
        arraylist.add(new listitem(R.drawable.korapnicon, "App Info"));
        arraylist.add(new listitem(R.drawable.logo, "Developer Info"));
        arraylist.add(new listitem(R.drawable.donation, "Donation"));//배열에 항목 추가
        
        CustomListAdapter customlistadapter =
        		new CustomListAdapter(this, R.layout.listitem, arraylist);//커스텀 리스트 어댑터 생성
        
        listview.setAdapter(customlistadapter);//커스텀 리스트 어댑터 설정
        
        listview.setOnItemClickListener(listener);//아이템클릭 리스너 설정
    }
    
    AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener()
    {
        public void onItemClick(AdapterView<?> adapterview, View view, int index, long l)
        {
            Intent intent = new Intent(KOREAAPNActivity.this, APNSettingActivity.class);
            Intent intent2 = new Intent(KOREAAPNActivity.this, InfoActivity.class);
            switch (index) {
			case 0:
				intent.putExtra("Target", SKT);//SKT APN설정
	            startActivity(intent);
				break;
			case 1:
				intent.putExtra("Target", KT);//KT APN 설정
	            startActivity(intent);
	            break;
			case 2:
				intent.putExtra("Target", SKTLTE);
				startActivity(intent);
			break;
			case 3:
				intent.putExtra("Target", LGTLTE);
				startActivity(intent);
				break;
			case 4:
				intent.putExtra("Target", SOFTBANK);
				startActivity(intent);
				break;
			case 5:
				intent.putExtra("Target", NTTDOCOMO);
				startActivity(intent);
				break;
			case 6:
				startActivity(intent2);
				break;
			case 7:
				KOREAAPNActivity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("http://www.facebook.com/PLAZMADev")));
				break;
			case 8:
				KOREAAPNActivity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("http://plazmadev.tistory.com/m/12")));
				break;
			default:
				break;
			}
        }
    };

    /*
     * 커스텀 리스트 어댑터에 들어가는 객체
     * 생성자 인수 : 아이콘 ID, 제목
     */
    
    public class listitem
    {

        int icon;
        String name;

        listitem(int id, String text)
        {
            icon = id;
            name = text;
        }
    }
    
    /*
     * 직접 만든 커스텀 리스트 어댑터
     * 생성자 인수 : 액티비티 객체, 레이아웃 ID, 원본 배열
     */
    
    public class CustomListAdapter extends BaseAdapter
    {
        Context context;
        LayoutInflater inflater;
        int layoutid;
        ArrayList<listitem> sourcearray;

    	public CustomListAdapter(Context context, int id, ArrayList<listitem> arraylist)
        {
            this.context = KOREAAPNActivity.this;
            LayoutInflater layoutinflater = (LayoutInflater)context.getSystemService("layout_inflater");
            inflater = layoutinflater;
            layoutid = id;
            sourcearray = arraylist;
        }

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}
		

        public int getCount()
        {
            return sourcearray.size();
        }

        public View getView(int i, View view, ViewGroup viewgroup)
        {
            if(view == null)
            {
                LayoutInflater layoutinflater = inflater;
                int j = layoutid;
                view = layoutinflater.inflate(j, viewgroup, false);
            }
            ImageView imageview = (ImageView)view.findViewById(R.id.img);
            int k = ((listitem)sourcearray.get(i)).icon;
            imageview.setImageResource(k);
            TextView textview = (TextView)view.findViewById(R.id.text);
            String s = ((listitem)sourcearray.get(i)).name;
            textview.setText(s);
            return view;
        }
    }
}
