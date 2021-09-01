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
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;



import java.util.ArrayList;

import com.mobi.efficacious.gurukulaarti.Interface.DataService;
import com.mobi.efficacious.gurukulaarti.R;
import com.mobi.efficacious.gurukulaarti.Tab.DailyDiary_Tab;
import com.mobi.efficacious.gurukulaarti.adapters.DailyDiaryAdapter;
import com.mobi.efficacious.gurukulaarti.common.ConnectionDetector;
import com.mobi.efficacious.gurukulaarti.entity.StandardDetail;
import com.mobi.efficacious.gurukulaarti.entity.StandardDetailsPojo;
import com.mobi.efficacious.gurukulaarti.webApi.RetrofitInstance;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class Homework_RejectedList extends Fragment {
    View myview;
    RecyclerView DairyListview;
    DailyDiaryAdapter adapter;
    ConnectionDetector cd;
    private static final String PREFRENCES_NAME = "myprefrences";
    SharedPreferences settings;
    String userid, role_id, value, Academic_id, Schooli_id, usertype;
    SearchView searchView;
    private ProgressDialog progress;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myview = inflater.inflate(R.layout.fragment_leavelist, null);
        settings = getActivity().getSharedPreferences(PREFRENCES_NAME, Context.MODE_PRIVATE);
        DairyListview = (RecyclerView) myview.findViewById(R.id.leavelist_list);
        cd = new ConnectionDetector(getContext().getApplicationContext());
        role_id = settings.getString("TAG_USERTYPEID", "");
        Academic_id = settings.getString("TAG_ACADEMIC_ID", "");
        Schooli_id = settings.getString("TAG_SCHOOL_ID", "");
        searchView = (SearchView) myview.findViewById(R.id.search_view_teachername);
        searchView.setVisibility(View.GONE);
        progress = new ProgressDialog(getActivity());
        progress.setCancelable(false);
        progress.setCanceledOnTouchOutside(false);
        progress.setMessage("loading...");
        progress.show();
        if (role_id.contentEquals("5")) {
            if (!cd.isConnectingToInternet()) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                alert.setMessage("No Internet Connection");
                alert.setPositiveButton("OK", null);
                alert.show();
            } else {
                try {
                    searchView.setVisibility(View.VISIBLE);
                    userid = settings.getString("TAG_USERID", "");
                    value = DailyDiary_Tab.value;
                    usertype = "Admin";
                    DailyDiaryAsync ();
                } catch (Exception ex) {

                }

            }
        }
        if (role_id.contentEquals("6") || role_id.contentEquals("7")) {
            if (!cd.isConnectingToInternet()) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                alert.setMessage("No Internet Connection");
                alert.setPositiveButton("OK", null);
                alert.show();
            } else {
                try {
                    searchView.setVisibility(View.VISIBLE);
                    userid = settings.getString("TAG_USERID", "");
                    value = DailyDiary_Tab.value;
                    usertype = "Admin";
                    DailyDiaryAsync();
                } catch (Exception ex) {

                }

            }
        }
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return true;
            }
        });
        return myview;
    }
    private void setupSearchView() {
        searchView.setIconifiedByDefault(false);
        searchView.setSubmitButtonEnabled(true);
        searchView.setQueryHint("Search by Teacher Name ");
    }
    public void  DailyDiaryAsync (){
        try {
            String command;
            DataService service = RetrofitInstance.getRetrofitInstance().create(DataService.class);
            if (role_id.contentEquals("6") || role_id.contentEquals("7")) {
                command="DailyDairyPrincipalRejected";
            }else
            {
                command="DailyDairyAdminRejected";
            }
            Observable<StandardDetailsPojo> call = service.getStandardDetails(command,Schooli_id,Academic_id,"","",userid,value);
            call.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<StandardDetailsPojo>() {
                @Override
                public void onSubscribe(Disposable disposable) {
                    progress.show();
                }

                @Override
                public void onNext(StandardDetailsPojo body) {
                    try {
                        generateDiaryHomeworkList((ArrayList<StandardDetail>) body.getStandardDetails());
                    } catch (Exception ex) {
                        progress.dismiss();
                        Toast.makeText(getActivity(), "Response taking time seems Network issue! onNext ", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onError(Throwable t) {
                    progress.dismiss();
                    Toast.makeText(getActivity(), "Response taking time seems Network issue! onError", Toast.LENGTH_SHORT).show();

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

    public void generateDiaryHomeworkList(ArrayList<StandardDetail> taskListDataList) {
        try {
            if ((taskListDataList != null && !taskListDataList.isEmpty())) {
                adapter = new DailyDiaryAdapter(getActivity(),taskListDataList, value,usertype);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                DairyListview.setLayoutManager(layoutManager);
                DairyListview.setAdapter(adapter);
                setupSearchView();
            } else {
                Toast toast = Toast.makeText(getActivity(),
                        "No Data Available",
                        Toast.LENGTH_SHORT);
                View toastView = toast.getView();
                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                toastView.setBackgroundResource(R.drawable.no_data_available);
                toast.show();
            }

        } catch (Exception ex) {
            progress.dismiss();
            Toast.makeText(getActivity(), "Response taking time seems Network issue ex !", Toast.LENGTH_SHORT).show();
        }
    }
}
