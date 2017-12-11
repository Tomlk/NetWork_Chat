package com.example.administrator.mychat;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class activity_friend extends AppCompatActivity {
    private ListView lv;
    private ImageView my_imageview;
    private TextView my_nickname;


    public static String usr_account;
    private List<Map<String,Object>> data;
    private Integer[] imageIDs={
            R.drawable.python,R.drawable.c_plus,R.drawable.java
    };
    private String[] NickNames={
            "python","c_plus","java"
    };
    private String[] User_Account={
            "475207136","333555502","123456"
    };
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);
        findView();
        set_User_info(Login.login_account);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               switch (String.valueOf(position))
               {
                   case "0":usr_account="475207136";
                       break;
                   case "1":usr_account="333555502";
                       break;
                   case "2":usr_account="123456";
                       break;

               }
                Intent intent=new Intent(activity_friend.this,Chat.class);
                startActivity(intent);
              //  startActivity(new Intent(Login.this,Chat.class));

                // Log.i("tag",String.valueOf(position));
                //Toast.makeText(demo_activity.this,String.valueOf(position),Toast.LENGTH_SHORT);
            }
        });
        data=getdata();
        MyAdapter adapter=new MyAdapter(this);
        lv.setAdapter(adapter);

//这里一定要注意，setAdapter的作用是将listview与adapter绑定，而并非是使用这个listview展示出此时adapter中的内容。也就是说，在setAdapter之后，倘若修改adapter中的内容，listview所展示的内容就会改变。


    }

    private  void findView()
    {
        lv=(ListView)findViewById(R.id.lv);
        my_imageview=(ImageView)findViewById(R.id.buddy_top_avatar);
        my_nickname=(TextView)findViewById(R.id.buddy_top_nick);

    }

    private void set_User_info(String account)
    {
        switch (account) {
            case "475207136":
                my_imageview.setImageDrawable(getResources().getDrawable(R.drawable.python));
                my_nickname.setText("python");
                break;
            case "333555502":
                my_imageview.setImageDrawable(getResources().getDrawable(R.drawable.c_plus));
                my_nickname.setText("c_plus");
                break;
            case "123456":
                my_imageview.setImageDrawable(getResources().getDrawable(R.drawable.java));
                my_nickname.setText("java");
                break;
        }

    }

    private List<Map<String,Object>> getdata()
    {
        List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
        for(int i=0;i<3;i++)
        {
            Map<String,Object> map=new HashMap<String, Object>();
            map.put("img",imageIDs[i]);
            map.put("title",  NickNames[i]);
            map.put("info", User_Account[i]);
            list.add(map);
        }
        return list;
    }

    static class ViewHolder
    {
        public ImageView img;
        public TextView title;
        public TextView info;
    }

    public class MyAdapter extends BaseAdapter {
        private LayoutInflater mInflater=null;

        public MyAdapter(Context context) {
// TODO Auto-generated constructor stub
            this.mInflater=LayoutInflater.from(context);  //这里就是确定你listview在哪一个layout里面展示
        }

        @Override
        public int getCount() {
// TODO Auto-generated method stub
            return data.size(); //这个决定你listview有多少个item
        }
        @Override
        public Object getItem(int position) {
// TODO Auto-generated method stub
            return position;
        }
        @Override
        public long getItemId(int position) {
// TODO Auto-generated method stub
            return position;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
// TODO Auto-generated method stub
            ViewHolder holder=null;
            if(convertView==null)
            {
                holder=new ViewHolder();
                convertView=mInflater.inflate(R.layout.list_item,null); //这里确定你listview里面每一个item的layout
                holder.img = (ImageView)convertView.findViewById(R.id.img); //此处是将内容与控件绑定。

                holder.title = (TextView)convertView.findViewById(R.id.tv);//注意：此处的findVIewById前要加convertView.
                holder.info = (TextView)convertView.findViewById(R.id.info);
                convertView.setTag(holder);
            }
            else{
                holder=(ViewHolder)convertView.getTag(); //这里是为了提高listview的运行效率
            }
            holder.img.setBackgroundResource((Integer)data.get(position).get("img"));
            holder.title.setText((String)data.get(position).get("title"));
            holder.info.setText((String)data.get(position).get("info"));

            return convertView;
        }
    }

}
