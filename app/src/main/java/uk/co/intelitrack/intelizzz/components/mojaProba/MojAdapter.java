package uk.co.intelitrack.intelizzz.components.mojaProba;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.intelitrack.intelizzz.R;
import uk.co.intelitrack.intelizzz.common.data.remote.ParentVehicle;

public class MojAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<ParentVehicle> items = new ArrayList<>();

    public MojAdapter() {
    }
    void AddItems(List<ParentVehicle> vehicles){
        this.items.addAll(vehicles);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return ItemViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ItemViewHolder) holder).bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_unit_id)
        TextView ime;
        ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        static ItemViewHolder create(ViewGroup parent) {
            return new ItemViewHolder(
                    LayoutInflater.from(parent.getContext()).inflate(R.layout.delete_row, parent, false));
        }
        void bind(ParentVehicle parentVehicle) {
            ((TextView) itemView).setText(parentVehicle.getName());
        }
    }
}
