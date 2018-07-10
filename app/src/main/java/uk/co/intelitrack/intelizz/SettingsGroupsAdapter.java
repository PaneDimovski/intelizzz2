package uk.co.intelitrack.intelizz;

import android.content.Context;
import android.text.TextUtils;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;
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
import uk.co.intelitrack.intelizzz.components.preview.GroupsClickListener;


public class SettingsGroupsAdapter extends ExpandableRecyclerViewAdapter<SettingsGroupsAdapter.ParentVehicleViewHolder, SettingsGroupsAdapter.ChildItemViewHolder> {

    //region final Fields
    private Map<String, Boolean> checkBoxStates = new HashMap<>();
    private final GroupsClickListener mOnItemClickListener;
    private final IntelizzzRepository mRepository;
    private boolean mIsClicked;
    private boolean mCheck;
    private boolean mTekst;
    private int mFilterField;
    private int mFilterType;
    private Context context;
    public SharedPreferencesUtils mSharedPreferencesUtils;

    //endregion

    //region Fields
    private List<ParentVehicle> mGroups = new ArrayList<>();
    SparseBooleanArray itemStateArray= new SparseBooleanArray();
    //endregion

    //region Constructors
    public SettingsGroupsAdapter(List<ParentVehicle> groups, IntelizzzRepository intelizzzRepository,
                                 GroupsClickListener listener, boolean isClicked,boolean isCheck,boolean isTekst,Context context1) {
        super(groups);
        this.mOnItemClickListener = listener;
        this.mRepository = intelizzzRepository;
        this.mGroups = groups;
        this.mIsClicked = isClicked;
        this.mCheck = isCheck;
        this.mTekst = isTekst;
        this.context=context1;

    }
    //endregion
    public void setData(List<ParentVehicle> groups, boolean isClicked,boolean isCheck,boolean isTekst) {
        mGroups.clear();
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

    @Override
    public ChildItemViewHolder onCreateChildViewHolder(ViewGroup child, int viewType) {
        View view = LayoutInflater.from(child.getContext()).inflate(R.layout.customtwo, child, false);
        return new ChildItemViewHolder(view);
    }

    @Override
    public void onBindChildViewHolder(ChildItemViewHolder holder, int position, ExpandableGroup group, int childIndex) {
        final Vehicle vehicle = ((ParentVehicle) group).getItems().get(childIndex);
        holder.bind(vehicle, mOnItemClickListener);
    }

    @Override
    public void onBindGroupViewHolder(ParentVehicleViewHolder holder, int position, ExpandableGroup group) {
        final ParentVehicle parentVehicle = mGroups.get(position);
//                ((ParentVehicle) group);
        holder.bind(((ParentVehicle) group), mOnItemClickListener, mIsClicked,mCheck,mTekst);
        Boolean checkedState = checkBoxStates.get(parentVehicle.getId());
        holder.checkBox.setChecked(checkedState == null ? false : checkedState);
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    final ParentVehicle parentVehicle = mGroups.get(position);
                    if (parentVehicle == null) {
                        return;
                    }
                    checkBoxStates.put(parentVehicle.getId(), isChecked);
//                    String id =   parentVehicle.getId();
                        mSharedPreferencesUtils.setSharedPreferencesString(Constants.OLI_ID,parentVehicle.getId());
                }
            }
        });
    }

    private void onCheckChanged(int position, boolean checked) {

    }

//        holder.checkBox.setOnCheckedChangeListener(null);
//        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked){
//                    holder.checkBox.setChecked(false);
//                    if (parentVehicle.getId()== String.valueOf(holder.getAdapterPosition()));{
//                        String id =   parentVehicle.getId();
//                        mSharedPreferencesUtils.setSharedPreferencesString(Constants.OLI_ID,id);
//                    }
//                }
//            }
//        });

//        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                //                        groupsClickListener.onDelete(mView,getAdapterPosition());
//                if (isChecked==true) {
//                    if (((ParentVehicle) group).getId()== String.valueOf(holder.checkBox.getId())){
//                        String id =  String.valueOf(holder.checkBox.getId());
//                        SharedPreff.addGroupID(id,context);
////                            mSharedPreferencesUtils.setSharedPreferencesString(Constants.ID,id);
//                    } else {
//                        return;
//                    }
//
//                }
//            }
//        });


    //endregion

    static class ParentVehicleViewHolder extends GroupViewHolder {
        @BindView(R.id.checkMark1)
        CheckBox checkBox;
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
            itemView.setOnClickListener(this);
        }


        public void bind(ParentVehicle parentVehicle, GroupsClickListener groupsClickListener, boolean showArrow, boolean showCheck, boolean chowTekst) {
            name.setText(parentVehicle.getName());

//            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                    //                        groupsClickListener.onDelete(mView,getAdapterPosition());
//                    if (isChecked==true) {
//                        if (parentVehicle.getId()== String.valueOf(checkBox.isChecked())){
//                            String id =   parentVehicle.getId();
//                            mSharedPreferencesUtils.setSharedPreferencesString(Constants.OLI_ID,id);
//                        } else {
//                            return;
//                        }
//
//                    }
//                }
//            });
           // name.setTextColor(mView.getContext().getResources().getColor(R.color.light_blue));

            mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (parentVehicle.isCompany()) {
                        groupsClickListener.onCompanyItemClick(parentVehicle.getId());
                    } else if (!parentVehicle.isCompany()){
                        groupsClickListener.onGroupItemClick(parentVehicle.getId());
                    }

//                          else if (v.getId()==mView.getId()){
//                        groupsClickListener.onDelete(v,getAdapterPosition());
//                    } else if (groupsClickListener!=null){
//                        groupsClickListener.onItemClick(v,getAdapterPosition());
//                    }
                }
            });



            mGroupArrow.setVisibility(showArrow ? View.VISIBLE : View.GONE);
            check.setVisibility(showCheck ? View.VISIBLE : View.GONE);
            teksttch.setVisibility(chowTekst ? View.VISIBLE : View.GONE);
        }


    }


    static class ChildItemViewHolder extends ChildViewHolder {

        @BindView(R.id.checkMark1)
        CheckBox checkBox;
        @BindView(R.id.item_unit_id1)
        TextView name;
        private View mView;

        public ChildItemViewHolder(View itemView) {
            super(itemView);
            this.mView = itemView;
            ButterKnife.bind(this, itemView);

        }

        public void bind(Vehicle vehicle, GroupsClickListener onItemClickListener) {
            name.setText(vehicle.getName());


            mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onGroupChildItemClick(vehicle.getId());
                }
            });

        }

    }
}