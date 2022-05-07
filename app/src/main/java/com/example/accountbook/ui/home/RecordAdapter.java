package com.example.accountbook.ui.home;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.accountbook.R;

import java.util.List;

public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.MyViewHolder>{
    List<Item> list;
    View inflater;
    Context context;

    public RecordAdapter(Context context,List<Item> list)
    {
        this.context=context;
        this.list=list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        inflater = LayoutInflater.from(parent.getContext()).inflate(R.layout.record_item,parent,false);
        final RecordAdapter.MyViewHolder myViewHolder = new RecordAdapter.MyViewHolder(inflater);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if(position == list.size()) {
            holder.itemView.setVisibility(View.GONE);
            return ;
        }
        final Item item=list.get(position);
        holder.textView_type.setText(item.getType());
        holder.textView_money.setText(item.getMoney());
        holder.textView_e_or_i.setText(item.getExpend_or_income());
        if(item.getExpend_or_income()=="收入")
            holder.textView_money.setTextColor(Color.parseColor("#ff0000"));
        else if(item.getExpend_or_income()=="支出")
            holder.textView_money.setTextColor(Color.parseColor("#000000"));
    }

    @Override
    public int getItemCount() {
        return (list==null?0:(list.size()+1));
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        View labelview;
        TextView textView_type;
        TextView textView_money;
        TextView textView_e_or_i;

        public MyViewHolder(View itemView) {
            super(itemView);
            labelview=itemView;
            textView_type = (TextView) itemView.findViewById(R.id.record_type);
            textView_money=(TextView)itemView.findViewById(R.id.record_money);
            textView_e_or_i=(TextView)itemView.findViewById(R.id.record_e_or_i);

        }
    }
}
