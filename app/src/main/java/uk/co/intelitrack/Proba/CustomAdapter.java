package uk.co.intelitrack.Proba;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import uk.co.intelitrack.intelizzz.R;
import uk.co.intelitrack.intelizzz.common.data.remote.Group;

/**
 * Created by Anti on 6/24/2018.
 */

public class CustomAdapter extends BaseAdapter {

    private Group [] ItemList;
    Item items;
ListenerOdbereno odbrano;
Boolean proverka = false;
    public HashMap<String, String> checked = new HashMap<String, String>();

    private Context ctx;

    public CustomAdapter(Context ctx, Group [] ItemList, ListenerOdbereno odbereno) {
        this.ctx = ctx;
        this.ItemList = ItemList;
        this.odbrano =  odbrano;

//
    }

    public void setCheckedItem(int item) {

        if (checked.containsKey(String.valueOf(item))) {
            checked.remove(String.valueOf(item));
        } else {
            checked.put(String.valueOf(item), String.valueOf(item));
        }
    }

    public HashMap<String, String> getCheckedItems() {
        return checked;
    }


    @Override
    public int getCount() {
        return ItemList.length;
    }

    @Override
    public Object getItem(int position) {
        return ItemList[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public List<Integer> myNumbers() {

        if (items.isChecked()) {

            List<Integer> list = new ArrayList<>();

            list.add(Integer.valueOf(items.getIdText2()));
            return  list;
        }


        return null;

    }


    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        ItemViewHolder viewHolder = null;

        if (convertView != null) {
            viewHolder = (ItemViewHolder) convertView.getTag();
        } else {
            Group items = ItemList[position];
            convertView = View.inflate(ctx, R.layout.custom, null);

            CheckBox checkbox = (CheckBox) convertView.findViewById(R.id.checkMark5);
            TextView listItemText = (TextView) convertView.findViewById(R.id.item_unit_id5);
            TextView tetx = (TextView) convertView.findViewById(R.id.tekst5);
            TextView textId = (TextView) convertView.findViewById(R.id.item_number5);

          //  viewHolder.itemCheckbox = (CheckBox) convertView.findViewById(R.id.checkMark5);

            viewHolder = new ItemViewHolder(convertView);


            viewHolder.setItemCheckbox(checkbox);
            viewHolder.setItemTextView(listItemText);
            viewHolder.setIdTextView(textId);
            viewHolder.setIdTextView2(tetx);

            convertView.setTag(viewHolder);


            viewHolder.itemCheckbox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    odbrano.Cekirano(items, proverka);

                    CheckBox cb = (CheckBox) v ;

                    if (cb.isChecked())
                      proverka = true;
                    else
                      proverka = false;

                }
            });







    }



        Group items = ItemList[position];
        viewHolder.getItemCheckbox().setChecked(proverka);
        viewHolder.getItemTextView().setText(items.getName());
        viewHolder.getIdTextView().setText(items.getId());
        viewHolder.getIdTextView2().setText(items.getId());

        return convertView;
    }
}
