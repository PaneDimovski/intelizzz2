package uk.co.intelitrack.intelizzz.components.preview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.intelitrack.intelizzz.R;
import uk.co.intelitrack.intelizzz.common.api.IntelizzzRepository;
import uk.co.intelitrack.intelizzz.common.data.remote.Vehicle;

/**
 * Created by Anti on 6/12/2018.
 */

public class UnitAdapter  extends RecyclerView.Adapter<UnitAdapter.ViewHolder> {

    //region final Fields
    private final VehiclesClickListener mOnItemClickListener;
    private final IntelizzzRepository mRepository;
    private int mFilterField;
    private int mFilterType;
    private Context mContext;
    //endregion

    //region Fields
    private List<Vehicle> mVehicles = new ArrayList<>();
    //endregion

    //region Constructors
    public UnitAdapter(VehiclesClickListener onItemClickListener, IntelizzzRepository intelizzzRepository, Context context) {
        this.mOnItemClickListener = onItemClickListener;
        this.mRepository = intelizzzRepository;
        if (!intelizzzRepository.getVehicles().isEmpty()) {
            mVehicles.addAll(intelizzzRepository.getVehicles());
            this.mContext = context;
        }
    }
    //endregion

    public void setData(List<Vehicle> units) {
        mVehicles.clear();
        mVehicles.addAll(units);
        notifyDataSetChanged();
    }

//    public void sortByName() {
//        if (mFilterField == Constants.FIELD_NAME) {
//            if (mFilterType == Constants.DECREASING) {
//                mFilterType = Constants.INCREASING;
//                Collections.sort(mVehicles, new Vehicle.NameComparator());
//            } else {
//                mFilterType = Constants.DECREASING;
//                Collections.sort(mVehicles, Collections.reverseOrder(new Vehicle.NameComparator()));
//            }
//        } else {
//            mFilterType = Constants.INCREASING;
//            mFilterField = Constants.FIELD_NAME;
//            Collections.sort(mVehicles, new Vehicle.NameComparator());
//        }
//        notifyDataSetChanged();
//    }
//
//    public void sortByNumber() {
//        if (mFilterField == Constants.FIELD_ID) {
//            if (mFilterType == Constants.DECREASING) {
//                mFilterType = Constants.INCREASING;
//                Collections.sort(mVehicles, new Vehicle.IdComparator());
//            } else {
//                mFilterType = Constants.DECREASING;
//                Collections.sort(mVehicles, Collections.reverseOrder(new Vehicle.IdComparator()));
//            }
//        } else {
//            mFilterType = Constants.INCREASING;
//            mFilterField = Constants.FIELD_ID;
//            Collections.sort(mVehicles, new Vehicle.IdComparator());
//        }
//        notifyDataSetChanged();
//    }

    void filter(String text) {
        List<Vehicle> temp = new ArrayList();
        for (Vehicle vehicle : mRepository.getVehicles()) {
            if (vehicle.getNm().toLowerCase().contains(text)) {
                temp.add(vehicle);
            }
        }
        if (TextUtils.isEmpty(text)) {
            temp = mRepository.getVehicles();
        }
        //update recyclerview
        setData(temp);
    }

    @Override
    public UnitAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.delete_row, parent, false);
        return new UnitAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UnitAdapter.ViewHolder holder, int position) {
//        holder.bind(mVehicles.get(position), mOnItemClickListener);
        Vehicle vehicle1 = mVehicles.get(position);
        holder.mId.setText(vehicle1.getId());
        holder.mNumber.setText(vehicle1.getNm());
    }


    @Override
    public int getItemCount() {
        return mVehicles.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.item_unit_id)
        TextView mId;
        @BindView(R.id.item_number)
        TextView mNumber;

//        @BindView(R.id.delete)
//        ImageView delete;

        // ImageView delete = (ImageView) itemView.findViewById(R.id.delete);

        private View mView;

        public ViewHolder(final View view) {
            super(view);
            this.mView = view;
            ButterKnife.bind(this, view);
        }

//        public void bind(Vehicle vehicle, VehiclesClickListener onItemClickListener) {
//            mNumber.setText(vehicle.getId());
//            mId.setText(vehicle.getName());
//
//
//
//            mId.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    onItemClickListener.onItemClick(vehicle.getName());
//                }
//            });






//            delete.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    AlertDialog.Builder dialog3 = new AlertDialog.Builder(itemView.getContext());
//                    dialog3.setCancelable(true);
//
//
//                    dialog3.setPositiveButton("", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//
//                        }
//                    });
//                    dialog3.setView(R.layout.alert_dialog_delete_unit);
//                    AlertDialog alert2 = dialog3.create();
//                    alert2.show();
//                    alert2.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.argb(0, 100, 100, 100)));
//                }
//            });


    }
}
//}
