package com.example.notwaste.ui.notification;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notwaste.R;
import com.example.notwaste.data.model.NotificationItem;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NotificationAdapter
        extends RecyclerView.Adapter<NotificationAdapter.ViewHolder>{

    private final List<NotificationItem> list;

    public NotificationAdapter(List<NotificationItem> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType
    ) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_notification, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull ViewHolder holder,
            int position
    ) {
        NotificationItem item = list.get(position);

        holder.tvMessage.setText(item.getMessage());
        holder.tvDateLabel.setText(getDateLabel(item.getCreatedAt()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvMessage;
        TextView tvDateLabel;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMessage = itemView.findViewById(R.id.tvMessage);
            tvDateLabel = itemView.findViewById(R.id.tvDateLabel);
        }
    }

    private String getDateLabel(long time) {
        Calendar now = Calendar.getInstance();
        Calendar date = Calendar.getInstance();
        date.setTimeInMillis(time);

        long diff =
                (now.getTimeInMillis() - date.getTimeInMillis()) / (1000 * 60 * 60 * 24);

        if (diff == 0) return "TODAY";
        if (diff == 1) return "YESTERDAY";

        return new SimpleDateFormat("EEE", Locale.ENGLISH)
                .format(new Date(time))
                .toUpperCase();
    }

}
