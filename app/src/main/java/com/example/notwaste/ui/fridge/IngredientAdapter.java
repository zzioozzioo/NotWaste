package com.example.notwaste.ui.fridge;


import static com.example.notwaste.util.DateUtil.calculateDday;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.notwaste.R;
import com.example.notwaste.data.model.Ingredient;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.ViewHolder> {

    private List<Ingredient> list;
    private OnItemClickListener listener;

    public IngredientAdapter(List<Ingredient> list, OnItemClickListener listener) {
        this.list = list;
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(Ingredient ingredient);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_ingredient, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Ingredient ingredient = list.get(position);

        holder.name.setText(ingredient.getName());
        holder.count.setText(ingredient.getCount() + "개");

        int dday = calculateDday(ingredient.getExpireDate());
        if (dday >= 0) {
            holder.dday.setText("D-" + dday);
            holder.dday.setTextColor(
                    holder.itemView.getContext().getColor(R.color.dday_normal)
            );
        } else {
            holder.dday.setText("D+" + Math.abs(dday));
            holder.dday.setTextColor(
                    holder.itemView.getContext().getColor(R.color.expired_red)
            );
        }

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onItemClick(ingredient);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, count, dday;

        ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tvName);
            count = itemView.findViewById(R.id.tvCount);
            dday = itemView.findViewById(R.id.tvDday);
        }
    }
}
