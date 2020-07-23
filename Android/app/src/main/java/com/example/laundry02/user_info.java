package com.example.laundry02;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class user_info extends AppCompatActivity{

    private Button user_ch_info;
    private EditText user_password1;
    private long backBtnTime = 0;

    TextView get_text;

    String user_name1, user_address1, user_id1, user_address_detail1;
    Double user_lat1, user_long1;

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
        setContentView(R.layout.activity_user_info);

        user_password1 = (EditText) findViewById(R.id.layout5_et2);

        Intent intent = getIntent();
        user_name1 = intent.getStringExtra("user_name");
        user_address1 = intent.getStringExtra("user_address");
        user_lat1 = intent.getDoubleExtra("user_lat",0.0);
        user_long1 = intent.getDoubleExtra("user_long",0.0);
        user_id1 = intent.getStringExtra("user_id");
        user_address_detail1 = intent.getStringExtra("user_address_detail");


        //액션바 설정하기//
        //액션바 타이틀 변경하기
        getSupportActionBar().setTitle(user_name1+"님 안녕하세요. [개인정보]");
        //액션바 배경색 변경
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFF339999));
        //홈버튼 표시
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        /*menubar= (Button) findViewById(R.id.btn_open);
        menubar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(user_main1.this , menubar);



                MenuInflater inf = popup.getMenuInflater();
                inf.inflate(R.menu.menu1, popup.getMenu());
                popup.show();
            }
        });*/


        user_ch_info = (Button) findViewById(R.id.layout5_b1);
        user_ch_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_id = user_id1;
                String user_password = user_password1.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success"); //php보면 response가 success면 ㄱㄱ
                            if(success){ //회원등록에 성공한 경우
                                String user_name  = jsonObject.getString("user_name");
                                String user_address = jsonObject.getString("user_address");
                                Double user_lat = jsonObject.getDouble("user_lat");
                                Double user_long = jsonObject.getDouble("user_long");
                                String user_id = jsonObject.getString("user_id");
                                int user_number = jsonObject.getInt("user_number");
                                String user_password = jsonObject.getString("user_password");
                                String user_email = jsonObject.getString("user_email");

                                Toast.makeText(getApplicationContext(),"접속 성공",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(user_info.this, user_info1.class);
                                intent.putExtra("user_name",user_name);
                                intent.putExtra("user_address",user_address);
                                intent.putExtra("user_lat",user_lat);
                                intent.putExtra("user_long",user_long);
                                intent.putExtra("user_id",user_id);
                                intent.putExtra("user_number",user_number);
                                intent.putExtra("user_password",user_password);
                                intent.putExtra("user_email",user_email);
                                intent.putExtra("user_address_detail",user_address_detail1);
                                startActivity(intent);
                            }
                            //실패한 경우
                            else{
                                Toast.makeText(getApplicationContext(),"정확한 아이디/패스워드를 입력해주세요.",Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };
                user_login_db loginRequest = new user_login_db(user_id, user_password, responseListener);
                RequestQueue queue = Volley.newRequestQueue(user_info.this);
                queue.add(loginRequest);
            }
        });


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu1, menu);
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
                Intent intent = new Intent(this, user_main1.class);
                intent.putExtra("user_name",user_name1);
                intent.putExtra("user_address",user_address1);
                intent.putExtra("user_lat",user_lat1);
                intent.putExtra("user_long",user_long1);
                intent.putExtra("user_id",user_id1);
                intent.putExtra("user_address_detail",user_address_detail1);
                startActivity(intent);
                break;
            case R.id.b2:
                Intent intent1 = new Intent(this, user_gongji.class);
                intent1.putExtra("user_name",user_name1);
                intent1.putExtra("user_address",user_address1);
                intent1.putExtra("user_lat",user_lat1);
                intent1.putExtra("user_long",user_long1);
                intent1.putExtra("user_id",user_id1);
                intent1.putExtra("user_address_detail",user_address_detail1);
                startActivity(intent1);
                break;
            case R.id.b3:
                Intent intent2 = new Intent(this, user_changelocation.class);
                intent2.putExtra("user_name",user_name1);
                intent2.putExtra("user_address",user_address1);
                intent2.putExtra("user_lat",user_lat1);
                intent2.putExtra("user_long",user_long1);
                intent2.putExtra("user_id",user_id1);
                intent2.putExtra("user_address_detail",user_address_detail1);
                startActivity(intent2);
                break;
            case R.id.b4:
                Intent intent3 = new Intent(this, user_info.class);
                intent3.putExtra("user_name",user_name1);
                intent3.putExtra("user_address",user_address1);
                intent3.putExtra("user_lat",user_lat1);
                intent3.putExtra("user_long",user_long1);
                intent3.putExtra("user_id",user_id1);
                intent3.putExtra("user_address_detail",user_address_detail1);
                startActivity(intent3);
                break;
            case R.id.b5:
                Intent intent4 = new Intent(this, user_review.class);
                intent4.putExtra("user_name",user_name1);
                intent4.putExtra("user_address",user_address1);
                intent4.putExtra("user_lat",user_lat1);
                intent4.putExtra("user_long",user_long1);
                intent4.putExtra("user_id",user_id1);
                intent4.putExtra("user_address_detail",user_address_detail1);
                startActivity(intent4);
                break;
            case R.id.b6:
                Intent intent5 = new Intent(this, user_now_order.class);
                intent5.putExtra("user_name",user_name1);
                intent5.putExtra("user_address",user_address1);
                intent5.putExtra("user_lat",user_lat1);
                intent5.putExtra("user_long",user_long1);
                intent5.putExtra("user_id",user_id1);
                intent5.putExtra("user_address_detail",user_address_detail1);
                startActivity(intent5);
                break;
            case R.id.b7:
                Intent intent6 = new Intent(this, user_order_record.class);
                intent6.putExtra("user_name",user_name1);
                intent6.putExtra("user_address",user_address1);
                intent6.putExtra("user_lat",user_lat1);
                intent6.putExtra("user_long",user_long1);
                intent6.putExtra("user_id",user_id1);
                intent6.putExtra("user_address_detail",user_address_detail1);
                startActivity(intent6);
                break;
            case R.id.b8:
                Intent intent7 = new Intent(this, user_logout.class);
                startActivity(intent7);
                break;

        }

        return super.onOptionsItemSelected(item);
    }

}

