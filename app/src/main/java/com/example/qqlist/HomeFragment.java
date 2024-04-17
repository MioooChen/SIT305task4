package com.example.qqlist;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.qqlist.base.BaseFragment;
import com.example.qqlist.base.BaseRecyclerAdapter;
import com.example.qqlist.base.MyRVViewHolder;
import com.example.qqlist.bean.EventBus_Tag;
import com.example.qqlist.bean.QQBean;
import com.example.qqlist.util.StrUtil;
import com.example.qqlist.util.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.litepal.crud.DataSupport;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;


public class  HomeFragment extends BaseFragment {


    @BindView(R.id.lv)
    RecyclerView lv;
    @BindView(R.id.tv_add)
    TextView tv_add;

    @BindView(R.id.tv_sel)
    TextView tv_sel;
    @BindView(R.id.et_name)
    EditText et_name;

    private List<QQBean> itemBeanList = new ArrayList();
    private MyAdapter myAdapter;

    public HomeFragment() {

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_one, container, false);
        ButterKnife.bind(this, view);
        registerEventBus();
        initData();
        return view;
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initData() {
        initAdapter();
        tv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                new QQDialog(getActivity(), 0).showDialog();
                Intent intent = new Intent(getActivity(), QQAEActivity.class);
                intent.putExtra("stype", 0);
                startActivity(intent);

            }
        });
        tv_sel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tempPwd = et_name.getText().toString().trim();
                if (StrUtil.isEmpty(tempPwd)) {
                    ToastUtil.showToast(getActivity(), "Input Not Empty");
                    return;
                }

                itemBeanList.clear();
                List<QQBean> temp = DataSupport.findAll(QQBean.class);//查询表Comment
                for (int i = 0; i < temp.size(); i++) {
                    if (temp.get(i).getName().contains(tempPwd)||
                            temp.get(i).getContents().contains(tempPwd)

                    ) {
                        itemBeanList.add(temp.get(i));
                    }
                }
                /**按照时间戳排序 小-大**/
                Collections.sort(itemBeanList, new Comparator<QQBean>() {
                    public int compare(QQBean o1, QQBean o2) {
                        String s1 = o1.dats;
                        String s2 = o2.dats;
                        return Collator.getInstance(Locale.CHINESE).compare(s1, s2);
                    }
                });
                myAdapter.notifyDataSetChanged();


            }
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initAdapter() {
        //init listview
        itemBeanList.clear();
        List<QQBean> temp = DataSupport.findAll(QQBean.class);//查询表Comment
        itemBeanList.addAll(temp);
        /**按照时间戳排序 小-大**/
        Collections.sort(itemBeanList, new Comparator<QQBean>() {
            public int compare(QQBean o1, QQBean o2) {
                String s1 = o1.dats;
                String s2 = o2.dats;
                return Collator.getInstance(Locale.CHINESE).compare(s1, s2);
            }
        });
        @SuppressLint("WrongConstant")
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        if (null == manager)
            return;
        lv.setLayoutManager(manager);
        myAdapter = new MyAdapter(getActivity(), itemBeanList, R.layout.item_meal);
        lv.setAdapter(myAdapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    class MyAdapter extends BaseRecyclerAdapter<QQBean> {

        private TextView tv_name, tv_content, tv_up, tv_del, tv_time;
        private int selPosi;

        public void setSelPosi(int selPosi) {
            this.selPosi = selPosi;
        }

        public MyAdapter(Context context, List<QQBean> datas, int layoutId) {
            super(context, datas, layoutId);
        }

        @Override
        public void setView(MyRVViewHolder holder, final QQBean bean, int position) {
            if (null == holder || null == bean)
                return;
            //init view
            tv_del = holder.getView(R.id.tv_del);
            tv_up = holder.getView(R.id.tv_up);
            tv_name = holder.getView(R.id.tv_name);
            tv_content = holder.getView(R.id.tv_content);
            tv_time = holder.getView(R.id.tv_time);
            //set view
            tv_name.setText(bean.getName());
            tv_content.setText(
                    "Task:" + bean.getContents()+"\nDue Date:" + bean.dats

            );
            tv_time.setText(bean.getTimes());
            tv_del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DataSupport.deleteAll(QQBean.class, "times = ? ", bean.getTimes());
                    ToastUtil.showToast(getActivity(), "Deleted");
                    EventBus.getDefault().post(new EventBus_Tag(1));
                }
            });
            tv_up.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    new QQDialog(getActivity(), 1,bean).showDialog();
                    Intent intent = new Intent(getActivity(), QQAEActivity.class);
                    intent.putExtra("stype", 1);
                    intent.putExtra("bean", bean);
                    startActivity(intent);
                }
            });
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(EventBus_Tag event) {
        switch (event.getTag()) {
            case 1:
                itemBeanList.clear();
                List<QQBean> temp = DataSupport.findAll(QQBean.class);//查询表Comment
                itemBeanList.addAll(temp);
                /**按照时间戳排序 小-大**/
                Collections.sort(itemBeanList, new Comparator<QQBean>() {
                    public int compare(QQBean o1, QQBean o2) {
                        String s1 = o1.dats;
                        String s2 = o2.dats;
                        return Collator.getInstance(Locale.CHINESE).compare(s1, s2);
                    }
                });
                myAdapter.notifyDataSetChanged();
                break;
        }
    }

    public void registerEventBus() {
        EventBus.getDefault().unregister(this);
        EventBus.getDefault().register(this);
    }


}
