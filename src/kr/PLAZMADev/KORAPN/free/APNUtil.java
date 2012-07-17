package kr.PLAZMADev.KORAPN.free;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.net.Uri;
import android.util.Log;

public class APNUtil {
	static final String Tag = "KORAPN"; //�뵒踰꾧렇 濡쒓퉭�슜 �깭洹� - �젙諛쒖떆 �젣嫄�
	public static final Uri APN_TABLE_URI = Uri.parse("content://telephony/carriers"); //apn db 二쇱냼
	 public static final Uri PREFERRED_APN_URI = Uri.parse("content://telephony/carriers/preferapn"); //�꽕�젙�맂 apn 二쇱냼
	Context context;
	
	/*
	 * �깮�꽦�옄 �씤�닔 : �븸�떚鍮꾪떚 媛앹껜(this)
	 */
	APNUtil(Context con){
		this.context = con;
	}
	
	/*
     * �떆�뒪�뀥 APN DB�뿉 �깉濡쒖슫 APN�쓣 異붽���븯怨� 異붽���맂 APN�쓽 �닚�꽌 ID瑜� 諛섑솚
     * �씤�닔 : APN�씠由�,APN二쇱냼,MMS二쇱냼,MMS�봽濡앹떆,MCC,MNC,MMS�룷�듃,����엯
     * 紐⑤몢 String ����엯�쑝濡� �엯�젰
     */
	
	public int InsertAPN(ContentValues values) 
    {
        int id = -1;
        ContentResolver resolver = context.getContentResolver();
        
        Cursor c = null;
        try{
            Uri newRow = resolver.insert(APN_TABLE_URI, values);
            if(newRow != null){
                c = resolver.query(newRow, null, null, null, null);                
                // APN ID瑜� 媛��졇�샂
                int idindex = c.getColumnIndex("_id");
                c.moveToFirst();
                id = c.getShort(idindex);
                Log.d(Tag, "New ID: " + id + ": Inserting new APN succeeded!");
            					}
        }catch(SQLException e){
            Log.d(Tag, e.getMessage());
        			}
        
        if(c !=null ) 
            c.close();
        return id;
    }
	
	/*
	 * ID�쓽 APN�쓣 湲곕낯�쑝濡� �꽕�젙
	 */
	
    public boolean SetDefaultAPN(int id)
    {
        boolean res = false;
        ContentResolver resolver = context.getContentResolver();
        ContentValues values = new ContentValues();
        
        //See /etc/apns-conf.xml. The TelephonyProvider uses this file to provide 
        //content://telephony/carriers/preferapn URI mapping
        
        values.put("apn_id", id); 
        try
        {
            resolver.update(PREFERRED_APN_URI, values, null, null);
            Cursor c = resolver.query(
                    PREFERRED_APN_URI, 
                    new String[]{"name","apn"}, 
                    "_id="+id, 
                    null, 
                    null);
            if(c != null)
            {
                res = true;
                c.close();
            }
        }
        catch (SQLException e)
        {
            Log.d(Tag, e.getMessage());
        }
         return res;
    }
    
    private String getAllColumnNames(String[] columnNames)
    {
        String s = "Column Names:\n";
        for(String t:columnNames)
        {
            s += t + ":\t";
        }
        return s+"\n";
    }
    
    private String printAllData(Cursor c)
    {
        if(c == null) return null;
        String s = "";
        
        if(c.moveToFirst())
        {
            String[] columnNames = c.getColumnNames();
            s += getAllColumnNames(columnNames);
            do{
                String row = "";
                for(String columnIndex:columnNames)
                {
                    int i = c.getColumnIndex(columnIndex);
                    row += c.getString(i)+":\t";
                }
                row += "\n";
                s += row;
            }while(c.moveToNext());
        }
        return s;
    } 
}

