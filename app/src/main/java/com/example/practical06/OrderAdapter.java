package com.example.practical06;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

/**
 * Adapter for displaying orders in a RecyclerView.
 */
public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private List<Order> orderList;

    public OrderAdapter(List<Order> orderList) {
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = orderList.get(position);
        holder.tvOrderId.setText(order.orderId);
        holder.tvOrderDate.setText(order.date);
        holder.tvOrderAmount.setText("₹" + String.format("%.2f", order.amount));
        holder.tvOrderStatus.setText(order.status);
        holder.tvOrderItems.setText(order.itemsDescription);

        holder.btnTrack.setOnClickListener(v -> {
            // Ideally simulate tracking or show details
            android.widget.Toast.makeText(v.getContext(), "Status: " + order.status, android.widget.Toast.LENGTH_SHORT)
                    .show();
        });
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView tvOrderId, tvOrderDate, tvOrderAmount, tvOrderStatus, tvOrderItems;
        Button btnTrack;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            tvOrderId = itemView.findViewById(R.id.tv_order_id);
            tvOrderDate = itemView.findViewById(R.id.tv_order_date);
            tvOrderAmount = itemView.findViewById(R.id.tv_order_amount);
            tvOrderStatus = itemView.findViewById(R.id.tv_order_status);
            tvOrderItems = itemView.findViewById(R.id.tv_order_items);
            btnTrack = itemView.findViewById(R.id.btn_track);
        }
    }
}
