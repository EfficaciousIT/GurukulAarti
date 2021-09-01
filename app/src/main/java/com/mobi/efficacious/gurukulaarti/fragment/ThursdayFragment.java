package com.mobi.efficacious.gurukulaarti.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
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
import com.mobi.efficacious.gurukulaarti.Tab.TimetableActivity_student;
import com.mobi.efficacious.gurukulaarti.adapters.StandardAdapter;
import com.mobi.efficacious.gurukulaarti.adapters.StudentTimetableAdapter;
import com.mobi.efficacious.gurukulaarti.entity.TimeTableDetail;
import com.mobi.efficacious.gurukulaarti.entity.TimeTableDetailPojo;
import com.mobi.efficacious.gurukulaarti.webApi.RetrofitInstance;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


public class ThursdayFragment extends Fragment {
    String Std_id, school_id;
    RecyclerView mrecyclerView;
    RecyclerView.Adapter madapter;
    private CompositeDisposable mCompositeDisposable;
    private ProgressDialog progress;
    private static final String PREFRENCES_NAME = "myprefrences";
    SharedPreferences settings;
    String Academic_id, role_id;

    public ThursdayFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mrecyclerView = (RecyclerView) getActivity().findViewById(R.id.thursday_list);
        settings = getActivity().getSharedPreferences(PREFRENCES_NAME, Context.MODE_PRIVATE);
        Academic_id = settings.getString("TAG_ACADEMIC_ID", "");
        role_id = settings.getString("TAG_USERTYPEID", "");
        try {
            if (role_id.contentEquals("6") || role_id.contentEquals("7") || role_id.contentEquals("3")) {
                school_id = StandardAdapter.intSchool_id;
            } else {
                school_id = settings.getString("TAG_SCHOOL_ID", "");
            }
            Std_id = TimetableActivity_student.stdid;
        } catch (Exception ex) {

        }



        try {
            thrAsync ();
        } catch (Exception ex) {

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_thursday, container, false);
    }

    public void  thrAsync (){
        try {
            DataService service = RetrofitInstance.getRetrofitInstance().create(DataService.class);
            mCompositeDisposable.add(service.getTimeTableDetails("selectStudentTT",school_id,"thursday",Academic_id,Std_id,"")
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribeWith(getObserver()));
        } catch (Exception ex) {
        }
    }
    public DisposableObserver<TimeTableDetailPojo> getObserver(){
        return new DisposableObserver<TimeTableDetailPojo>() {

            @Override
            public void onNext(@NonNull TimeTableDetailPojo timeTableDetailPojo) {
                try {
                    generateTimetableList((ArrayList<TimeTableDetail>) timeTableDetailPojo.getTimeTableDetail());
                } catch (Exception ex) {
                    Toast.makeText(getActivity(), "Response taking time seems Network issue!", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onError(@NonNull Throwable e) {
                Toast.makeText(getActivity(), "Response taking time seems Network issue!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete() {
            }
        };
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mCompositeDisposable != null && !mCompositeDisposable.isDisposed()) {
            mCompositeDisposable.dispose();
        }
    }


    public void generateTimetableList(ArrayList<TimeTableDetail> taskListDataList) {
        try {
            if ((taskListDataList != null && !taskListDataList.isEmpty())) {
                madapter = new StudentTimetableAdapter(getActivity(),taskListDataList,"Student");

                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());

                mrecyclerView.setLayoutManager(layoutManager);

                mrecyclerView.setAdapter(madapter);
            } else {


            }

        } catch (Exception ex) {
            Toast.makeText(getActivity(), "Response taking time seems Network issue!", Toast.LENGTH_SHORT).show();
        }
    }
}