package uk.co.intelitrack.Proba;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

import uk.co.intelitrack.intelizzz.R;

/**
 * Created by Anti on 6/24/2018.
 */

public class CustomAdapter extends BaseAdapter {

    private Context activity;
    private List<Item> data;
    private static LayoutInflater inflater = null;
    private View vi;
    private ViewHolder viewHolder;

    public CustomAdapter(Context context, List  <Item> items) {
        this.activity = context;
        this.data = items;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        vi = view;
        //Populate the Listview
        final int pos = position;
        Item items = data.get(pos);
        if(view == null) {
            vi = inflater.inflate(R.layout.custom, null);
            viewHolder = new ViewHolder();
            viewHolder.checkBox = (CheckBox) vi.findViewById(R.id.checkMark5);
            viewHolder.name = (TextView) vi.findViewById(R.id.item_unit_id5);
            viewHolder.id = (TextView) vi.findViewById(R.id.item_number5);
            vi.setTag(viewHolder);
        }else
            viewHolder = (ViewHolder) view.getTag();

        viewHolder.name.setText(items.getName());
        viewHolder.id.setText(items.getId());
        if(items.isCheckbox()){
            viewHolder.checkBox.setChecked(true);
        }
        else {
            viewHolder.checkBox.setChecked(false);
        }
        return vi;
    }
    public List<Item> getAllData(){
        return data;
    }
    public void setCheckBox(int position){
        //Update status of checkbox
        Item items = data.get(position);
        items.setCheckbox(!items.isCheckbox());
        notifyDataSetChanged();
    }

    public class ViewHolder{
        TextView name;
        TextView id;
        CheckBox checkBox;
    }
}