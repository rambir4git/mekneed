package com.myapp.mekvahan.Cart;

import android.content.Context;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.myapp.mekvahan.R;

import java.util.List;

public class AddressPairAdapter extends RecyclerView.Adapter<AddressPairAdapter.AddressViewHolder>{

    private Context mCtx;
    private List<Pair<Integer, String>> mAddressList;
    private final String TAG = getClass().getSimpleName();


    public AddressPairAdapter(Context mCtx, List<Pair<Integer, String>> mAddress) {
        this.mCtx = mCtx;
        this.mAddressList = mAddress;

    }

    @NonNull
    @Override
    public AddressViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new AddressViewHolder(LayoutInflater.from(mCtx).inflate(R.layout.row_address_pair,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull AddressViewHolder holder, int i) {
        final Pair<Integer, String> address = mAddressList.get(i);

        holder.tv_address.setText(address.second);

    }



    @Override
    public int getItemCount() {
        return mAddressList.size();
    }

    public class AddressViewHolder extends RecyclerView.ViewHolder {

        TextView tv_address;
        RadioButton radioButton;

        public AddressViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_address = itemView.findViewById(R.id.address);
            radioButton = itemView.findViewById(R.id.radiobutton);

        }
    }
}
