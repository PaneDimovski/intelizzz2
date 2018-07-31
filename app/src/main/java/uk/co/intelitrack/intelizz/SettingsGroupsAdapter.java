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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.intelitrack.intelizzz.R;
import uk.co.intelitrack.intelizzz.common.api.IntelizzzRepository;
import uk.co.intelitrack.intelizzz.common.data.remote.Company;
import uk.co.intelitrack.intelizzz.common.data.remote.Group;
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
    public SharedPreferencesUtils utils;
    IntelizzzRepository repository;
    //endregion
//    List<ParentVehicle> newGroupList = new ArrayList<>();
    //region Fields
    CheckBox chek;
    private List<Vehicle> vehicless = new ArrayList<>();
    private List<MultiCheckGengre> mGroups = new ArrayList<>();
    SparseBooleanArray itemStateArray = new SparseBooleanArray();
    //endregion
    boolean cheked;
    String s = "";
    List<String> stringList2 = new ArrayList<String>(  );
    //region Constructors
    public SettingsGroupsAdapter(List<MultiCheckGengre> groups, IntelizzzRepository intelizzzRepository,
                                 GroupsClickListenerSettings listener, boolean isClicked, boolean isCheck, boolean isTekst, Context context1) {
        super(groups);
        this.mOnItemClickListener = listener;
        this.mRepository = intelizzzRepository;
        this.mGroups = groups;
        this.mIsClicked = isClicked;
        this.mCheck = isCheck;
        this.mTekst = isTekst;
        this.context = context1;

    }

//

    //endregion
    public void setData(List<MultiCheckGengre> groups, boolean isClicked, boolean isCheck, boolean isTekst) {
        mGroups.clear();


//            setGroups(newGroupList);

        mGroups.addAll(groups);


        mIsClicked = isClicked;
        this.mCheck = isCheck;
        this.mTekst = isTekst;
        notifyDataSetChanged();
    }


    public void setGroup(MultiCheckGengre group) {
        mGroups.clear();
        mGroups.add(group);
        notifyDataSetChanged();
    }

//    public void sortByName() {
//        if (mFilterField == Constants.FIELD_NAME) {
//            if (mFilterType == Constants.DECREASING) {
//                mFilterType = Constants.INCREASING;
//                Collections.sort(mGroups, new MultiCheckGengre.NameComparator());
//                if (mIsClicked) {
//                    Collections.sort(mGroups.get(0).getVehicles(), new Vehicle.NameComparator());
//                }
//            } else {
//                mFilterType = Constants.DECREASING;
//                Collections.sort(mGroups, Collections.reverseOrder(new MultiCheckGengre.NameComparator()));
//                if (mIsClicked) {
//                    Collections.sort(mGroups.get(0).getVehicles(), Collections.reverseOrder(new Vehicle.NameComparator()));
//                }
//            }
//        } else {
//            mFilterType = Constants.INCREASING;
//            mFilterField = Constants.FIELD_NAME;
//            Collections.sort(mGroups, new MultiCheckGengre.NameComparator());
//            if (mIsClicked) {
//                Collections.sort(mGroups.get(0).getVehicles(), new Vehicle.NameComparator());
//            }
//        }
//        notifyDataSetChanged();
//    }

//    public void sortByNumber() {
//        if (mFilterField == Constants.FIELD_ID) {
//            if (mFilterType == Constants.DECREASING) {
//                mFilterType = Constants.INCREASING;
//                Collections.sort(mGroups, new MultiCheckGengre.IdComparator());
//                if (mIsClicked) {
//                    Collections.sort(mGroups.get(0).getVehicles(), new Vehicle.IdComparator());
//                }
//            } else {
//                mFilterType = Constants.DECREASING;
//                Collections.sort(mGroups, Collections.reverseOrder(new MultiCheckGengre.IdComparator()));
//                if (mIsClicked) {
//                    Collections.sort(mGroups.get(0).getVehicles(), Collections.reverseOrder(new Vehicle.IdComparator()));
//                }
//            }
//        } else {
//            mFilterType = Constants.INCREASING;
//            mFilterField = Constants.FIELD_ID;
//            Collections.sort(mGroups, new MultiCheckGengre.IdComparator());
//            if (mIsClicked) {
//                Collections.sort(mGroups.get(0).getVehicles(), new Vehicle.IdComparator());
//            }
//        }
//        notifyDataSetChanged();
//    }


//    public void sortByDays() {
//        if (mFilterField == Constants.FIELD_DAYS) {
//            if (mFilterType == Constants.DECREASING) {
//                mFilterType = Constants.INCREASING;
//                Collections.sort(mGroups, new MultiCheckGengre.DaysComparator());
//                if (mIsClicked) {
//                    Collections.sort(mGroups.get(0).getVehicles(), new Vehicle.DaysComparator());
//                }
//            } else {
//                mFilterType = Constants.DECREASING;
//                Collections.sort(mGroups, Collections.reverseOrder(new MultiCheckGengre.DaysComparator()));
//                if (mIsClicked) {
//                    Collections.sort(mGroups.get(0).getVehicles(), Collections.reverseOrder(new Vehicle.DaysComparator()));
//                }
//            }
//        } else {
//            mFilterType = Constants.INCREASING;
//            mFilterField = Constants.FIELD_DAYS;
//            Collections.sort(mGroups, new MultiCheckGengre.DaysComparator());
//            if (mIsClicked) {
//                Collections.sort(mGroups.get(0).getVehicles(), new Vehicle.DaysComparator());
//            }
//        }
//        notifyDataSetChanged();
//    }

//    public void sortByGeo() {
//        if (mFilterField == Constants.FIELD_GEO) {
//            if (mFilterType == Constants.DECREASING) {
//                mFilterType = Constants.INCREASING;
//                Collections.sort(mGroups, new MultiCheckGengre.GeoComparator());
//                if (mIsClicked) {
//                    Collections.sort(mGroups.get(0).getVehicles(), new Vehicle.GeoComparator());
//                }
//            } else {
//                mFilterType = Constants.DECREASING;
//                Collections.sort(mGroups, Collections.reverseOrder(new MultiCheckGengre.GeoComparator()));
//                if (mIsClicked) {
//                    Collections.sort(mGroups.get(0).getVehicles(), Collections.reverseOrder(new Vehicle.GeoComparator()));
//                }
//            }
//        } else {
//            mFilterType = Constants.INCREASING;
//            mFilterField = Constants.FIELD_GEO;
//            Collections.sort(mGroups, new MultiCheckGengre.GeoComparator());
//            if (mIsClicked) {
//                Collections.sort(mGroups.get(0).getVehicles(), new Vehicle.GeoComparator());
//            }
//        }
//        notifyDataSetChanged();
//    }
//
//    public void sortByWarning() {
//        if (mFilterField == Constants.FIELD_WARNING) {
//            if (mFilterType == Constants.DECREASING) {
//                mFilterType = Constants.INCREASING;
//                Collections.sort(mGroups, new MultiCheckGengre.WarningComparator());
//                if (mIsClicked) {
//                    Collections.sort(mGroups.get(0).getVehicles(), new Vehicle.WarningComparator());
//                }
//            } else {
//                mFilterType = Constants.DECREASING;
//                Collections.sort(mGroups, Collections.reverseOrder(new MultiCheckGengre.WarningComparator()));
//
//                if (mIsClicked) {
//                    Collections.sort(mGroups.get(0).getVehicles(), Collections.reverseOrder(new Vehicle.WarningComparator()));
//                }
//            }
//        } else {
//            mFilterType = Constants.INCREASING;
//            mFilterField = Constants.FIELD_WARNING;
//            Collections.sort(mGroups, new MultiCheckGengre.WarningComparator());
//
//            if (mIsClicked) {
//                Collections.sort(mGroups.get(0).getVehicles(), new Vehicle.WarningComparator());
//            }
//        }
//        notifyDataSetChanged();
//    }


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

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.customthree, parent, false);
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
        final Vehicle vehicle = ((Vehicle) group.getItems().get(childIndex));

        holder.setArtistName(vehicle.getName());

        if (holder.getCheckable().isChecked()) {


            String[] thisIsAStringArray = { ((Vehicle) group.getItems().get(childIndex)).getId() };
            List<String> fixedList = Arrays.asList( thisIsAStringArray );
            List<String> stringList = new ArrayList<String>( fixedList );


            for (int i = 0; i <  fixedList.size(); i++) {

                stringList2.add ( stringList.get(i));

                SharedPreferencesUtils preferencesUtils = new SharedPreferencesUtils(holder.itemView.getContext());
                preferencesUtils.writeList(holder.itemView.getContext(),stringList2,"pref");

//            Toast.makeText(holder.itemView.getContext(), "Odbereni ID: " + stringList2    + "\n", Toast.LENGTH_SHORT).show();
        }








        } else {
            holder.getCheckable().isChecked();

        }

    }

//    @Override
//    public long getItemId(int position) {
//       // return Long.parseLong(mGroups.get(position).getId());
//        return Long.parseLong(mGroups.get(position).getDeviceId());
//    }

    @Override
    public void onBindGroupViewHolder(ParentVehicleViewHolder holder, int flatPosition, ExpandableGroup group) {
//        final ParentVehicle parentVehicle = mGroups.get(flatPosition);
////                ((ParentVehicle) group);
//        holder.bind(parentVehicle, mOnItemClickListener, mIsClicked, mCheck, mTekst);

//        holder.bind(((MultiCheckGengre) group), mOnItemClickListener, mIsClicked,mCheck,mTekst);
        holder.setGenreTitle(group, true);


//        for (int i = 0; i < ; i++) {
//
//        }


    }

    private void onCheckChanged(int position, boolean checked) {

    }


    //endregion

    static class ParentVehicleViewHolder extends GroupViewHolder {
        @BindView(R.id.checkMark12)
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

        public void setGenreTitle(ExpandableGroup genre, boolean showArrow) {

            if (genre instanceof MultiCheckGengre) {
                name.setText((genre).getTitle());

                mGroupArrow.setVisibility(showArrow ? View.VISIBLE : View.GONE);
            }

        }

        public void bind(MultiCheckGengre parentVehicle, GroupsClickListenerSettings groupsClickListener, boolean showArrow, boolean showCheck, boolean chowTekst) {
//            name.setText(parentVehicle.getName());


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
