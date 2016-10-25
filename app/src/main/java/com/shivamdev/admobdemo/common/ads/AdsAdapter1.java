package com.shivamdev.admobdemo.common.ads;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.shivamdev.admobdemo.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by shivam on 25/10/16.
 */

public class AdsAdapter1 extends RecyclerView.Adapter<AdsAdapter1.AdsHolder1> {

    private List<String> titles;
    private List<String> descs;

    private Context context;

    AdsAdapter1(Context context, List<String> title, List<String> desc) {
        this.context = context;
        this.titles = title;
        this.descs = desc;
    }

    @Override
    public AdsHolder1 onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.ad_item_1, parent, false);
        return new AdsHolder1(view);
    }

    @Override
    public void onBindViewHolder(AdsHolder1 holder, int position) {
        holder.tvTitle.setText(titles.get(position));
        holder.tvDesc.setText(descs.get(position));
    }

    @Override
    public int getItemCount() {
        return titles.size();
    }

    class AdsHolder1 extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_desc)
        TextView tvDesc;

        public AdsHolder1(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Clicked on item : " + getLayoutPosition(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
