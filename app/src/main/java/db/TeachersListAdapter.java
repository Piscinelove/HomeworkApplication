package db;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.audreycelia.homeworkapp.R;

import java.util.ArrayList;

/**
 * Created by Rafael Peixoto on 22.04.2017.
 */

public class TeachersListAdapter extends BaseAdapter {
    private ArrayList<Teacher> listData;
    private LayoutInflater layoutInflater;

    public TeachersListAdapter(Context aContext, ArrayList<Teacher> listData) {
        this.listData = listData;
        layoutInflater = LayoutInflater.from(aContext);
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.row_teacher, null);
            holder = new ViewHolder();
            holder.teacherName = (TextView) convertView.findViewById(R.id.tv_teacher_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.teacherName.setText(listData.get(position).getFirstName()+" " + listData.get(position).getLastName());
        return convertView;
    }

    static class ViewHolder {
        TextView teacherName;
    }
}
