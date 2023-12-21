package com.app.gpsphonelocator_new;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.app.gpsphonelocator_new.adapter.AvatarAdapter;

public class UserRVAdapter extends ListAdapter<UserFriend,UserRVAdapter.ViewHolder> {

    private OnItemClickListener listener;

    public UserRVAdapter() {
        super(DIFF_CALLBACK);
    }
    private static final DiffUtil.ItemCallback<UserFriend> DIFF_CALLBACK = new DiffUtil.ItemCallback<UserFriend>() {
        @Override
        public boolean areItemsTheSame(UserFriend oldItem, UserFriend newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(UserFriend oldItem, UserFriend newItem) {
            // below line is to check the course name, description and course duration.
            return oldItem.getname().equals(newItem.getname()) &&
                    oldItem.getsecurityCode().equals(newItem.getsecurityCode()) &&
                    oldItem.getphoneNumber().equals(newItem.getphoneNumber());
        }
    };

    @NonNull
    @Override
    public UserRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_rv_item, parent, false);
        return new ViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull UserRVAdapter.ViewHolder holder, int position) {
        UserFriend userFriend = getUserAt(position);
        holder.nameTV.setText(userFriend.getname());
        holder.securitycodeTV.setText(userFriend.getsecurityCode());
        holder.phonenumberTV.setText(userFriend.getphoneNumber());
    }
    public UserFriend getUserAt(int position) {
        return getItem(position);
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        // view holder class to create a variable for each view.
        TextView nameTV, securitycodeTV, phonenumberTV;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            nameTV = itemView.findViewById(R.id.idTVName);
            securitycodeTV = itemView.findViewById(R.id.idTVSecurityCode);
            phonenumberTV = itemView.findViewById(R.id.idTVPhoneNumber);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION)
                      getItem(position);
                }
            });
        }
    }
    public interface OnItemClickListener {
        void onItemClick(UserFriend userFriend);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;;
    }

}
