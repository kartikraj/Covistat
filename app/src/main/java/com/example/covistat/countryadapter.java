package com.example.covistat;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.covistat.API.data;

import java.text.NumberFormat;
import java.util.List;
import java.util.Map;

public class countryadapter extends RecyclerView.Adapter<countryadapter.CountryViewHolder> {

    private Context context;
    private List<data> list;

    public countryadapter(Context context, List<data> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public CountryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.country_item,parent,false);
        return new CountryViewHolder(view);
    }

    public void fliterList(List<data> filterList){
        list = filterList;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull CountryViewHolder holder, int position) {

        data d=  list.get(position);
        holder.cases.setText(NumberFormat.getInstance().format(Integer.parseInt(d.getCases())));
        holder.name.setText(d.getCountry());
        holder.no.setText(String.valueOf(position+1));

        Map<String,String> img = d.getCountryInfo();
        Glide.with(context).load(img.get("flag")).into(holder.image);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, MainActivity.class);
            intent.putExtra("country", d.getCountry());
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    public class CountryViewHolder extends RecyclerView.ViewHolder {

        private TextView no,name,cases;
        private ImageView image;

        public CountryViewHolder(@NonNull View itemView) {
            super(itemView);

            no= itemView.findViewById(R.id.no);
            name= itemView.findViewById(R.id.name);
            cases= itemView.findViewById(R.id.cases);
            image= itemView.findViewById(R.id.image);
        }
    }
}
