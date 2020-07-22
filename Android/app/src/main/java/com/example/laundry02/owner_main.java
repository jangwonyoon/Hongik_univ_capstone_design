package com.example.laundry02;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class owner_main extends AppCompatActivity{

    public String var_name, var_address, var_store_name;
    public Double var_lat, var_long;

    private long backBtnTime = 0;
    Button b1,b2,b3,b4,b5,b6,b7,b8;
    TextView get_text;

    private ListView listView;


    String owner_name1, owner_address1, store_name1;
    Double owner_lat1, owner_long1;

    private static String TAG = "phptest";

    private EditText mEditTextName;
    private EditText mEditTextCountry;
    private TextView mTextViewResult;
    private ArrayList<owner_main1_list> mArrayList;
    private owner_main_Adpter mAdapter;
    private RecyclerView mRecyclerView;
    private EditText mEditTextSearchKeyword;
    private String mJsonString;

    @Override
    public void onBackPressed() {
        long curTime = System.currentTimeMillis();
        long gapTime = curTime - backBtnTime;

        if(0 <= gapTime && 2000 >= gapTime) {
            moveTaskToBack(true);
            finish();
            android.os.Process.killProcess(android.os.Process.myPid());
        }
        else {
            backBtnTime = curTime;
            Toast.makeText(this, "한번 더 누르면 종료됩니다.",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_main);

        Intent intent = getIntent();
        owner_name1 = intent.getStringExtra("owner_name");
        owner_address1 = intent.getStringExtra("owner_address");
        owner_lat1 = intent.getDoubleExtra("owner_lat", 0.0);
        owner_long1 = intent.getDoubleExtra("owner_long", 0.0);
        store_name1 = intent.getStringExtra("store_name");

        var_name = owner_name1;
        var_address = owner_address1;
        var_lat = owner_lat1;
        var_long = owner_long1;
        var_store_name = store_name1;

        mTextViewResult = (TextView) findViewById(R.id.textView_main_result);
        mRecyclerView = (RecyclerView) findViewById(R.id.listView_main_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mTextViewResult.setMovementMethod(new ScrollingMovementMethod());

        mArrayList = new ArrayList<>();

        mAdapter = new owner_main_Adpter(this, mArrayList, var_name, var_address, var_lat, var_long);
        mRecyclerView.setAdapter(mAdapter);

        mArrayList.clear();
        mAdapter.notifyDataSetChanged();

        owner_main.GetData task = new owner_main.GetData();
        task.execute("http://edit0.dothome.co.kr/owner_main1_db.php", store_name1);


        //액션바 설정하기//
        //액션바 타이틀 변경하기
        getSupportActionBar().setTitle(owner_name1 + "사장님 안녕하세요.");
        //액션바 배경색 변경
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFF339999));
        //홈버튼 표시
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    private class GetData extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(owner_main.this,
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            /*mTextViewResult.setText(result);*/
            Log.d(TAG, "response - " + result);

            if (result == null){

                mTextViewResult.setText(errorString);
            }
            else {

                mJsonString = result;
                showResult();
            }
        }


        @Override
        protected String doInBackground(String... params) {

            String serverURL = params[0];
            String postParameters = "store_name=" + params[1];


            try {

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();


                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();


                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "response code - " + responseStatusCode);

                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }

                bufferedReader.close();

                return sb.toString().trim();


            } catch (Exception e) {

                Log.d(TAG, "GetData : Error ", e);
                errorString = e.toString();

                return null;
            }

        }
    }


    private void showResult(){

        String TAG_JSON="result";
        String TAG_u_address = "u_address";
        String TAG_u_id = "u_id";
        String TAG_u_pw ="u_pw";


        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for(int i=0;i<jsonArray.length();i++){

                JSONObject item = jsonArray.getJSONObject(i);

                String u_address1 = item.getString(TAG_u_address);
                String u_id1 = item.getString(TAG_u_id);
                String u_pw1 = item.getString(TAG_u_pw);

                owner_main1_list personalData = new owner_main1_list();

                personalData.setMember_u_address(u_address1);
                personalData.setMember_u_id(u_id1);
                personalData.setMember_u_pw(u_pw1);

                mArrayList.add(personalData);
                mAdapter.notifyDataSetChanged();
            }



        } catch (JSONException e) {

            Log.d(TAG, "showResult : ", e);
        }

    }

        /*listView = (ListView)findViewById(R.id.listview);
        myHuman1 = new Human_owner("주소: 임시주소입니다.", "ID: abc123", "번호: 01024357578");
        myHuman2 = new Human_owner("주소: 서울특별시 ", "ID: q1rw3", "번호: 01023445677");
        myHuman3 = new Human_owner("주소: 세종특별자치시", "ID: ihave", "번호: 01035455721");
        h_info_list = new ArrayList<Human_owner>();
        h_info_list.add(myHuman1);
        h_info_list.add(myHuman2);
        h_info_list.add(myHuman3);
        h_info_list.add(myHuman1);
        h_info_list.add(myHuman2);
        h_info_list.add(myHuman3);

        //getApplicationContext() or this
        myadapter_owner = new HumanAdpter_owner(this,R.layout.human_info_owner, h_info_list);
        listView.setAdapter(myadapter_owner);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view,int position, long id) {
                Intent intent = new Intent(owner_main.this, ImageActivity_owner.class); // 다음넘어갈 화면
                intent.putExtra("name",h_info_list.get(position).getName());
                intent.putExtra("gender",h_info_list.get(position).getGender());
                intent.putExtra("age",h_info_list.get(position).getAge());
                startActivity(intent);

            }
        });

        *//*String[] list = {"하나세탁소(test)","영풍세탁소(test)","홍익세탁소(test)"};

        ArrayAdapter<String> adapter;

        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, list);

        ListView listview = (ListView)findViewById(R.id.listview);

        listview.setAdapter(adapter);*//*


        //이값은 디비에서 불러오는걸로
        *//*get_text = findViewById(R.id.tv1);
        Intent intent = getIntent();

        Bundle bundle = intent.getExtras();
        String set_address = bundle.getString("set_address");

        get_text.setText(""+set_address+"");*//*



    }*/



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        switch(item.getItemId()){
            case R.id.b1:
                Intent intent = new Intent(this, owner_main.class);
                intent.putExtra("owner_name",owner_name1);
                intent.putExtra("owner_address",owner_address1);
                intent.putExtra("owner_lat",owner_lat1);
                intent.putExtra("owner_long",owner_long1);
                intent.putExtra("store_name",store_name1);
                startActivity(intent);
                break;
            case R.id.b2:
                Intent intent1 = new Intent(this, owner_order_y_n.class);
                intent1.putExtra("owner_name",owner_name1);
                intent1.putExtra("owner_address",owner_address1);
                intent1.putExtra("owner_lat",owner_lat1);
                intent1.putExtra("owner_long",owner_long1);
                intent1.putExtra("store_name",store_name1);
                startActivity(intent1);
                break;
            case R.id.b3:
                Intent intent2 = new Intent(this, owner_item_add_del.class);
                intent2.putExtra("owner_name",owner_name1);
                intent2.putExtra("owner_address",owner_address1);
                intent2.putExtra("owner_lat",owner_lat1);
                intent2.putExtra("owner_long",owner_long1);
                intent2.putExtra("store_name",store_name1);
                startActivity(intent2);
                break;
            case R.id.b4:
                Intent intent3 = new Intent(this, owner_gongji_management.class);
                intent3.putExtra("owner_name",owner_name1);
                intent3.putExtra("owner_address",owner_address1);
                intent3.putExtra("owner_lat",owner_lat1);
                intent3.putExtra("owner_long",owner_long1);
                intent3.putExtra("store_name",store_name1);
                startActivity(intent3);
                break;
            case R.id.b5:
                Intent intent4 = new Intent(this, owner_info.class);
                intent4.putExtra("owner_name",owner_name1);
                intent4.putExtra("owner_address",owner_address1);
                intent4.putExtra("owner_lat",owner_lat1);
                intent4.putExtra("owner_long",owner_long1);
                intent4.putExtra("store_name",store_name1);
                startActivity(intent4);
                break;
            case R.id.b6:
                Intent intent5 = new Intent(this, owner_review_management.class);
                intent5.putExtra("owner_name",owner_name1);
                intent5.putExtra("owner_address",owner_address1);
                intent5.putExtra("owner_lat",owner_lat1);
                intent5.putExtra("owner_long",owner_long1);
                intent5.putExtra("store_name",store_name1);
                startActivity(intent5);
                break;
            case R.id.b7:
                Intent intent6 = new Intent(this, owner_logout.class);
                startActivity(intent6);
                break;


        }

        return super.onOptionsItemSelected(item);
    }
    public void onClick(View v){

    }
}
