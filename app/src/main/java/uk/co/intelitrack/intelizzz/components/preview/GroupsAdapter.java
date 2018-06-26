package uk.co.intelitrack.intelizzz.components.preview;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.intelitrack.intelizzz.R;
import uk.co.intelitrack.intelizzz.common.api.IntelizzzRepository;
import uk.co.intelitrack.intelizzz.common.data.Constants;
import uk.co.intelitrack.intelizzz.common.data.remote.Company;
import uk.co.intelitrack.intelizzz.common.data.remote.Group;
import uk.co.intelitrack.intelizzz.common.data.remote.ParentVehicle;
import uk.co.intelitrack.intelizzz.common.data.remote.Vehicle;

/**
 * Created by Filip Stojanovski (filip100janovski@gmail.com).
 */

class GroupsAdapter extends ExpandableRecyclerViewAdapter<GroupsAdapter.ParentVehicleViewHolder, GroupsAdapter.ChildItemViewHolder> {

    //region final Fields
    private final GroupsClickListener mOnItemClickListener;
    private final IntelizzzRepository mRepository;
    private boolean mIsClicked;
    private int mFilterField;
    private int mFilterType;
    //endregion

    //region Fields
    private List<ParentVehicle> mGroups = new ArrayList<>();
    //endregion

    //region Constructors
    public GroupsAdapter(List<ParentVehicle> groups, IntelizzzRepository intelizzzRepository,
                         GroupsClickListener listener, boolean isClicked) {
        super(groups);
        this.mOnItemClickListener = listener;
        this.mRepository = intelizzzRepository;
        this.mGroups = groups;
        this.mIsClicked = isClicked;
    }
    //endregion

    public void setData(List<ParentVehicle> groups, boolean isClicked) {
        mGroups.clear();
        mGroups.addAll(groups);
        mIsClicked = isClicked;
        notifyDataSetChanged();
    }

    public void setGroup(ParentVehicle group) {
        mGroups.clear();
        mGroups.add(group);
        notifyDataSetChanged();
    }

    public void sortByName() {
        if (mFilterField == Constants.FIELD_NAME) {
            if (mFilterType == Constants.DECREASING) {
                mFilterType = Constants.INCREASING;
                Collections.sort(mGroups, new ParentVehicle.NameComparator());
                if (mIsClicked) {
                    Collections.sort(mGroups.get(0).getVehicles(), new Vehicle.NameComparator());
                }
            } else {
                mFilterType = Constants.DECREASING;
                Collections.sort(mGroups, Collections.reverseOrder(new ParentVehicle.NameComparator()));
                if (mIsClicked) {
                    Collections.sort(mGroups.get(0).getVehicles(), Collections.reverseOrder(new Vehicle.NameComparator()));
                }
            }
        } else {
            mFilterType = Constants.INCREASING;
            mFilterField = Constants.FIELD_NAME;
            Collections.sort(mGroups, new ParentVehicle.NameComparator());
            if (mIsClicked) {
                Collections.sort(mGroups.get(0).getVehicles(), new Vehicle.NameComparator());
            }
        }
        notifyDataSetChanged();
    }

    public void sortByNumber() {
        if (mFilterField == Constants.FIELD_ID) {
            if (mFilterType == Constants.DECREASING) {
                mFilterType = Constants.INCREASING;
                Collections.sort(mGroups, new ParentVehicle.IdComparator());
                if (mIsClicked) {
                    Collections.sort(mGroups.get(0).getVehicles(), new Vehicle.IdComparator());
                }
            } else {
                mFilterType = Constants.DECREASING;
                Collections.sort(mGroups, Collections.reverseOrder(new ParentVehicle.IdComparator()));
                if (mIsClicked) {
                    Collections.sort(mGroups.get(0).getVehicles(), Collections.reverseOrder(new Vehicle.IdComparator()));
                }
            }
        } else {
            mFilterType = Constants.INCREASING;
            mFilterField = Constants.FIELD_ID;
            Collections.sort(mGroups, new ParentVehicle.IdComparator());
            if (mIsClicked) {
                Collections.sort(mGroups.get(0).getVehicles(), new Vehicle.IdComparator());
            }
        }
        notifyDataSetChanged();
    }


    public void sortByDays() {
        if (mFilterField == Constants.FIELD_DAYS) {
            if (mFilterType == Constants.DECREASING) {
                mFilterType = Constants.INCREASING;
                Collections.sort(mGroups, new ParentVehicle.DaysComparator());
                if (mIsClicked) {
                    Collections.sort(mGroups.get(0).getVehicles(), new Vehicle.DaysComparator());
                }
            } else {
                mFilterType = Constants.DECREASING;
                Collections.sort(mGroups, Collections.reverseOrder(new ParentVehicle.DaysComparator()));
                if (mIsClicked) {
                    Collections.sort(mGroups.get(0).getVehicles(), Collections.reverseOrder(new Vehicle.DaysComparator()));
                }
            }
        } else {
            mFilterType = Constants.INCREASING;
            mFilterField = Constants.FIELD_DAYS;
            Collections.sort(mGroups, new ParentVehicle.DaysComparator());
            if (mIsClicked) {
                Collections.sort(mGroups.get(0).getVehicles(), new Vehicle.DaysComparator());
            }
        }
        notifyDataSetChanged();
    }

    public void sortByGeo() {
        if (mFilterField == Constants.FIELD_GEO) {
            if (mFilterType == Constants.DECREASING) {
                mFilterType = Constants.INCREASING;
                Collections.sort(mGroups, new ParentVehicle.GeoComparator());
                if (mIsClicked) {
                    Collections.sort(mGroups.get(0).getVehicles(), new Vehicle.GeoComparator());
                }
            } else {
                mFilterType = Constants.DECREASING;
                Collections.sort(mGroups, Collections.reverseOrder(new ParentVehicle.GeoComparator()));
                if (mIsClicked) {
                    Collections.sort(mGroups.get(0).getVehicles(), Collections.reverseOrder(new Vehicle.GeoComparator()));
                }
            }
        } else {
            mFilterType = Constants.INCREASING;
            mFilterField = Constants.FIELD_GEO;
            Collections.sort(mGroups, new ParentVehicle.GeoComparator());
            if (mIsClicked) {
                Collections.sort(mGroups.get(0).getVehicles(), new Vehicle.GeoComparator());
            }
        }
        notifyDataSetChanged();
    }

    public void sortByWarning() {
        if (mFilterField == Constants.FIELD_WARNING) {
            if (mFilterType == Constants.DECREASING) {
                mFilterType = Constants.INCREASING;
                Collections.sort(mGroups, new ParentVehicle.WarningComparator());
                if (mIsClicked) {
                    Collections.sort(mGroups.get(0).getVehicles(), new Vehicle.WarningComparator());
                }
            } else {
                mFilterType = Constants.DECREASING;
                Collections.sort(mGroups, Collections.reverseOrder(new ParentVehicle.WarningComparator()));

                if (mIsClicked) {
                    Collections.sort(mGroups.get(0).getVehicles(), Collections.reverseOrder(new Vehicle.WarningComparator()));
                }
            }
        } else {
            mFilterType = Constants.INCREASING;
            mFilterField = Constants.FIELD_WARNING;
            Collections.sort(mGroups, new ParentVehicle.WarningComparator());

            if (mIsClicked) {
                Collections.sort(mGroups.get(0).getVehicles(), new Vehicle.WarningComparator());
            }
        }
        notifyDataSetChanged();
    }


    void filter(String text) {
        List<Group> temp = new ArrayList();
        for (Group group : getAllGroups()) {
            if (group.getName().toLowerCase().contains(text)) {
                temp.add(group);
            }
        }
        if (TextUtils.isEmpty(text)) {
            temp = getAllGroups();
        }
        //update recyclerview
        //setData(temp);
    }

    private List<Group> getAllGroups() {
        final List<Group> tempGroupList = new ArrayList<>();
        if (!mRepository.getCompanies().isEmpty()) {
            for (Company company : mRepository.getCompanies()) {
                tempGroupList.addAll(Arrays.asList(company.getGroups()));
            }
        }
        return tempGroupList;
    }

    //region expandableAdapterMethods
    @Override
    public ParentVehicleViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_table, parent, false);
        return new ParentVehicleViewHolder(view);
    }

    @Override
    public ChildItemViewHolder onCreateChildViewHolder(ViewGroup child, int viewType) {
        View view = LayoutInflater.from(child.getContext()).inflate(R.layout.item_table, child, false);
        return new ChildItemViewHolder(view);
    }

    @Override
    public void onBindChildViewHolder(ChildItemViewHolder holder, int flatPosition, ExpandableGroup group, int childIndex) {
        final Vehicle vehicle = ((ParentVehicle) group).getItems().get(childIndex);
        holder.bind(vehicle, mOnItemClickListener);

    }

    @Override
    public void onBindGroupViewHolder(ParentVehicleViewHolder holder, int flatPosition, ExpandableGroup group) {
        holder.bind(((ParentVehicle) group), mOnItemClickListener, mIsClicked);
    }
    //endregion

    static class ParentVehicleViewHolder extends GroupViewHolder {

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
        @BindView(R.id.group_arrow)
        ImageView mGroupArrow;
        private View mView;

        public ParentVehicleViewHolder(View itemView) {
            super(itemView);
            this.mView = itemView;
            ButterKnife.bind(this, itemView);
        }

        public void bind(ParentVehicle parentVehicle, GroupsClickListener groupsClickListener, boolean showArrow) {
            mNumber.setText(parentVehicle.getId());
            mId.setText(parentVehicle.getName());
            mId.setTextColor(mView.getContext().getResources().getColor(R.color.light_blue));

            mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (parentVehicle.isCompany()) {
                        groupsClickListener.onCompanyItemClick(parentVehicle.getId());
                    } else {
                        groupsClickListener.onGroupItemClick(parentVehicle.getId());
                    }
                }
            });

            mGroupArrow.setVisibility(showArrow ? View.VISIBLE : View.GONE);

            if (parentVehicle.getVehicles() != null && !parentVehicle.getVehicles().isEmpty()) {
                if (isGeo(parentVehicle.getVehicles())) {
                    mGsimbol.setVisibility(View.INVISIBLE);
                } else {
                    mGsimbol.setVisibility(View.VISIBLE);
                }

                if (isWarning(parentVehicle.getVehicles())) {
                    mWarning.setVisibility(View.VISIBLE);
                } else {
                    mWarning.setVisibility(View.INVISIBLE);
                }
            }
            day1.setBackgroundResource(isActiveDay(parentVehicle.getVehicles(), 0) ? R.drawable.green_rectangle : R.drawable.red_rectangle);
            day2.setBackgroundResource(isActiveDay(parentVehicle.getVehicles(), 1) ? R.drawable.green_rectangle : R.drawable.red_rectangle);
            day3.setBackgroundResource(isActiveDay(parentVehicle.getVehicles(), 2) ? R.drawable.green_rectangle : R.drawable.red_rectangle);
            day4.setBackgroundResource(isActiveDay(parentVehicle.getVehicles(), 3) ? R.drawable.green_rectangle : R.drawable.red_rectangle);
            day5.setBackgroundResource(isActiveDay(parentVehicle.getVehicles(), 4) ? R.drawable.green_rectangle : R.drawable.red_rectangle);
            day6.setBackgroundResource(isActiveDay(parentVehicle.getVehicles(), 5) ? R.drawable.green_rectangle : R.drawable.red_rectangle);
            day7.setBackgroundResource(isActiveDay(parentVehicle.getVehicles(), 6) ? R.drawable.green_rectangle : R.drawable.red_rectangle);
        }

        private boolean isGeo(List<Vehicle> vehicles) {
            if (vehicles == null || vehicles.isEmpty()) {
                return true;
            }
            for (Vehicle vehicle : vehicles) {
                if (!vehicle.hasGeofenceAlarm()) {
                    return false;
                }
            }
            return true;
        }

        private boolean isWarning(List<Vehicle> vehicles) {
            if (vehicles == null || vehicles.isEmpty()) {
                return false;
            }
            for (Vehicle vehicle : vehicles) {
                if (vehicle.isWarning()) {
                    return true;
                }
            }
            return false;
        }

        private boolean isActiveDay(List<Vehicle> vehicles, int day) {
            if (vehicles == null || vehicles.isEmpty()) {
                return true;
            }
            for (Vehicle vehicle : vehicles) {
                if (vehicle.getDays() == null || vehicle.getDays().length == 0 || !vehicle.getDays()[day]) {
                    return false;
                }
            }
            return true;
        }
    }

    static class ChildItemViewHolder extends ChildViewHolder {

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
        @BindView(R.id.item_battery)
        ImageView mBattery;
        @BindView(R.id.item_gsimbol)
        ImageView mGsimbol;
        @BindView(R.id.icon_warning)
        ImageView mWarning;
        private View mView;

        public ChildItemViewHolder(View itemView) {
            super(itemView);
            this.mView = itemView;
            ButterKnife.bind(this, itemView);
        }

        public void bind(Vehicle vehicle, GroupsClickListener onItemClickListener) {
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

            mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onGroupChildItemClick(vehicle.getId());
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