package uk.co.intelitrack.intelizzz.components.preview;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AlertDialogLayout;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.text.TextUtils;
import android.text.style.BackgroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.arlib.floatingsearchview.util.adapter.OnItemTouchListenerAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;
import io.reactivex.annotations.Nullable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.intelitrack.intelizzz.R;
import uk.co.intelitrack.intelizzz.common.api.IntelizzzRepository;
import uk.co.intelitrack.intelizzz.common.api.RestApi;
import uk.co.intelitrack.intelizzz.common.data.Constants;
import uk.co.intelitrack.intelizzz.common.data.remote.ParentVehicle;
import uk.co.intelitrack.intelizzz.common.data.remote.Vehicle;
import uk.co.intelitrack.intelizzz.common.utils.SharedPreferencesUtils;

/**
 * Created by Filip Stojanovski (filip100janovski@gmail.com).
 */

public class VehiclesAdapter extends RecyclerView.Adapter<VehiclesAdapter.ViewHolder> {

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
    public VehiclesAdapter(VehiclesClickListener onItemClickListener, IntelizzzRepository intelizzzRepository, Context context) {
        this.mOnItemClickListener = onItemClickListener;
        this.mRepository = intelizzzRepository;
        if (!intelizzzRepository.getVehicles().isEmpty()) {
            mVehicles.addAll(intelizzzRepository.getVehicles());
            this.mContext = context;
        }
    }
    //endregion

    private void setData(List<Vehicle> units) {
        mVehicles.clear();
        mVehicles.addAll(units);
        notifyDataSetChanged();
    }

    public void sortByName() {
        if (mFilterField == Constants.FIELD_NAME) {
            if (mFilterType == Constants.DECREASING) {
                mFilterType = Constants.INCREASING;
                Collections.sort(mVehicles, new Vehicle.NameComparator());
            } else {
                mFilterType = Constants.DECREASING;
                Collections.sort(mVehicles, Collections.reverseOrder(new Vehicle.NameComparator()));
            }
        } else {
            mFilterType = Constants.INCREASING;
            mFilterField = Constants.FIELD_NAME;
            Collections.sort(mVehicles, new Vehicle.NameComparator());
        }
        notifyDataSetChanged();
    }

    public void sortByNumber() {
        if (mFilterField == Constants.FIELD_ID) {
            if (mFilterType == Constants.DECREASING) {
                mFilterType = Constants.INCREASING;
                Collections.sort(mVehicles, new Vehicle.IdComparator());
            } else {
                mFilterType = Constants.DECREASING;
                Collections.sort(mVehicles, Collections.reverseOrder(new Vehicle.IdComparator()));
            }
        } else {
            mFilterType = Constants.INCREASING;
            mFilterField = Constants.FIELD_ID;
            Collections.sort(mVehicles, new Vehicle.IdComparator());
        }
        notifyDataSetChanged();
    }

    public void sortByDays() {
        if (mFilterField == Constants.FIELD_DAYS) {
            if (mFilterType == Constants.DECREASING) {
                mFilterType = Constants.INCREASING;
                Collections.sort(mVehicles, new Vehicle.DaysComparator());
            } else {
                mFilterType = Constants.DECREASING;
                Collections.sort(mVehicles, Collections.reverseOrder(new Vehicle.DaysComparator()));
            }
        } else {
            mFilterType = Constants.INCREASING;
            mFilterField = Constants.FIELD_DAYS;
            Collections.sort(mVehicles, new Vehicle.DaysComparator());
        }
        notifyDataSetChanged();
    }

    public void sortByGeo() {
        if (mFilterField == Constants.FIELD_GEO) {
            if (mFilterType == Constants.DECREASING) {
                mFilterType = Constants.INCREASING;
                Collections.sort(mVehicles, new Vehicle.GeoComparator());
            } else {
                mFilterType = Constants.DECREASING;
                Collections.sort(mVehicles, Collections.reverseOrder(new Vehicle.GeoComparator()));
            }
        } else {
            mFilterType = Constants.INCREASING;
            mFilterField = Constants.FIELD_GEO;
            Collections.sort(mVehicles, new Vehicle.GeoComparator());
        }
        notifyDataSetChanged();
    }

    public void sortByWarning() {
        if (mFilterField == Constants.FIELD_WARNING) {
            if (mFilterType == Constants.DECREASING) {
                mFilterType = Constants.INCREASING;
                Collections.sort(mVehicles, new Vehicle.WarningComparator());
            } else {
                mFilterType = Constants.DECREASING;
                Collections.sort(mVehicles, Collections.reverseOrder(new Vehicle.WarningComparator()));
            }
        } else {
            mFilterType = Constants.INCREASING;
            mFilterField = Constants.FIELD_WARNING;
            Collections.sort(mVehicles, new Vehicle.WarningComparator());
        }
        notifyDataSetChanged();
    }

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
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_table, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(mVehicles.get(position), mOnItemClickListener);
//        holder.bind(mVehicles.get(position),mOnItemClickListener);


//        holder.mWarning.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////            tuka alert dialog vo koj ke ima API povik za reset
//
//                AlertDialog.Builder builder1 = new AlertDialog.Builder(mContext);
//                builder1.setMessage("Do you want to remove ?");
//                builder1.setCancelable(true);
//
//                builder1.setPositiveButton(
//                        "Yes",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                //Do your code...
//                            }
//                        });
//
//                builder1.setNegativeButton(
//                        "No",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                dialog.cancel();
//                            }
//                        });
//
//                AlertDialog alert11 = builder1.create();
//                alert11.show();
//            }
//        });


    }


    @Override
    public int getItemCount() {
        return mVehicles.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.number_7)
        TextView day7;
        @BindView(R.id.number_6)
        TextView day6;
        @BindView(R.id.number_5)
        TextView day5;
        @BindView(R.id.number_4)
        TextView day4;
        @BindView(R.id.number_3)
        TextView day3;
        @BindView(R.id.number_2)
        TextView day2;
        @BindView(R.id.number_1)
        TextView day1;

        @BindView(R.id.item_unit_id)
        TextView mId;
        @BindView(R.id.item_number)
        TextView mNumber;
        @BindView(R.id.item_battery)
        ImageView mBattery;
        @BindView(R.id.item_gsimbol)
        ImageView mGsimbol;
        @BindView(R.id.icon_warning)
        ImageView mWarning;
        RestApi api;
        SharedPreferencesUtils sharedPreferencesUtils;
//        @Nullable
//        @BindView(R.id.yes)
//        public ImageView buttonYes;
//        @BindView(R.id.delete)
//        ImageView delete;

     //   ImageView delete = (ImageView) itemView.findViewById(R.id.delete);

        private View mView;

        public ViewHolder(final View view) {
            super(view);
            this.mView = view;
            ButterKnife.bind(this, view);
        }

        public void bind(Vehicle vehicle, VehiclesClickListener onItemClickListener) {
            mNumber.setText(vehicle.getId());
            mId.setText(vehicle.getName());

            if (vehicle.hasGeofenceAlarm()) {
                mGsimbol.setVisibility(View.INVISIBLE);
            } else {
                mGsimbol.setVisibility(View.VISIBLE);
            }

            if (vehicle.isWarning()) {
                mWarning.setVisibility(View.VISIBLE);
            } else {
                mWarning.setVisibility(View.INVISIBLE);
            }

            mId.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(vehicle.getId());
                }
            });

            mWarning.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick2(vehicle.getId());
//                    AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext());
//                    //  builder.setMessage("Do you want to remove ?");
//                    builder.setCancelable(true);
////                    builder.setView(R.layout.   )    tuka da se vmetne layout od alertdialog
//                    builder.setView(R.layout.alert_dialog_reset);
//                    Button buttonYes=itemView.findViewById(R.id.yes);
//
//                              buttonYes.setOnClickListener(new View.OnClickListener() {
//                                  @Override
//                                  public void onClick(View v) {
//                                      api = new RestApi(itemView.getContext());
//                                      String JSESSIONIN = sharedPreferencesUtils.getSharedPreferencesString(Constants.JSESSIONID);
//                                      String condiIdno = "handled";
//                                      String typeIdno = "17";
//                                      String sourceIdno="2018-07-10 00:00:00";
//                                      String vehiColor = "2018-07-10 23:59:59";
//
//                                      Call<Vehicle>call = api.resetTamper(JSESSIONIN,condiIdno,typeIdno,sourceIdno,vehiColor);
//                                      call.enqueue(new Callback<Vehicle>() {
//                                          @Override
//                                          public void onResponse(Call<Vehicle> call, Response<Vehicle> response) {
//                                              if (response.isSuccessful()){
//                                                  Toast.makeText(itemView.getContext(), "USPESNO TAMPER", Toast.LENGTH_SHORT).show();
//                                              } else if (!response.isSuccessful()){
//                                                  Toast.makeText(itemView.getContext(), "NEUSPESNO TAMPER", Toast.LENGTH_SHORT).show();
//                                              }
//                                          }
//
//                                          @Override
//                                          public void onFailure(Call<Vehicle> call, Throwable t) {
//
//                                          }
//                                      });
//                                  }
//                              });
//
//
//
//
//
//
//                    builder.setNegativeButton(
//                            "",
//                            new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int id) {
//                                    dialog.cancel();
//                                }
//                            });
//
//                    AlertDialog alert11 = builder.create();
//                    alert11.show();
//                    alert11.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.argb(0, 200, 200, 200)));
                }
            });






            day1.setBackgroundResource(vehicle.getDays()[0] ? R.drawable.green_rectangle : R.drawable.red_rectangle);
            day2.setBackgroundResource(vehicle.getDays()[1] ? R.drawable.green_rectangle : R.drawable.red_rectangle);
            day3.setBackgroundResource(vehicle.getDays()[2] ? R.drawable.green_rectangle : R.drawable.red_rectangle);
            day4.setBackgroundResource(vehicle.getDays()[3] ? R.drawable.green_rectangle : R.drawable.red_rectangle);
            day5.setBackgroundResource(vehicle.getDays()[4] ? R.drawable.green_rectangle : R.drawable.red_rectangle);
            day6.setBackgroundResource(vehicle.getDays()[5] ? R.drawable.green_rectangle : R.drawable.red_rectangle);
            day7.setBackgroundResource(vehicle.getDays()[6] ? R.drawable.green_rectangle : R.drawable.red_rectangle);
        }
    }
}