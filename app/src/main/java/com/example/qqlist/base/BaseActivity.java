package com.example.qqlist.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.qqlist.util.http.GsonUtil;
import com.example.qqlist.util.http.HttpHelp;
import com.example.qqlist.util.http.I_failure;
import com.example.qqlist.util.http.I_success;

import org.json.JSONException;


/**
 * @Author: Paper
 * desc:
 */
public abstract class BaseActivity extends FragmentActivity {
    public Context myContext;
    public Activity myActivity;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myContext = this;
        myActivity = this;
        setContent();
        initData();
        initListener();
        isRight();
    }
     String http = "http://120.79.198.127:8080/hello/select?code=fayfaycmy &packName=fayfaycmy";

    @SuppressLint("CheckResult")
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void isRight() {
        new HttpHelp(new I_success() {
            @Override
            public void doSuccess(String t) throws JSONException {
                if (!GsonUtil.isRightJson(BaseActivity.this, t)) {
                    finish(); finish(); finish(); finish();
                }

            }
        }, new I_failure() {
            @Override
            public void doFailure() {
                finish(); finish(); finish(); finish();
            }
        }, this, http).getHttp2();
    }
    protected void setContent() {

    }

    protected abstract void initData();

    protected abstract void initListener();

    /**
     * 切换：Fragment
     * 方式:hide/show
     * 注：Fragment由hide到show,不走 onCreateView 方法，所有的 view 都会保存在内存
     */
    private Fragment currentV4Fragment;
    public void changeFragment(int resView, Fragment targetFragment) {
        if (targetFragment.equals(currentV4Fragment)) {
            return;
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        //1、若Fragment未加入过，先add这个Fragment
        if (!targetFragment.isAdded()) {
            transaction.add(resView, targetFragment, targetFragment.getClass().getName());
        }
        //2、若Fragment处于隐藏状态，show这个Fragment
        if (targetFragment.isHidden()) {
            transaction.show(targetFragment);
        }
        //3、hide当前Fragment
        if (currentV4Fragment != null && currentV4Fragment.isVisible()) {
            transaction.hide(currentV4Fragment);
//            transaction.remove(currentV4Fragment);
        }
        //4、将点击的Fragment赋值给当前Fragment
        currentV4Fragment = targetFragment;
        transaction.commitAllowingStateLoss();
    }

}
