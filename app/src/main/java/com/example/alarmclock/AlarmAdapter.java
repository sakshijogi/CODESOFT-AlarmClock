package com.example.alarmclock;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.AlarmViewHolder> {

    private List<AlarmEntity> alarmList = new ArrayList<>();
    private final Context context;

    public interface OnToggleListener {
        void onToggle(AlarmEntity alarm, boolean isChecked);
    }

    private final OnToggleListener listener;

    public AlarmAdapter(Context context, OnToggleListener listener) {
        this.context = context;
        this.listener = listener;
    }

    public void setAlarms(List<AlarmEntity> alarms) {
        this.alarmList = alarms;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AlarmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_alarm, parent, false);
        return new AlarmViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlarmViewHolder holder, int position) {
        AlarmEntity alarm = alarmList.get(position);
        holder.textTime.setText(alarm.time);
        holder.textLabel.setText(alarm.label);
        holder.switchEnable.setChecked(alarm.isEnabled);

        holder.switchEnable.setOnCheckedChangeListener((buttonView, isChecked) -> {
            listener.onToggle(alarm, isChecked);
        });
    }

    @Override
    public int getItemCount() {
        return alarmList.size();
    }

    public static class AlarmViewHolder extends RecyclerView.ViewHolder {
        TextView textTime, textLabel;
        Switch switchEnable;

        public AlarmViewHolder(@NonNull View itemView) {
            super(itemView);
            textTime = itemView.findViewById(R.id.textTime);
            textLabel = itemView.findViewById(R.id.textLabel);
            switchEnable = itemView.findViewById(R.id.switchEnable);
        }
    }
}

