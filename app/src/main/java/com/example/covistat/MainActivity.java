package com.example.covistat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.covistat.API.apiutilities;
import com.example.covistat.API.data;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private TextView totalconfrim,totalactive,totalrecover,totaldeath,totaltest;
    private TextView todayconfrim,todayrecover,todaydeath,date;
    private PieChart pieChart;

    private List<data> list;
    String country = "India";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        list = new ArrayList<>();
        if(getIntent().getStringExtra("country")!= null)
            country = getIntent().getStringExtra("country");

        init();
        TextView cname= findViewById(R.id.cname);
        cname.setText(country);
        cname.setOnClickListener(v->
                startActivity(new Intent(MainActivity.this,countryactivity.class))
        );

        apiutilities.getAPIinterface().getdata()
                .enqueue(new Callback<List<data>>() {
                    @Override
                    public void onResponse(Call<List<data>> call, Response<List<data>> response) {
                        list.addAll(response.body());
                       for(int i= 0 ; i<list.size();i++) {
                           if (list.get(i).getCountry().equals(country)) {
                              int confirm = Integer.parseInt(list.get(i).getCases());
                               int active = Integer.parseInt(list.get(i).getActive());
                               int recovered = Integer.parseInt(list.get(i).getRecovered());
                               int death = Integer.parseInt(list.get(i).getDeaths());
                               int tests = Integer.parseInt(list.get(i).getTests());


                              setText(list.get(i).getUpdated());

                               totalconfrim.setText(NumberFormat.getInstance().format(confirm));
                               totalactive.setText(NumberFormat.getInstance().format(active));
                               totalrecover.setText(NumberFormat.getInstance().format(recovered));
                               totaldeath.setText(NumberFormat.getInstance().format(death));
                               totaltest.setText(NumberFormat.getInstance().format(tests));
                              // totalconfrim.setText(NumberFormat.getInstance().format(Integer.parseInt(list.get(i).getCases())));
                              // totalactive.setText(NumberFormat.getInstance().format(Integer.parseInt(list.get(i).getActive())));
                              // totalrecover.setText(NumberFormat.getInstance().format(Integer.parseInt(list.get(i).getRecovered())));
                              // totaldeath.setText(NumberFormat.getInstance().format(Integer.parseInt(list.get(i).getDeaths())));
                              // totaltest.setText(NumberFormat.getInstance().format(Integer.parseInt(list.get(i).getTests())));
                               todaydeath.setText(NumberFormat.getInstance().format(Integer.parseInt(list.get(i).getTodayDeaths())));
                               todayconfrim.setText(NumberFormat.getInstance().format(Integer.parseInt(list.get(i).getTodayCases())));
                               todayrecover.setText(NumberFormat.getInstance().format(Integer.parseInt(list.get(i).getTodayRecovered())));



                               pieChart.addPieSlice(new PieModel("Confirm", confirm, getResources().getColor(R.color.red_pie)));
                               pieChart.addPieSlice(new PieModel("Active", active, getResources().getColor(R.color.blue_pie)));
                               pieChart.addPieSlice(new PieModel("Recovered", recovered, getResources().getColor(R.color.green_pie)));
                               pieChart.addPieSlice(new PieModel("Death", death, getResources().getColor(R.color.black)));

                               pieChart.startAnimation();
                           }
                       }

                    }

                    @Override
                    public void onFailure(Call<List<data>> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "Error :"+t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void setText(String updated) {
        DateFormat format = new SimpleDateFormat("MM/ dd/ yyyy");
        long milliseconds= Long.parseLong(updated);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliseconds);
        date.setText("Last updated on "+ format.format(calendar.getTime()));
    }

    private void init(){
        totalconfrim = findViewById(R.id.totalconfirm);
        totalactive = findViewById(R.id.totalactive);
        totalrecover = findViewById(R.id.totalrecover);
        totaldeath = findViewById(R.id.totaldeath);
        totaltest = findViewById(R.id.totaltests);
        todayconfrim = findViewById(R.id.todayconfirm);
        todayrecover = findViewById(R.id.todayrecover);
        todaydeath = findViewById(R.id.todaydeath);
        todaydeath = findViewById(R.id.todaydeath);
        pieChart = findViewById(R.id.piechart);
        date = findViewById(R.id.updated);

    }
}