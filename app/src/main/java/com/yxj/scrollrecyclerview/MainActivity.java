package com.yxj.scrollrecyclerview;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.github.library.BaseRecyclerAdapter;
import com.github.library.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private EditText etNum;
    private Button btn;
    private ScrollRecyclerView recycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etNum = (EditText) findViewById(R.id.et_num);
        btn = (Button) findViewById(R.id.btn);
        recycler = (ScrollRecyclerView) findViewById(R.id.recycler);

        btn.setOnClickListener(this);

        List<String> list = new ArrayList<>();
        for(int i=0;i<50;i++){
            list.add("第 "+i+" 个");
        }

        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setAdapter(new BaseRecyclerAdapter<String>(this,list,R.layout.item) {
            @Override
            protected void convert(BaseViewHolder helper, String item) {
                helper.setText(R.id.tv,item);
            }
        });

    }

    @Override
    public void onClick(View v) {
        int position = 0;
        try {
            position = Integer.parseInt(etNum.getText().toString());
            if(position<50 && position>0){
                recycler.moveToPosition(position);
            }else{
                throw new RuntimeException();
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            Toast.makeText(this,"请输入正确的数字",Toast.LENGTH_SHORT).show();
        } catch (Exception e){
            Toast.makeText(this,"请输入50以内的数字",Toast.LENGTH_SHORT).show();
        }
    }

}

