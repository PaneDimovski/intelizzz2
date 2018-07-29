package uk.co.intelitrack.intelizz;


import android.view.View;

import uk.co.intelitrack.intelizzz.common.data.remote.Vehicle;

public interface GroupsClickListenerSettings {
    void onCompanyItemClick(String id);

    void onGroupItemClick(String groupId);

    void onGroupChildItemClick(Vehicle vehicle);

    void onItemClick(View view, int position);

    void onDelete(View view, int position);
}
