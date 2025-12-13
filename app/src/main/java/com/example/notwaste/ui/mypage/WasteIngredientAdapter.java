package com.example.notwaste.ui.mypage;
import static com.example.notwaste.util.DateUtil.calculateDday;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.notwaste.R;
import com.example.notwaste.data.model.Ingredient;

import java.util.List;

public class WasteIngredientAdapter
        extends RecyclerView.Adapter<WasteIngredientAdapter.ViewHolder> {

    private final List<Ingredient> list;

    public WasteIngredientAdapter(List<Ingredient> list) {
        this.list = list;
    }

    public void update(List<Ingredient> newList) {
        list.clear();
        list.addAll(newList);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_waste_ingredient, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder h, int pos) {
        Ingredient ingredient = list.get(pos);
        h.tvName.setText(ingredient.getName() + " (" + ingredient.getCount() + "개)");

        int dday = calculateDday(ingredient.getExpireDate());
        if (dday >= 0) { // 사실상 여기로는 안 옴
            h.tvExpire.setText("D-" + dday);
            h.tvExpire.setTextColor(
                    h.itemView.getContext().getColor(R.color.dday_normal)
            );
        } else {
            h.tvExpire.setText("D+" + Math.abs(dday));
            h.tvExpire.setTextColor(
                    h.itemView.getContext().getColor(R.color.expired_red)
            );
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvCount, tvExpire;
        ViewHolder(View v) {
            super(v);
            tvName = v.findViewById(R.id.tvName);
            tvCount = v.findViewById(R.id.tvCount);
            tvExpire = v.findViewById(R.id.tvExpire);
        }
    }
}
