package uk.co.intelitrack.intelizzz.components.groups;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.intelitrack.intelizzz.R;
import uk.co.intelitrack.intelizzz.common.api.IntelizzzRepository;
import uk.co.intelitrack.intelizzz.common.data.remote.Company;
import uk.co.intelitrack.intelizzz.common.data.remote.Vehicle;

/**
 * Created by Filip Stojanovski (filip100janovski@gmail.com).
 */

public class UnassignedVehiclessDataAdapter extends RecyclerView.Adapter<UnassignedVehiclessDataAdapter.ViewHolder> {

    //region Fields
    private final IntelizzzRepository mRepository;
    private List<Vehicle> mUnassignedVehicless = new ArrayList<>();
    private String mCompanyId;
    //endregion

    //region Constructors
    public UnassignedVehiclessDataAdapter(IntelizzzRepository repository) {
        this.mRepository = repository;
        mUnassignedVehicless = repository.getUnassingVehiclesFromCompanies();

        for (Vehicle unassignedVehicles : mUnassignedVehicless) {
            unassignedVehicles.setChecked(false);
        }
    }
    //endregion

    public void setCompanyId(String companyId) {
        mCompanyId = companyId;
        for (Company company : mRepository.getCompanies()) {
            if (companyId.equals(company.getId())) {
                setData(Arrays.asList(company.getUnassignedVehicles()));
            }
        }
        notifyDataSetChanged();
    }

    private void setData(List<Vehicle> unassignedVehicless) {
        for (Vehicle unassignedVehicle : unassignedVehicless) {
            unassignedVehicle.setChecked(false);
        }

        mUnassignedVehicless.clear();
        mUnassignedVehicless.addAll(unassignedVehicless);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_custom_check, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(mUnassignedVehicless.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mUnassignedVehicless.size();
    }

    public List<Vehicle> getUnassignedVehicless() {
        List<Vehicle> results = new ArrayList<>();
        for (Vehicle vehicle : mUnassignedVehicless) {
            if (vehicle.isChecked()) {
                results.add(vehicle);
            }
        }
        return results;
    }

    public void refreshUnassignedVehicles() {
        if (TextUtils.isEmpty(mCompanyId)) {
            setData(mRepository.getUnassingVehiclesFromCompanies());
        } else {
            setCompanyId(mCompanyId);
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.checkMark)
        CheckBox mCheckBox;
        @BindView(R.id.itemNumber)
        TextView mNumber;
        @BindView(R.id.name)
        TextView mUnassignedVehiclesName;
        private View mView;

        public ViewHolder(final View view) {
            super(view);
            this.mView = view;
            ButterKnife.bind(this, view);
        }

        public void bind(Vehicle unassignedVehicles, int position) {
            mNumber.setText(String.valueOf(position));
            mUnassignedVehiclesName.setText(unassignedVehicles.getName());
            mCheckBox.setChecked(false);
            mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    mCheckBox.setChecked(mCheckBox.isChecked());
                    unassignedVehicles.setChecked(mCheckBox.isChecked());
                }
            });

            mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCheckBox.performClick();
                    unassignedVehicles.setChecked(mCheckBox.isChecked());
                }
            });
        }
    }
}