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
 * Created by audreycelia on 22.04.17.
 */

public class CoursesListAdapter extends BaseAdapter {

        private ArrayList<Course> listData;
        private LayoutInflater layoutInflater;

        public CoursesListAdapter(Context aContext, ArrayList<Course> listData) {
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

            CoursesListAdapter.ViewHolder holder;
            if (convertView == null) {
                convertView = layoutInflater.inflate(R.layout.row_course, null);
                holder = new CoursesListAdapter.ViewHolder();
                holder.date = (TextView) convertView.findViewById(R.id.tv_course_date);
                holder.courseName = (TextView) convertView.findViewById(R.id.tv_course_name);
                convertView.setTag(holder);
            } else {
                holder = (CoursesListAdapter.ViewHolder) convertView.getTag();
            }

            holder.date.setText(listData.get(position).getStart()+" " + listData.get(position).getEnd());
            holder.courseName.setText(listData.get(position).getName());
            return convertView;
        }

static class ViewHolder {
    TextView date;
    TextView courseName;

}
}
