package com.cssoftwaretech.samvad;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class SignUpSchoolListAdapter extends RecyclerView.Adapter<SignUpSchoolListAdapter.ViewHolder> {
    private static int selectedItem = -1;
    private List<SchoolItemInfo> listItems;
    private Context context;
    private OnItemClickListener listener;

    public SignUpSchoolListAdapter(List<SchoolItemInfo> listItems, Context context) {
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
    public SignUpSchoolListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.school_info_itemview, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(SignUpSchoolListAdapter.ViewHolder holder, int position) {
        final SchoolItemInfo listItem = listItems.get(position);
        holder.tvSchId.setText(listItem.getSchId());
        holder.tvSchName.setText(listItem.getSchName());
        holder.tvSchContact.setText(listItem.getSchContact());
        holder.tvSchEmail.setText(listItem.getSchEmail());
        holder.tvSchWebsite.setText(listItem.getSchWebsite());
        holder.tvSchAddress.setText(listItem.getSchAddress());
        //holder.tvImgText.setText(listItem.getStdStatus());
        holder.imgImage.setImageResource(R.drawable.ic_attendance);
    }


    public interface OnItemClickListener {
        void onItemClick(SchoolItemInfo item);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvSchId, tvSchName, tvSchContact, tvSchAddress, tvSchEmail, tvSchWebsite, tvImgText;
        private ImageView imgImage;

        public ViewHolder(View itemView) {
            super(itemView);
            tvSchId = (TextView) itemView.findViewById(R.id.tvSSL_idNo);
            tvSchName = (TextView) itemView.findViewById(R.id.tvSSL_name);
            tvSchContact = (TextView) itemView.findViewById(R.id.tvSSL_contact);
            tvSchEmail = (TextView) itemView.findViewById(R.id.tvSSL_email);
            tvSchWebsite = (TextView) itemView.findViewById(R.id.tvSSL_website);
            tvSchAddress = (TextView) itemView.findViewById(R.id.tvSSL_address);
            //Name Image or Text
            imgImage = (ImageView) itemView.findViewById(R.id.imgSSL_stdImg);
            tvImgText = (TextView) itemView.findViewById(R.id.imgSSL_stdText);
        }

        public void bind(final SchoolItemInfo item, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }


}

class SchoolItemInfo {
    String schId, schName, schContact, schEmail, schWebsite ,schAddress;

    public SchoolItemInfo(String schId, String schName, String schContact, String schEmail, String schWebsite, String schAddress) {
        this.schId = schId;
        this.schName = schName;
        this.schContact = schContact;
        this.schEmail = schEmail;
        this.schWebsite = schWebsite;
        this.schAddress = schAddress;
    }

    public String getSchId() {
        return schId;
    }

    public String getSchName() {
        return schName;
    }

    public String getSchContact() {
        return schContact;
    }

    public String getSchAddress() {
        return schAddress;
    }

    public String getSchEmail() {
        return schEmail;
    }

    public String getSchWebsite() {
        return schWebsite;
    }
}