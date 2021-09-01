package com.mobi.efficacious.gurukulaarti.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;



import java.util.ArrayList;

import com.mobi.efficacious.gurukulaarti.Interface.DataService;
import com.mobi.efficacious.gurukulaarti.R;
import com.mobi.efficacious.gurukulaarti.adapters.StudentLeaveListAdapter;
import com.mobi.efficacious.gurukulaarti.adapters.TeacherLeaveListAdapter;
import com.mobi.efficacious.gurukulaarti.common.ConnectionDetector;
import com.mobi.efficacious.gurukulaarti.entity.LeaveDetail;
import com.mobi.efficacious.gurukulaarti.entity.LeaveDetailPojo;
import com.mobi.efficacious.gurukulaarti.webApi.RetrofitInstance;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class Student_LeaveList  extends Fragment {
    View myview;
    private static final String PREFRENCES_NAME = "myprefrences";
    SharedPreferences settings;
    String user_id;
    String UserType_id;
    String School_id;
    String Year_id;
    ConnectionDetector cd;
    RecyclerView recyclerView;
    private ProgressDialog progress;
    StudentLeaveListAdapter madapter;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myview = inflater.inflate(R.layout.teacher_leave, null);
        settings = getActivity().getSharedPreferences(PREFRENCES_NAME, Context.MODE_PRIVATE);
        cd = new ConnectionDetector(getActivity());
        recyclerView = (RecyclerView) myview.findViewById(R.id.teacherapproval_list);
        user_id = settings.getString("TAG_USERID", "");
        UserType_id = settings.getString("TAG_USERTYPEID", "");
        Year_id = settings.getString("TAG_ACADEMIC_ID", "");
        School_id = settings.getString("TAG_SCHOOL_ID", "");
        progress = new ProgressDialog(getActivity());
        progress.setCancelable(false);
        progress.setCanceledOnTouchOutside(false);
        progress.setMessage("loading...");
        progress.show();
        if (!cd.isConnectingToInternet()) {

            AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
            alert.setMessage("No InternetConnection");
            alert.setPositiveButton("OK", null);
            alert.show();

        } else {
            try {
                AdminAsync();
            } catch (Exception ex) {

            }

        }
        return myview;
    }
    public void  AdminAsync (){
        try {
            String command;
            if (UserType_id.contentEquals("6") || UserType_id.contentEquals("7")) {
                command="SelectStudentLeaveListBYPrincipal";
            } else {
                command="SelectStudentLeaveList";
            }
            DataService service = RetrofitInstance.getRetrofitInstance().create(DataService.class);
            Observable<LeaveDetailPojo> call = service.getLeaveDetailDetails(command,Year_id,UserType_id,user_id,School_id,"");
            call.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<LeaveDetailPojo>() {
                @Override
                public void onSubscribe(Disposable disposable) {
                    progress.show();
                }

                @Override
                public void onNext(LeaveDetailPojo body) {
                    try {
                        generateLeaveList((ArrayList<LeaveDetail>) body.getLeaveDetail());
                    } catch (Exception ex) {
                        progress.dismiss();
                        Toast.makeText(getActivity(), "Response taking time seems Network issue!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onError(Throwable t) {
                    progress.dismiss();
                    Toast.makeText(getActivity(), "Response taking time seems Network issue!", Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onComplete() {
                    progress.dismiss();

                }
            });
        } catch (Exception ex) {
            progress.dismiss();
        }
    }

    public void generateLeaveList(ArrayList<LeaveDetail> taskListDataList) {
        try {
            if ((taskListDataList != null && !taskListDataList.isEmpty())) {
                madapter = new StudentLeaveListAdapter(taskListDataList,getActivity());
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(madapter);

            } else {

            }

        } catch (Exception ex) {
            progress.dismiss();
            Toast.makeText(getActivity(), "Response taking time seems Network issue!", Toast.LENGTH_SHORT).show();
        }
    }
}