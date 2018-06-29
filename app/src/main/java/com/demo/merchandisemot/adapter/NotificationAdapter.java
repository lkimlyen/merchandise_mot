package com.demo.merchandisemot.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.demo.architect.data.model.offline.NotificationModel;
import com.demo.merchandisemot.R;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

public class NotificationAdapter extends RealmRecyclerViewAdapter<NotificationModel, NotificationAdapter.MyViewHolder> {
    public NotificationAdapter(OrderedRealmCollection<NotificationModel> data) {
        super(data, true);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final NotificationModel obj = getItem(position);
        holder.data = obj;
        final int itemId = obj.getId();
        //noinspection ConstantConditions
        holder.txtTitle.setText(obj.getTitle());
        holder.txtContent.setText(obj.getDescription());
        holder.txtDate.setText(obj.getDate());
    }

    @Override
    public long getItemId(int index) {
        //noinspection ConstantConditions
        return getItem(index).getId();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle;
        TextView txtContent;
        TextView txtDate;
        public NotificationModel data;

        MyViewHolder(View view) {
            super(view);
            txtTitle = view.findViewById(R.id.txt_title);
            txtContent = view.findViewById(R.id.txt_description);
            txtDate = view.findViewById(R.id.txt_date);
        }
    }
}
