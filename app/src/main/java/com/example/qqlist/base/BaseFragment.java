package com.example.qqlist.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.greenrobot.eventbus.EventBus;


/**
 * Created by SJ o
 */
public abstract class   BaseFragment extends Fragment {
    public String mTag;
    public BaseActivity mActivity;

    public BaseFragment() {
        mTag = this.getClass().getSimpleName();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mActivity = (BaseActivity) getActivity();

        return super.onCreateView(inflater, container, savedInstanceState);
    }


    /**
     * 切换用户，当前view为可见时，刷新相关数据；
     * 注：在子类中重写该方法时，必须同时在子类中重写signisSwitchAccountAndisFirst方法，才有效
     */
    protected void refreshData() {
    }

    /**
     * fragment处理back事件的方法
     * mreturn  true表示要处理，false表示不处理
     */
    public boolean onBackPressed() {
        return false;
    }



    /**
     * fragment intent跳转
     */
    public void skipActivity(Activity aty, Class<?> cls, Bundle extras) {
        Intent intent = new Intent();
        intent.putExtras(extras);
        intent.setClass(aty, cls);
        aty.startActivity(intent);
//        getActivity().overridePendingTransition(R.anim.start_in, R.anim.start_out);
    }

    public void skipActivity(Activity aty, Class<?> cls) {
        Intent intent = new Intent();
        intent.setClass(aty, cls);
        aty.startActivity(intent);
//        getActivity().overridePendingTransition(R.anim.start_in, R.anim.start_out);
    }



    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        //阻止activity保存fragment的状态
        //super.onSaveInstanceState(outState);
    }

    public void registerEventBus() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }
}
