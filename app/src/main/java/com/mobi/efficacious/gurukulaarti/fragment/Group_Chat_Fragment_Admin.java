package com.mobi.efficacious.gurukulaarti.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.mobi.efficacious.gurukulaarti.Interface.DataService;
import com.mobi.efficacious.gurukulaarti.R;
import com.mobi.efficacious.gurukulaarti.activity.Gmail_login;
import com.mobi.efficacious.gurukulaarti.activity.IndividualChat;
import com.mobi.efficacious.gurukulaarti.activity.MainActivity;
import com.mobi.efficacious.gurukulaarti.adapters.ChatAllUser_Adapter;
import com.mobi.efficacious.gurukulaarti.common.ConnectionDetector;
import com.mobi.efficacious.gurukulaarti.entity.LoginDetail;
import com.mobi.efficacious.gurukulaarti.webApi.RetrofitInstance;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class Group_Chat_Fragment_Admin extends Fragment {
    View myview;
    private static final String PREFRENCES_NAME = "myprefrences";
    SharedPreferences settings;
    String academic_id,Academic_id,Schooli_id,role_id, Standard_id="", Division_id="", userid="";
    ConnectionDetector cd;
    private ProgressDialog progress;
    TextView allteacher_tv,allstudent_tv,allstaff_tv;
    String Selected_command="";
    LinearLayout allteacher_linear,allstudent_linear,allstaff_linear;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myview = inflater.inflate(R.layout.group_chat_fragment_admin, null);
        cd = new ConnectionDetector(getActivity());
        allteacher_linear=(LinearLayout) myview.findViewById(R.id.allteacher_linear);
        allstudent_linear=(LinearLayout)myview.findViewById(R.id.allstudent_linear);
        allstaff_linear=(LinearLayout)myview.findViewById(R.id.allstaff_linear);

        settings = getActivity().getSharedPreferences(PREFRENCES_NAME, Context.MODE_PRIVATE);
        academic_id = settings.getString("TAG_ACADEMIC_ID", "");
        Academic_id= settings.getString("TAG_ACADEMIC_ID", "");
        Schooli_id= settings.getString("TAG_SCHOOL_ID", "");
        role_id = settings.getString("TAG_USERTYPEID", "");
        userid = settings.getString("TAG_USERID", "");


        allstaff_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Selected_command="AllStaff";
                intentmethod();

            }
        });
        allteacher_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Selected_command="AllTeacher";
                intentmethod();
            }
        });
     allstudent_linear.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             Selected_command="AllStudent";
             intentmethod();
         }
     });
        return  myview;
    }
    public void intentmethod()
    {
        Intent intent=new Intent(getActivity(), IndividualChat.class);
        intent.putExtra("ReceiverFCMToken","Notification");
        intent.putExtra("UserSelectedForNotification",Selected_command);
        startActivity(intent);
    }

}
