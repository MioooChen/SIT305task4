package com.example.qqlist;

import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.Fragment;


import com.example.qqlist.base.BaseActivity;
import com.example.qqlist.bean.EventBus_Tag;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;

public class    MainActivity extends BaseActivity {



    public Fragment getoFragment() {
//        if (null == oFragment) {
            oFragment = new HomeFragment();
//        }
        return oFragment;
    }



    private Fragment oFragment, tFragment;

    @Override
    protected void setContent() {
        super.setContent();
        setContentView(R.layout.activity_main);


 
    }

    @Override
    protected void initData() {
        DisplayFragment(0);
    }

    @Override
    protected void initListener() {

    }

    public void DisplayFragment(int position) {
//        myAdapter.setSelPosi(position);
//        myAdapter.notifyDataSetChanged();
        switch (position) {

            case 0:
                changeFragment(R.id.fragment, getoFragment());
                break;



        }
    }



}
