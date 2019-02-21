package com.zhiqisim.visionworks;

import java.util.Date;
import java.text.SimpleDateFormat;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;

public class LogsAdapter extends FirestoreRecyclerAdapter<Logs, LogsAdapter.LogsHolder> {
    public LogsAdapter(@NonNull FirestoreRecyclerOptions<Logs> options) {
        super(options);
    }

    @NonNull
    @Override
    public LogsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.log_item,
                parent, false);
        return new LogsHolder(v);
    }

    @Override
    protected void onBindViewHolder(@NonNull LogsHolder holder, int position, @NonNull Logs model) {
        long unixTime = model.getTime();
        Date date = new java.util.Date(unixTime*1000L);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String formattedDate = sdf.format(date);
        holder.textViewLicense.setText(model.getLicense());
        holder.textViewName.setText(model.getName());
        holder.textViewDate.setText(formattedDate);
        holder.textViewPurpose.setText(model.getPurpose());
        holder.textViewChecked.setText(String.valueOf(model.isChecked()));
    }

    class LogsHolder extends RecyclerView.ViewHolder {
        TextView textViewName;
        TextView textViewLicense;
        TextView textViewPurpose;
        TextView textViewDate;
        TextView textViewChecked;

        public LogsHolder(View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.text_view_name);
            textViewLicense = itemView.findViewById(R.id.text_view_license);
            textViewDate = itemView.findViewById(R.id.text_view_time);
            textViewPurpose = itemView.findViewById(R.id.text_view_purpose);
            textViewChecked = itemView.findViewById(R.id.text_view_checked);
        }
    }
}
