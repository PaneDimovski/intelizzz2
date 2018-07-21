package uk.co.intelitrack.intelizz;


import android.view.View;

public interface GroupsClickListenerSettings {
    void onCompanyItemClick(String id);

    void onGroupItemClick(String groupId);

    void onGroupChildItemClick(String vehicleId);

    void onItemClick(View view, int position);

    void onDelete(View view, int position);
}
