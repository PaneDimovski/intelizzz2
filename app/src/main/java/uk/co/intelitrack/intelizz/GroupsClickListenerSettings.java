package uk.co.intelitrack.intelizz;


public interface GroupsClickListenerSettings {
    void onCompanyItemClick(String id);

    void onGroupItemClick(String groupId);

    void onGroupChildItemClick(String vehicleId);
}
