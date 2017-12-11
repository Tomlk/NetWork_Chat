package com.example.administrator.mychat;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PicturesView extends AppCompatActivity {


    private ImageView picture;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pictures_view);
        picture=(ImageView)findViewById(R.id.picture_Isend);
        picture.setImageBitmap(Chat.bitmap_pictureIsend);
     /*   RelativeLayout addViewLayout=(RelativeLayout)findViewById(R.id.image_sendlayout);
        bt_addimage=(Button)findViewById(R.id.bt_addimage);

                ImageView imageView=new ImageView(PicturesView.this);
                imageView.setImageBitmap(Chat.bitmap_pictureIsend);
                addViewLayout.addView(imageView);


    }*/
    }
  /*  protected void addImageToList() {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("image",R.drawable.batman_icon);
        ImageList.add(map);
    }*/
/*    public class ImageAdapter extends BaseAdapter{

         Context context = null;
        ArrayList<HashMap<String, Object>> ImageList = null;
        int layout;
        String from;
        int to;


        public ImageAdapter(Context context,
                            ArrayList<HashMap<String, Object>> ImageList, int layout,
                            String from, int to) {
            super();
            this.context = context;
            this.ImageList = ImageList;
            this.layout = layout;
            this.from = from;
            this.to = to;
        }

        @Override
        public int getCount() {
            return ImageList.size();
        }

        @Override
        public Object getItem(int arg0) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }
        class ViewHolder {
            public ImageView imageView = null;

        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
                ViewHolder holder = null;
            convertView = LayoutInflater.from(context).inflate(
                    layout, null);
            holder=new ViewHolder();
            holder.imageView = (ImageView) convertView.findViewById(to);
            holder.imageView.setBackgroundResource((Integer) ImageList.get(position).get(from));
            return convertView;
        }
    }*/
}
