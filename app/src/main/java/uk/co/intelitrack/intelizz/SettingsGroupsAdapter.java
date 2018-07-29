package uk.co.intelitrack.intelizz;

import android.content.Context;
import android.text.TextUtils;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.TextView;

import com.thoughtbot.expandablecheckrecyclerview.CheckableChildRecyclerViewAdapter;
import com.thoughtbot.expandablecheckrecyclerview.models.CheckedExpandableGroup;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.intelitrack.intelizzz.R;
import uk.co.intelitrack.intelizzz.common.api.IntelizzzRepository;
import uk.co.intelitrack.intelizzz.common.data.Constants;
import uk.co.intelitrack.intelizzz.common.data.remote.Company;
import uk.co.intelitrack.intelizzz.common.data.remote.Group;
import uk.co.intelitrack.intelizzz.common.data.remote.ParentVehicle;
import uk.co.intelitrack.intelizzz.common.data.remote.Vehicle;
import uk.co.intelitrack.intelizzz.common.utils.SharedPreferencesUtils;
import uk.co.intelitrack.intelizzz.common.utils.SharedPreff;


public class SettingsGroupsAdapter extends CheckableChildRecyclerViewAdapter<SettingsGroupsAdapter.ParentVehicleViewHolder, ChildItemViewHolder> {

    //region final Fields
    private Map<String, Boolean> checkBoxStates = new HashMap<>();
    private final GroupsClickListenerSettings mOnItemClickListener;
    private final IntelizzzRepository mRepository;
    private boolean mIsClicked;
    private boolean mCheck;
    private boolean mTekst;
    private int mFilterField;
    private int mFilterType;
    private Context context;
    public SharedPreferencesUtils mSharedPreferencesUtils;
    IntelizzzRepository repository;
    //endregion
//    List<ParentVehicle> newGroupList = new ArrayList<>();
    //region Fields
    private List<ParentVehicle> mGroups = new ArrayList<>();
    SparseBooleanArray itemStateArray = new SparseBooleanArray();
    //endregion

    //region Constructors
    public SettingsGroupsAdapter(List<MultiCheckGengre> groups, IntelizzzRepository intelizzzRepository,
                                 GroupsClickListenerSettings listener, boolean isClicked, boolean isCheck, boolean isTekst, Context context1) {
        super(groups);
        this.mOnItemClickListener = listener;
        this.mRepository = intelizzzRepository;
       // this.mGroups = groups;
        this.mIsClicked = isClicked;
        this.mCheck = isCheck;
        this.mTekst = isTekst;
        this.context = context1;

    }

//

    //endregion
    public void setData(List<ParentVehicle> groups,  boolean isClicked, boolean isCheck, boolean isTekst) {
        mGroups.clear();


//            setGroups(newGroupList);

            mGroups.addAll(groups);





        mIsClicked = isClicked;
        this.mCheck = isCheck;
        this.mTekst = isTekst;
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
//        setData(temp);
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

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.customtwo, parent, false);
        return new ParentVehicleViewHolder(view);
    }

//    @Override
//    public ChildItemViewHolder onCreateChildViewHolder(ViewGroup child, int viewType) {
//        View view = LayoutInflater.from(child.getContext()).inflate(R.layout.customtwo, child, false);
//        return new ChildItemViewHolder(view);
//    }

//    @Override
//    public void onBindChildViewHolder(ChildItemViewHolder holder, int position, ExpandableGroup group, int childIndex) {
//        final Vehicle vehicle = ((ParentVehicle) group).getItems().get(childIndex);
//
//        holder.bind(vehicle, (GroupsClickListenerSettings) mOnItemClickListener);
//    }

    @Override
    public ChildItemViewHolder onCreateCheckChildViewHolder(ViewGroup child, int viewType) {
        View view = LayoutInflater.from(child.getContext()).inflate(R.layout.customtwo, child, false);
        return new ChildItemViewHolder(view);
    }


    @Override
    public void onBindCheckChildViewHolder(ChildItemViewHolder holder, int position, CheckedExpandableGroup group, int childIndex) {
        final Vehicle vehicle = (Vehicle) ((MultiCheckGengre) group).getItems().get(childIndex);
      // holder.text.setText(vehicle.getName());
        holder.setArtistName(vehicle.getName());
    }

    @Override
    public long getItemId(int position) {
        return Long.parseLong(mGroups.get(position).getId());
    }

    @Override
    public void onBindGroupViewHolder(ParentVehicleViewHolder holder, int flatPosition, ExpandableGroup group) {
//        final ParentVehicle parentVehicle = mGroups.get(flatPosition);
////                ((ParentVehicle) group);
//        holder.bind(parentVehicle, mOnItemClickListener, mIsClicked, mCheck, mTekst);
        holder.bind(((ParentVehicle) group), mOnItemClickListener, mIsClicked,mCheck,mTekst);


//        for (int i = 0; i < ; i++) {
//
//        }


    }

    private void onCheckChanged(int position, boolean checked) {

    }


    //endregion

    static class ParentVehicleViewHolder extends GroupViewHolder {
        @BindView(R.id.checkMark1)
        CheckedTextView checkBox;
        @BindView(R.id.item_unit_id1)
        TextView name;
        @BindView(R.id.group_arrow)
        ImageView mGroupArrow;
        @BindView(R.id.check)
        CheckBox check;
        @BindView(R.id.tekstch)
        TextView teksttch;
        private View mView;
        SharedPreferencesUtils mSharedPreferencesUtils;
        SharedPreff preff;

        public ParentVehicleViewHolder(View itemView) {
            super(itemView);
            this.mView = itemView;
            ButterKnife.bind(this, itemView);
           // itemView.setOnClickListener(this);
        }


        public void bind(ParentVehicle parentVehicle, GroupsClickListenerSettings groupsClickListener, boolean showArrow, boolean showCheck, boolean chowTekst) {
            name.setText(parentVehicle.getName());

//
//            mView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (parentVehicle.isCompany()) {
//                        groupsClickListener.onCompanyItemClick(parentVehicle.getId());
//                    } else if (!parentVehicle.isCompany()) {
//                        groupsClickListener.onGroupItemClick(parentVehicle.getId());
//                    }
//
////                          else if (v.getId()==mView.getId()){
////                        groupsClickListener.onDelete(v,getAdapterPosition());
////                    } else if (groupsClickListener!=null){
////                        groupsClickListener.onItemClick(v,getAdapterPosition());
////                    }
//                }
//            });


            mGroupArrow.setVisibility(showArrow ? View.VISIBLE : View.GONE);
            check.setVisibility(showCheck ? View.VISIBLE : View.GONE);
            teksttch.setVisibility(chowTekst ? View.VISIBLE : View.GONE);
        }


    }


//    static class ChildItemViewHolder extends ChildViewHolder {
//
////        @BindView(R.id.checkMark1)
//        CheckBox checkBox2;
//        @BindView(R.id.item_unit_id1)
//        TextView name;
//        @BindView(R.id.item_device_id)
//        TextView device_name;
//        String mVehicle;
//        private View mView;
//        final ArrayList<String> list = new ArrayList<>();
//
//
//        public ChildItemViewHolder(View itemView) {
//            super(itemView);
//            this.mView = itemView;
//
//            ButterKnife.bind(this, itemView);
//        }
//
//        public void bind(Vehicle vehicle, GroupsClickListenerSettings onItemClickListener) {
//            itemView.setTag(mView);
//            name.setText(vehicle.getName());
//
//            checkBox2 = (CheckBox) itemView.findViewById(R.id.checkMark1);
//
//            checkBox2.setChecked(false);
//
//
//
//            checkBox2.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//
//
//                    if (vehicle.isChecked()){
//                        vehicle.setChecked(true);
//                       checkBox2.setChecked(true);
//                    }else {
//                        checkBox2.setChecked(false);
//                        vehicle.setChecked(false);
//
//                    }
//                    onItemClickListener.onGroupChildItemClick(vehicle);
//                }
//
//            });
//
//
////                        if (vehicle.isChecked()){
////                            list.add(vehicle.getId());
////                        }
//
//
////                     onItemClickListener.onGroupChildItemClick(vehicle.getId());
////
////                        String[] data = new String[]{vehicle.getId()};
////
////                        final ArrayList<String> list = new ArrayList<>();
////
////                      for (int i = 0; i < data.length; i++) {
////                          if (data.length() == 0 || data[i].length() == 0){
////                          list.add(data[i]);
////                }
////                      }
//
//
////                            Toast.makeText(mView.getContext(), "Odbereni ID : " + list + "\n", Toast.LENGTH_LONG).show();
//                        //    SharedPreferencesUtils shared = new SharedPreferencesUtils(mView.getContext());
//                        //    shared.setSharedPreferencesString(Constants.OLI_ID, vehicle.getId());
//
//
    }
//}
//}
