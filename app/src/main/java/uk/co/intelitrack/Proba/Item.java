package uk.co.intelitrack.Proba;

/**
 * Created by Anti on 6/24/2018.
 */

public class Item {

    String name;
    String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    boolean checkbox;

    public Item() {
         /*Empty Constructor*/
    }
    public  Item(String country, boolean status){
        this.name = country;
        this.checkbox = status;
    }
    //Getter and Setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCheckbox() {
        return checkbox;
    }

    public void setCheckbox(boolean checkbox) {
        this.checkbox = checkbox;
    }

}