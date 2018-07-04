package uk.co.intelitrack.Proba;

/**
 * Created by Anti on 6/24/2018.
 */

public class ListViewItemDTO {

    private boolean checked = false;

    private String itemText = "";

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getItemText() {
        return itemText;
    }

    public void setItemText(String itemText) {
        this.itemText = itemText;
    }
}