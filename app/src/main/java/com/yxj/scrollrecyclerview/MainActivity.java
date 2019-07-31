package com.yxj.scrollrecyclerview;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.github.library.BaseRecyclerAdapter;
import com.github.library.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private EditText etNum;
    private Button btnScroll;
    private Button btnAddLast;
    private ScrollRecyclerView recycler;
    private SwipeRefreshLayout swipeRefresh;
    private CheckBox cbFastSlow;
    private CheckBox cbStartEnd;
    private CustomScrollLinearLayoutManager linearLayoutManager;

    private int startIndex = 100;
    private BaseQuickAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etNum = (EditText) findViewById(R.id.et_num);
        btnScroll = (Button) findViewById(R.id.btn_scroll);
        btnAddLast = (Button) findViewById(R.id.btn_add_last);
        recycler = (ScrollRecyclerView) findViewById(R.id.recycler);
        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        cbFastSlow = (CheckBox) findViewById(R.id.cb_fast_slow);
        cbStartEnd = (CheckBox) findViewById(R.id.cb_start_end);

        btnScroll.setOnClickListener(this);
        btnAddLast.setOnClickListener(this);

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefresh.setRefreshing(false);
                        List<String> list = new ArrayList<>();
                        for (int i = 0; i < 10; i++) {
                            list.add("第 " + (++startIndex) + " 个");
                        }
                        adapter.getData().addAll(0,list);
                        adapter.notifyItemRangeInserted(0,list.size());
                        recycler.smoothScrollToPosition(list.size() - 1);
                    }
                },1000);
            }
        });

        List<String> list = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            list.add("第 " + (++startIndex) + " 个");
        }

        linearLayoutManager = new CustomScrollLinearLayoutManager(this);
        linearLayoutManager.setSnapToBounds(true);
        recycler.setLayoutManager(linearLayoutManager);
        recycler.setItemAnimator(new DefaultItemAnimator());
        adapter = new BaseQuickAdapter(list);
        recycler.setAdapter(adapter);

        cbStartEnd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                buttonView.setText(isChecked ? "start" : "end");
                linearLayoutManager.setSnapToBounds(isChecked);
            }
        });

        cbFastSlow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                buttonView.setText(isChecked ? "fast" : "slow");
                if(isChecked){
                    linearLayoutManager.setSpeedFast();
                }else{
                    linearLayoutManager.setSpeedSlow();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_scroll:
                scroll();
                break;
            case R.id.btn_add_last:
                List<String> list = new ArrayList<>();
                list.add("最后一个"+(++startIndex));
                adapter.getData().addAll(0,list);
                adapter.notifyItemInserted(adapter.getData().size());
                recycler.smoothScrollToPosition(adapter.getData().size() - 1);
                break;
        }
    }

    private void scroll() {
        int position = 0;
        try {
            position = Integer.parseInt(etNum.getText().toString());
            if (position < 50 && position > 0) {
                recycler.smoothScrollToPosition(position);
            } else {
                throw new RuntimeException();
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            Toast.makeText(this, "请输入正确的数字", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "请输入50以内的数字", Toast.LENGTH_SHORT).show();
        }
    }

}

