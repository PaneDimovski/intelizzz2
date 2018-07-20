package uk.co.intelitrack.Proba;

/**
 * Created by Anti on 6/24/2018.
 */

public class Item {

    private boolean checked = false ;

    private String itemText = "";
    private String IdText = "";
    private String IdText2 = "";

    public String getIdText2() {
        return IdText2;
    }

    public void setIdText2(String idText2) {
        IdText2 = idText2;
    }

    public String getIdText() {
        return IdText;
    }

    public void setIdText(String idText) {
        IdText = idText;
    }



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