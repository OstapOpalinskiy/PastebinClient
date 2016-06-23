package com.opalinskiy.ostap.pastebin.screens.myPastesScreen.view;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.opalinskiy.ostap.pastebin.R;
import com.opalinskiy.ostap.pastebin.interactor.models.Paste;
import com.opalinskiy.ostap.pastebin.screens.myPastesScreen.IMyPastesScreen;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class MyPastesAdapter extends RecyclerView.Adapter<MyPastesAdapter.MyViewHolder> {

    private List<Paste> list;
    private IMyPastesScreen.IItemClickHandler handler;


    public MyPastesAdapter(List<Paste> list, IMyPastesScreen.IItemClickHandler handler) {
        this.list = list;
        this.handler = handler;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.my_pastes_element, viewGroup, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, final int i) {

        String title = list.get(i).getPasteTitle();
        viewHolder.tvName.setText(title.isEmpty() ? "No name" : title);

        Date date = new Date(Long.parseLong(list.get(i).getPasteDate()) * 1000);
        SimpleDateFormat format = new SimpleDateFormat("dd/MM");
        viewHolder.tvDate.setText(format.format(date));

        viewHolder.tvCode.setText(list.get(i).getPasteUrl());

        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.onItemClick(list.get(i).getPasteUrl());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        private TextView tvName;
        private TextView tvDate;
        private TextView tvCode;


        public MyViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.cardView_MPE);
            tvName = (TextView) itemView.findViewById(R.id.tv_name_MPE);
            tvDate = (TextView) itemView.findViewById(R.id.tv_date_MPE);
            tvCode = (TextView) itemView.findViewById(R.id.tv_code_MPE);
        }
    }
}
