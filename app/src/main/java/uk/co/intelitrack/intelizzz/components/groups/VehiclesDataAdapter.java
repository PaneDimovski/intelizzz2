package uk.co.intelitrack.intelizzz.components.groups;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.intelitrack.intelizzz.R;
import uk.co.intelitrack.intelizzz.common.data.remote.Vehicle;

/**
 * Created by Filip Stojanovski (filip100janovski@gmail.com).
 */

public class VehiclesDataAdapter extends RecyclerView.Adapter<VehiclesDataAdapter.ViewHolder> {

    //region Fields
    private List<Vehicle> mVehicles = new ArrayList<>();
    //endregion

    //region Constructors
    public VehiclesDataAdapter() {
    }
    //endregion

    public void setData(List<Vehicle> vehicles) {
        for (Vehicle vehicle : vehicles) {
            vehicle.setChecked(false);
        }

        mVehicles.clear();
        mVehicles.addAll(vehicles);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_custom_check, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(mVehicles.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mVehicles.size();
    }

    public List<Vehicle> getSelectedVehicles() {
        List<Vehicle> results = new ArrayList<>();
        for (Vehicle vehicle : mVehicles) {
            if (vehicle.isChecked()) {
                results.add(vehicle);
            }
        }
        return results;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.checkMark)
        CheckBox mCheckBox;
        @BindView(R.id.itemNumber)
        TextView mNumber;
        @BindView(R.id.name)
        TextView mVehicleName;
        private View mView;

        public ViewHolder(final View view) {
            super(view);
            this.mView = view;
            ButterKnife.bind(this, view);
        }

        public void bind(Vehicle vehicle, int position) {
            mNumber.setText(String.valueOf(position));
            mVehicleName.setText(vehicle.getName());
            mCheckBox.setChecked(false);

            mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    mCheckBox.setChecked(mCheckBox.isChecked());
                    vehicle.setChecked(mCheckBox.isChecked());
                }
            });

            mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCheckBox.performClick();
                    vehicle.setChecked(mCheckBox.isChecked());
                }
            });
        }
    }
}