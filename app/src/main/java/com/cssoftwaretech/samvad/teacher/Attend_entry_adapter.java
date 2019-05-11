package com.cssoftwaretech.samvad.teacher;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;


import com.cssoftwaretech.samvad.R;

import java.util.List;

public class Attend_entry_adapter extends RecyclerView.Adapter<Attend_entry_adapter.ViewHolder> {
    private static int selectedItem = -1;
    private List<AttendItemInfo> listItems;
    private Context context;
    private OnItemClickListener listener;

    public Attend_entry_adapter(List<AttendItemInfo> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
        listener = null;
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public void setSelectedItem(int position) {
        selectedItem = position;
    }

    @Override
    public Attend_entry_adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.teach_actv_attendance_entry_itemview, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(Attend_entry_adapter.ViewHolder holder, int position) {
        final AttendItemInfo listItem = listItems.get(position);
        holder.tvStdName.setText(listItem.getStdName());
        holder.tvStdClass.setText(listItem.getStdClass());
        holder.tvStdEnrollNo.setText(listItem.getStdEnrollNo());
        //holder.tvImgText.setText(listItem.getStdStatus());
        holder.imgImage.setImageResource(R.drawable.ic_person);
        switch (listItem.getStdStatus()) {
            case "P":
                holder.rbtStatusP.setChecked(true);
                break;
            case "A":
                holder.rbtStatusA.setChecked(true);
                break;
            case "L":
                holder.rbtStatusL.setChecked(true);
                break;
        }
    }


    public interface OnItemClickListener {
        void onItemClick(AttendItemInfo item);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvStdName, tvStdClass, tvStdEnrollNo, tvImgText;
        private RadioButton rbtStatusP, rbtStatusA, rbtStatusL;
        private ImageView imgImage;

        public ViewHolder(View itemView) {
            super(itemView);
            tvStdName = (TextView) itemView.findViewById(R.id.tvAEV_name);
            tvStdClass = (TextView) itemView.findViewById(R.id.tvAEV_class);
            tvStdEnrollNo = (TextView) itemView.findViewById(R.id.tvAEV_EnrollNo);
            //Name Image or Text
            imgImage = (ImageView) itemView.findViewById(R.id.imgAEV_stdImg);
            tvImgText = (TextView) itemView.findViewById(R.id.imgAEV_stdText);

            rbtStatusP = (RadioButton) itemView.findViewById(R.id.rbt_attend_present);
            rbtStatusA = (RadioButton) itemView.findViewById(R.id.rbt_attend_absent);
            rbtStatusL = (RadioButton) itemView.findViewById(R.id.rbt_attend_leav);

        }

        public void bind(final AttendItemInfo item, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }


}
class AttendItemInfo {

    String stdName, stdClass, stdEnrollNo, stdStatus;

    public AttendItemInfo(String stdName, String stdClass, String stdEnrollNo, String stdStatus) {
        this.stdName = stdName;
        this.stdClass = stdClass;
        this.stdEnrollNo = stdEnrollNo;
        this.stdStatus = stdStatus;
    }

    public String getStdName() {
        return stdName;
    }

    public String getStdClass() {
        return stdClass;
    }

    public String getStdEnrollNo() {
        return stdEnrollNo;
    }

    public String getStdStatus() {
        return stdStatus;
    }
}
