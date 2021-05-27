package com.example.covistat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.covistat.API.apiutilities;
import com.example.covistat.API.data;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class countryactivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<data> list;
    private ProgressDialog dialog;
    private EditText searchbar;
    private countryadapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countryactivity);

        recyclerView= findViewById(R.id.countries);
        list= new ArrayList<>();
        searchbar = findViewById(R.id.searchbar);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        adapter= new countryadapter(this,list);
        recyclerView.setAdapter(adapter);



        dialog = new ProgressDialog(this);
        dialog.setMessage("Hang on ..");
        dialog.setCancelable(false);
        dialog.show();

        apiutilities.getAPIinterface().getdata().enqueue(new Callback<List<data>>() {
            @Override
            public void onResponse(Call<List<data>> call, Response<List<data>> response) {
                list.addAll(response.body());
                adapter.notifyDataSetChanged();
                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<data>> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(countryactivity.this, "Error :"+t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        searchbar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                fliter(s.toString());

            }
        });
    }

    public void fliter(String text) {
        List<data> filterList = new ArrayList<>();
        for(data items:list){
            if(items.getCountry().toLowerCase().contains(text.toLowerCase())){
                filterList.add(items);
            }
        }
        adapter.fliterList(filterList);

    }
}