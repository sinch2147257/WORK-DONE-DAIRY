package com.example.dairy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class eventAdapter extends RecyclerView.Adapter<eventAdapter.EventViewHolder> implements Filterable {

    public LayoutInflater layoutInflater;
    ArrayList<Events> events;
    ArrayList<Events> eventsAll;
    Context context;


    public eventAdapter(Context context) {
        events = new ArrayList<>();
        eventsAll = new ArrayList<>(events);
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    public void setData(ArrayList<Events> events) {
        this.events = events;
        eventsAll = new ArrayList<>(events);
    }

    @NonNull
    @Override
    public eventAdapter.EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View dataView = layoutInflater.inflate(R.layout.recycle_event, parent, false);
        return new eventAdapter.EventViewHolder(dataView);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        Events eventss = events.get(position);
        holder.ddate.setText(eventss.ddate);
        holder.ft.setText(eventss.ft);
        holder.tt.setText(eventss.tt);
        holder.po.setText(eventss.po);

    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }
    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<Events> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(eventsAll);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Events list : eventsAll) {
                    if (list.getPo().toLowerCase().contains(filterPattern)) {
                        filteredList.add(list);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            events.clear();
            events.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };



    public class EventViewHolder extends RecyclerView.ViewHolder {
        TextView ddate, ft, tt, po;
        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            ddate = itemView.findViewById(R.id.ddate);
            ft = itemView.findViewById(R.id.ft);
            tt = itemView.findViewById(R.id.tt);
            po = itemView.findViewById(R.id.po);


        }
    }
}
