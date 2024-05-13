package com.softmeth.project5;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Adapter for displaying donut items in a RecyclerView.
 * This adapter handles the binding of donut data to views that are displayed within a RecyclerView.
 *
 * @author Ridwan Sharkar
 */
class DonutAdapter extends RecyclerView.Adapter<DonutAdapter.DonutViewHolder>
{
    private Context context;
    private ArrayList<DonutItem> items;
    private OnItemClickListener onItemClickListener;

    /**
     * Interface for handling item click events.
     */
    public interface OnItemClickListener {
        void onItemClick(int position);  // Changed to include position parameter
    }

    /**
     * Constructs a new DonutAdapter.
     *
     * @param context the UI context in which the adapter operates, used to inflate layouts.
     * @param items the list of DonutItem objects to be displayed.
     * @param listener the listener that handles item click events.
     */
    public DonutAdapter(Context context, ArrayList<DonutItem> items, OnItemClickListener listener) {
        this.context = context;
        this.items = items;
        this.onItemClickListener = listener;  // Set listener via constructor
    }

    /**
     * Inflates the item layout and creates a new ViewHolder.
     *
     * @param parent the ViewGroup into which the new view will be added after it is bound to an adapter position.
     * @param viewType the view type of the new View.
     * @return a new ViewHolder that holds the View for each item
     */
    @NonNull
    @Override
    public DonutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_view, parent, false);
        return new DonutViewHolder(view);
    }

    /**
     * Binds the data at the specified position in the data set to the corresponding view in the ViewHolder.
     *
     * @param holder the ViewHolder which should be updated to represent the contents of the item at the given position.
     * @param position the position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull DonutViewHolder holder, int position) {
        DonutItem item = items.get(position);
        holder.tvName.setText(item.getName());
        holder.tvPrice.setText(item.getPrice());
        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(position);  // Pass position on click
            }
        });
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return the size of the items list
     */
    @Override
    public int getItemCount() {
        return items.size();
    }

    /**
     * A ViewHolder describes an item view and metadata about its place within the RecyclerView.
     */
    public static class DonutViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvPrice;
        public DonutViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvPrice = itemView.findViewById(R.id.tv_price);
        }
    }
}