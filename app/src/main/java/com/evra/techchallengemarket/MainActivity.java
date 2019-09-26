package com.evra.techchallengemarket;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    Context context = this;
    String url = "http://kariyertechchallenge.mockable.io/";
    CustomAdapter customAdapter;
    ArrayList<Market> marketList = new ArrayList<>();
    UserSessionManagerPrefs sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sessionManager = new UserSessionManagerPrefs(getApplicationContext());

        recyclerView = findViewById(R.id.recycler_view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(0);
        recyclerView.setLayoutManager(layoutManager);

        getDatas();
        recyclerView.setHasFixedSize(true);
        customAdapter = new CustomAdapter(marketList, context);
        recyclerView.setAdapter(customAdapter);
    }
    private void getDatas() {
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Yükleniyor...");
        progressDialog.show();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        Market market = new Market();
                        market.setMarketName(jsonObject.getString("marketName"));
                        market.setDate(jsonObject.getString("date"));
                        market.setMonth(jsonObject.getString("month"));

                        market.setState(jsonObject.getString("productState"));
                        market.setOrderName(jsonObject.getString("orderName"));
                        market.setPrice(jsonObject.getString("productPrice"));

                        JSONObject productDetail = jsonObject.getJSONObject("productDetail");
                        market.setOrderDetail(productDetail.getString("orderDetail"));
                        market.setSummaryPrice(productDetail.getString("summaryPrice"));

                        marketList.add(market);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                customAdapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("VolleyError: ", error.toString());
                progressDialog.dismiss();
            }
        });
        MySingleton.getInstance(context).addRequestQueue(jsonArrayRequest);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.exit:
                logout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void logout() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);//Bilgi alert ediliyor
        alertDialog.setTitle("Çıkış")
                .setMessage("Çıkış yapmak istediğinizden emin misiniz?")
                .setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        sessionManager.setForgetSession();
                        Intent i = new Intent(context, LoginActivity.class);
                        startActivity(i);
                        finish();
                    }
                })
                .setNegativeButton("Vazgeç", null)
                .show();
    }
}
