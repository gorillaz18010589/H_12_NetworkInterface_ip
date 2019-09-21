package tw.org.iii.appps.h_13_datagramsocket_ip;
//目的:網際網路
//1.開網際網路權限,想要查網路狀態再加上網卡狀態權限
//<uses-permission android:name="android.permission.INTERNET"/>
//<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
//2.你要知道你的ip是哪,寫一個取得ip1方法,但取得ip出現錯誤android.os.NetworkOnMainThreadException,必須放在背景執行緒處理
//3.在安卓任何網路的動作必須包在背景或執行緒上面,所以把網路行為包在 new Thread裡面
//4.一台電腦可能有多張網卡,一個網卡可能有多個ip

// NetworkInterface networkInterface =  en.nextElement();//拿到一組一組的網卡NetworkInterface
//Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();//取得網卡物件(回傳到Enumeration<NetworkInterface>)
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getMyip();
        getMyIPv2();
    }
    //1.取得我單一的ip位置
    private void getMyip(){
        new Thread(){ //執行緒背景處理包住網路傳遞
            @Override
            public void run() {
                try {
                    String myip = InetAddress.getLocalHost().getHostAddress();//取得我的ip
                    Log.v("brad","我的ip是:" + myip);
                }catch (Exception e){
                    Log.v("brad","取得ip出現錯誤" + e.toString());
                }
            }
        }.start();//執行緒開始
    }

    //2.一台電腦可能有多張網卡,所以用NetwordkInterface去處理,取得多個網卡裡面多個ip
        private void getMyIPv2 (){
            try {
                Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();//取得網卡物件(回傳到Enumeration<NetworkInterface>)
                while(en.hasMoreElements()){//這個網卡裡面還有元素嗎
                     NetworkInterface networkInterface =  en.nextElement();//拿到一組一組的網卡NetworkInterface
                     Enumeration<InetAddress>  ips = networkInterface.getInetAddresses();//取得網卡裡的多個ip
                     while(ips.hasMoreElements()){//當裡面有還元素時
                      InetAddress ip =  ips.nextElement();//陣列取得ip存入
                         Log.v("brad","ip:"+ ip.getHostAddress());
                     }
                }
            }catch (Exception e) {
                Log.v("brad","取得網卡ip失敗" + e.toString());
            }
        }
}
