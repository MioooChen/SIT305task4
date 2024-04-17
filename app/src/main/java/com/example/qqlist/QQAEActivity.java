package com.example.qqlist;

import android.app.Activity;
import android.content.ContentValues;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.qqlist.bean.EventBus_Tag;
import com.example.qqlist.bean.QQBean;
import com.example.qqlist.util.DateUtil;
import com.example.qqlist.util.StrUtil;
import com.example.qqlist.util.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.litepal.crud.DataSupport;

public class  QQAEActivity extends Activity {
    TextView tv_commit, tv_cannal, tv_title;
    EditText et_phone, et_teach,et_data;
     int stype = 0;//0添加，1修改
    QQBean bean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qq_ae);

        stype = getIntent().getIntExtra("stype", 0);
        if (stype == 1) {
            bean = (QQBean) getIntent().getSerializableExtra("bean");
        }

        //init view
        et_phone = findViewById(R.id.et_phone);
        et_teach = findViewById(R.id.et_teach);
        tv_commit = findViewById(R.id.tv_commit);
        tv_cannal = findViewById(R.id.tv_cannal);
        tv_title = findViewById(R.id.tv_title);
        et_data= findViewById(R.id.et_data);

        //set view
        if (stype == 0) {
            tv_title.setText("Add Task");
        } else {
            tv_title.setText("Update Task");
            et_phone.setText(bean.getName());
            et_teach.setText(bean.getContents());
            et_data.setText(bean.dats);


        }
        //取消
        tv_cannal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //确定
        tv_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tempPhone = et_phone.getText().toString().trim();
                String tempPwd = et_teach.getText().toString().trim();
                String dats = et_data.getText().toString().trim();


                if (StrUtil.isEmpty(tempPhone) || StrUtil.isEmpty(tempPwd)|| StrUtil.isEmpty(dats)) {
                    ToastUtil.showToast(QQAEActivity.this, "Empty Input");
                    return;
                }



                if (stype == 0) {//add
                    QQBean tempBean = new QQBean();
                    tempBean.setName(tempPhone);
                    tempBean.setContents(tempPwd);
                    tempBean.setTimes(DateUtil.getTodayData_3());
                    tempBean.dats=(dats);


                    tempBean.save();
                    if (tempBean.isSaved()) {
                        ToastUtil.showToast(QQAEActivity.this, "Add Success");
                    }
                } else {//updata
                    ContentValues values = new ContentValues();
                    values.put("name", tempPhone);
                    values.put("contents", tempPwd);
                    values.put("dats", dats);



                    DataSupport.updateAll(QQBean.class, values, "times = ?", bean.getTimes());
                    ToastUtil.showToast(QQAEActivity.this, "Updated!");
                }
                EventBus.getDefault().post(new EventBus_Tag(1));
                finish();


            }
        });

    }
}
