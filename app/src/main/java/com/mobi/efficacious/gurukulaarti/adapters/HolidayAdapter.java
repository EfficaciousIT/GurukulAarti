package com.mobi.efficacious.gurukulaarti.adapters;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import com.mobi.efficacious.gurukulaarti.R;
import com.mobi.efficacious.gurukulaarti.entity.DashboardDetail;
import com.mobi.efficacious.gurukulaarti.entity.Holiday;


public class HolidayAdapter  extends RecyclerView.Adapter<HolidayAdapter.HolidayListHolder> {

    Context context;
    ArrayList<DashboardDetail> itemsArrayList;

    public HolidayAdapter(Context context, ArrayList<DashboardDetail> itemsArrayList) {
        this.context = context;
        this.itemsArrayList = itemsArrayList;
    }

    @Override
    public HolidayListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.holiday_row, parent, false);
        return new HolidayListHolder(view);
    }

    @Override
    public void onBindViewHolder(final HolidayListHolder holder, final int position) {
        try
        {
            String ExamDate = itemsArrayList.get(position).getDtFromDate();
            String[] separated = ExamDate.split("/");
            String DAte=separated[0];
            String Month=separated[1];
            String Year=separated[2];
            if(Month.contentEquals("01"))
            {
                holder.datetv.setText(Month+" "+"JANUARY"+" "+Year);
            }else if(Month.contentEquals("02"))
            {
                holder.datetv.setText(Month+" "+"FEBRUARY"+" "+Year);
            }else if(Month.contentEquals("03"))
            {
                holder.datetv.setText(Month+" "+"MARCH"+" "+Year);
            }else if(Month.contentEquals("04"))
            {
                holder.datetv.setText(Month+" "+"APRIL"+" "+Year);
            }else if(Month.contentEquals("05"))
            {
                holder.datetv.setText(Month+" "+"MAY"+" "+Year);
            }else if(Month.contentEquals("06"))
            {
                holder.datetv.setText(Month+" "+"JUNE"+" "+Year);
            }else if(Month.contentEquals("07"))
            {
                holder.datetv.setText(Month+" "+"JULY"+" "+Year);
            }else if(Month.contentEquals("08"))
            {
                holder.datetv.setText(Month+" "+"AUGUST"+" "+Year);
            }else if(Month.contentEquals("09"))
            {
                holder.datetv.setText(Month+" "+"SEPTEMBER"+" "+Year);
            }else if(Month.contentEquals("10"))
            {
                holder.datetv.setText(Month+" "+"OCTOBER"+" "+Year);
            }else if(Month.contentEquals("11"))
            {
                holder.datetv.setText(Month+" "+"NOVEMBER"+" "+Year);
            }else if(Month.contentEquals("12"))
            {
                holder.datetv.setText(Month+" "+"DECEMBER"+" "+Year);
            }
            else
            {
                holder.datetv.setText(" ");
            }

            if(position % 7 == 0){
                holder.fab.setBackgroundTintList(context.getResources().getColorStateList(R.color.colorAccentPressed));
                holder.datetv.setTextColor(context.getResources().getColorStateList(R.color.colorAccentPressed));
            }else if(position % 7 == 1){
                holder.fab.setBackgroundTintList(context.getResources().getColorStateList(R.color.darkgreen));
                holder.datetv.setTextColor(context.getResources().getColorStateList(R.color.darkgreen));
            }else if(position % 7 == 2){
                holder.fab.setBackgroundTintList(context.getResources().getColorStateList(R.color.orange1));
                holder.datetv.setTextColor(context.getResources().getColorStateList(R.color.orange1));
            }else if(position % 7 == 3){
                holder.fab.setBackgroundTintList(context.getResources().getColorStateList(R.color.green));
                holder.datetv.setTextColor(context.getResources().getColorStateList(R.color.green));
            }else if(position % 7 == 4){
                holder.fab.setBackgroundTintList(context.getResources().getColorStateList(R.color.red));
                holder.datetv.setTextColor(context.getResources().getColorStateList(R.color.red));
            }else if(position % 7 == 5){
                holder.fab.setBackgroundTintList(context.getResources().getColorStateList(R.color.colorPrimary));
                holder.datetv.setTextColor(context.getResources().getColorStateList(R.color.colorPrimary));
            }else if(position % 7 == 6){
                holder.fab.setBackgroundTintList(context.getResources().getColorStateList(R.color.darkred));
                holder.datetv.setTextColor(context.getResources().getColorStateList(R.color.darkred));
            }
            holder.festivalname.setText(itemsArrayList.get(position).getHoliday());
            holder.from_todate.setText(itemsArrayList.get(position).getDtFromDate()+" - "+itemsArrayList.get(position).getDtToDate());
        }catch (Exception ex)
        {

        }
    }
    @Override
    public int getItemCount() {
        return itemsArrayList.size();
    }

    class HolidayListHolder extends RecyclerView.ViewHolder {

        TextView datetv,festivalname,from_todate;
        FloatingActionButton fab;
        HolidayListHolder(View itemView) {
            super(itemView);
            datetv = (TextView) itemView.findViewById(R.id.datetv);
            festivalname = (TextView) itemView.findViewById(R.id.festivalname);
            from_todate = (TextView) itemView.findViewById(R.id.from_todate);
            fab = (FloatingActionButton) itemView.findViewById(R.id.fab);
        }

    }

}