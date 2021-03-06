package com.example.laundry02;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;


public class user_signup3 extends Activity implements OnMapReadyCallback {

    private FragmentManager fragmentManager;
    private MapFragment mapFragment;
    private Button user_signup_back, user_signup_go;
    private EditText user_address_detail1;
    private GoogleMap mMap;

    String set_address,user_id1, user_address_detail;

    Double latitude, longitude;

    Double user_lat, user_long;
    String user_address, user_id;

    List<Address> a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_signup3);


        Intent intent = getIntent();
        user_id1 = intent.getStringExtra("user_id");

        user_signup_back = (Button) findViewById(R.id.layout3_b1);
        user_signup_back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(user_signup3.this, user_signup2.class);
                startActivity(intent);
            }
        });

        user_address_detail1 = (EditText) findViewById(R.id.layout3_et1);
        user_signup_go = (Button) findViewById(R.id.layout3_b2);
        user_signup_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //현재 입력되어 있는 값을 가져온다(get)
                try {
                    user_lat = latitude;
                    user_long = longitude;
                    user_address = a.get(0).getAddressLine(0);
                    user_id = user_id1;
                    user_address_detail = user_address_detail1.getText().toString();

                } catch (Exception e){
                    Toast.makeText(getApplicationContext(),"지도에 위치를 지정하세요.",Toast.LENGTH_SHORT).show();
                }
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success"); //php보면 response가 success면 ㄱㄱ
                            if(success){ //회원등록에 성공한 경우
                                Toast.makeText(getApplicationContext(),"회원가입 완료",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(user_signup3.this, user_signup4.class);
                                startActivity(intent);
                            }
                            //실패한 경우
                            else{
                                Toast.makeText(getApplicationContext(),"회원가입 실패",Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };

                //서버로 Volley를 이용해서 요청을 함
                user_register2_db registerRequest = new user_register2_db(user_lat, user_long,user_address, user_id, user_address_detail, responseListener);
                RequestQueue queue = Volley.newRequestQueue(user_signup3.this);
                queue.add(registerRequest);
            }
            /*public void onClick(View v) {
                Intent intent = new Intent(owner_signup3.this, owner_signup4.class);
                startActivity(intent);
            }*/
                // set_address 값을 db에 저장 후 불러오기
                /*Toast.makeText(getApplicationContext(), ""+set_address, Toast.LENGTH_SHORT).show();
                Intent intent2 = new Intent(user_signup3.this,user_main.class);
                intent2.putExtra("set_address",set_address);
                startActivity(intent2);*/
        });

        fragmentManager = getFragmentManager();
        mapFragment = (MapFragment)fragmentManager.findFragmentById(R.id.googlemap);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        /*마커*/
        LatLng location = new LatLng(36.620784, 127.287240);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.title("홍익대학교 세종캠퍼스");
        markerOptions.snippet("교육기관");
        markerOptions.position(location);
        googleMap.addMarker(markerOptions);

        /*googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location,16));*/
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location,16));


        final Geocoder g = new Geocoder(this);
        mMap = googleMap;
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener(){
            @Override
            public void onMapClick(LatLng point) {
                MarkerOptions mOptions = new MarkerOptions();
                // 마커 타이틀
                mOptions.title("이곳으로 배달해주세요!");
                latitude = point.latitude; // 위도
                longitude = point.longitude; // 경도
                // 마커의 스니펫(간단한 텍스트) 설정
                mOptions.snippet(latitude.toString() + ", " + longitude.toString());
                // LatLng: 위도 경도 쌍을 나타냄
                mOptions.position(new LatLng(latitude, longitude));

                googleMap.clear();

                // 마커(핀) 추가
                googleMap.addMarker(mOptions);

                try {
                    a = g.getFromLocation(latitude,longitude,1);
                    Toast.makeText(getApplicationContext(), ""+a.get(0).getAddressLine(0), Toast.LENGTH_SHORT).show();
                    set_address = a.get(0).getAddressLine(0);
                } catch (IOException e) {
                    e.printStackTrace();
                }


                /*Double temp1 = latitude;
                Double temp2 = longitude;
                if (latitude == temp1 || longitude == temp2){
                    googleMap.remove
                }*/
            }
        });
    }
}
