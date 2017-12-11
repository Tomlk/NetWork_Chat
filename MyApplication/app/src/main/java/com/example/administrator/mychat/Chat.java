package com.example.administrator.mychat;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.msg.MessageBuilder;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.MsgServiceObserve;
import com.netease.nimlib.sdk.msg.attachment.FileAttachment;
import com.netease.nimlib.sdk.msg.attachment.ImageAttachment;
import com.netease.nimlib.sdk.msg.constant.MsgTypeEnum;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Chat extends AppCompatActivity {

    static  Bitmap bitmap_pictureIsend;
    static Bitmap bitmap_pictureIrev;

    private String PATH;
    public final static int OTHER = 1;
    public final static int ME = 0;
    //***
    ArrayList<HashMap<String, Object>> chatList = null;
    String[] from = {"image", "text"};
    //String[] from2={"image","image"};
    int[] to = {R.id.chatlist_image_me, R.id.chatlist_text_me, R.id.chatlist_image_other, R.id.chatlist_text_other};
    int[] layout = {R.layout.message_isend, R.layout.message_noti};
    protected MyChatAdapter newadapter = null;
    //****发送图片新的适配器
    //
    ArrayList<HashMap<String,Object>> charList2=null;
    String[] from2={"image"};
    int[] to2={R.id.image_here,R.id.image_there};
    int[] layout2={R.layout.image_isend,R.layout.image_noti};
    protected MyChatAdapter2 adapter2=null;



    private EditText sentEdit;
    //private String[] str_array;
    private int j = 0;
    //  private ArrayAdapter<String > adapter;
    private Button send_message;

    //**********加载个人头像和名称
    private ImageView duifang_imageview;
      private ImageView my_imageview;
    private ImageView duifang_imageview2;
    private TextView duifang_nickname;

    //**********加载个人头像和名称
    private ListView listView;

    //*********发送图片处理
    private Button button_checkpicture;
    private Button button_checkpicture2;
    private ImageButton Open_image;

//文件消息类型处理
    private ImageButton open_file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        findView();
        set_User_info(activity_friend.usr_account);
        // getmyimage(Login.login_account);
        Bt_Clicklistener();
        //*******
        chatList = new ArrayList<HashMap<String, Object>>();
        newadapter = new MyChatAdapter(this, chatList, layout, from, to);
        charList2=new ArrayList<HashMap<String ,Object>>();
        adapter2=new MyChatAdapter2(this,chatList,layout2,from2,to2);
        //************

//消息接收观察者
        NIMClient.getService(MsgServiceObserve.class)
                .observeReceiveMessage(incomingMessageObserver, true);
    }

    private void set_User_info(String duifanguser_account) {
        switch (duifanguser_account) {
            case "475207136":
                duifang_imageview.setImageDrawable(getResources().getDrawable(R.drawable.python));
                //   duifang_imageview2.setImageDrawable(getResources().getDrawable(R.drawable.python));
                duifang_nickname.setText("python");
                break;
            case "333555502":
                duifang_imageview.setImageDrawable(getResources().getDrawable(R.drawable.c_plus));
                //  duifang_imageview2.setImageDrawable(getResources().getDrawable(R.drawable.c_plus));
                duifang_nickname.setText("c_plus");
                break;
            case "123456":
                duifang_imageview.setImageDrawable(getResources().getDrawable(R.drawable.java));
                //  duifang_imageview2.setImageDrawable(getResources().getDrawable(R.drawable.java));
                duifang_nickname.setText("java");
                break;

        }
    }

    private void getmyimage(String myaccount) {
        switch (myaccount) {
            case "475207136":
                my_imageview.setImageDrawable(getResources().getDrawable(R.drawable.python));
                break;
            case "333555502":
                my_imageview.setImageDrawable(getResources().getDrawable(R.drawable.c_plus));
                break;
            case "123456":
                my_imageview.setImageDrawable(getResources().getDrawable(R.drawable.java));
                break;

        }
    }


    private void findView() {
        // my_imageview=(ImageView)findViewById(R.id.chatlist_image_me);
        button_checkpicture=(Button)findViewById(R.id.check_pictureIsend);
        button_checkpicture2=(Button)findViewById(R.id.check_pictureIrev) ;
        Open_image=(ImageButton)findViewById(R.id.pictures);
        duifang_nickname = (TextView) findViewById(R.id.duifangnick_name);
        duifang_imageview = (ImageView) findViewById(R.id.duifang_image);
        sentEdit = (EditText) findViewById(R.id.et_input);
        send_message = (Button) findViewById(R.id.ib_send);
        listView = (ListView) findViewById(R.id.lv_chat);
        open_file=(ImageButton)findViewById(R.id.files);
    }

    private void Bt_Clicklistener() {
        send_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendText();
                listView.setAdapter(newadapter);
            }
        });
        Open_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setType("image/*");
               //intent.setAction(Intent.ACTION_GET_CONTENT);
                 intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(intent,1);

            }
        });
        button_checkpicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Chat.this,PicturesView.class));
            }
        });
        button_checkpicture2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Chat.this,PicturesView2.class));
            }
        });
        open_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");//设置类型，我这里是任意类型，任意后缀的可以这样写。
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(Intent.createChooser(intent,"选择一个进行操作"),2);
                listView.setAdapter(newadapter);
            }
        });
    }


//注销
    @Override
    protected void onDestroy() {
        super.onDestroy();
        NIMClient.getService(MsgServiceObserve.class)
                .observeReceiveMessage(incomingMessageObserver, false);
    }

    //接收方
    Observer<List<IMMessage>> incomingMessageObserver =
            new Observer<List<IMMessage>>() {
                @Override
                public void onEvent(List<IMMessage> messages) {

                    for (IMMessage message : messages) {
                        if(message.getMsgType()==MsgTypeEnum.image)
                        {
                            set_image(message);
                            addTextToList("图片消息", OTHER);
                            newadapter.notifyDataSetChanged();
                            listView.setSelection(chatList.size() - 1);

                        }
                        else if(message.getMsgType()==MsgTypeEnum.file)
                        {
                            set_file(message);
                            addTextToList("文件消息", OTHER);
                            newadapter.notifyDataSetChanged();
                            listView.setSelection(chatList.size() - 1);

                        }

                        else {
                            addTextToList(message.getContent(), OTHER);
                            newadapter.notifyDataSetChanged();
                            listView.setSelection(chatList.size() - 1);
                        }
                       /*
                        adapter=new ArrayAdapter<String>(Chat.this,android.R.layout.simple_list_item_1,getDataSource(message.getContent()));
                        listView.setAdapter(adapter);
                 */
                    }
                    // 处理新收到的消息，为了上传处理方便，SDK 保证参数 messages 全部来自同一个聊天对象。
                }
            };


    public void sendText() {

        final String content = sentEdit.getText().toString();
        // 创建文本消息
        final IMMessage message = MessageBuilder.createTextMessage(
                activity_friend.usr_account, // 聊天对象的 ID，如果是单聊，为用户帐号，如果是群聊，为群组 ID
                SessionTypeEnum.P2P, // 聊天类型，单聊或群组
                content // 文本内容
        );
        NIMClient.getService(MsgService.class).sendMessage(message, false).setCallback(new RequestCallback<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(Chat.this, "success", Toast.LENGTH_SHORT);
           /*     adapter=new ArrayAdapter<String>(Chat.this,android.R.layout.simple_list_item_1,getDataSource(message.getContent()));
                listView.setAdapter(adapter);*/


                //*********
                sentEdit.setText("");
                addTextToList(content, ME);
                // addTextToList1()
                //更新数据列表！！！！！
                newadapter.notifyDataSetChanged();
                listView.setSelection(chatList.size() - 1);
                //*******88


            }

            @Override
            public void onFailed(int i) {
                Toast.makeText(Chat.this, "failed", Toast.LENGTH_SHORT);
            }

            @Override
            public void onException(Throwable throwable) {

            }
        });//表示为发送消息，TRUE的话为重新发送
    }


    protected void addTextToList(String text, int who) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("person", who);
        map.put("image", who == ME ? R.drawable.image_me : R.drawable.image_other);
        map.put("text", text);
        chatList.add(map);
    }
    protected  void addTextToList2(ImageView image,int who)
    {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("person", who);
        map.put("image", image);
        charList2.add(map);

    }
  /*  protected void addTextToList1(String text, int who,ImageView view) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("person", who);
        map.put("image", who == ME ? R.drawable.python : R.drawable.c_plus);
        map.put("image",view);
       // map.put("text", text);
        chatList.add(map);
    }*/




    private void send_image() {
      /*  Fi
        le file=*/
        File file=new File(PATH);
        // 创建图片消息
        IMMessage message = MessageBuilder.createImageMessage(
                activity_friend.usr_account, // 聊天对象的 ID，如果是单聊，为用户帐号，如果是群聊，为群组 ID
                SessionTypeEnum.P2P, // 聊天类型，单聊或群组
                file, // 图片文件对象
                null// 文件显示名字，如果第三方 APP 不关注，可以为 null
        );
        NIMClient.getService(MsgService.class).sendMessage(message, false);
}
    private void send_file(File thefile)
    {
        IMMessage message=MessageBuilder.createFileMessage(
                activity_friend.usr_account, // 聊天对象的 ID，如果是单聊，为用户帐号，如果是群聊，为群组 ID
                SessionTypeEnum.P2P, // 聊天类型，单聊或群组
                thefile, // 文件对象
                null// 文件显示名字，如果第三方 APP 不关注，可以为 null
        );
        NIMClient.getService(MsgService.class).sendMessage(message, false);
    }



    //聊天ListView的适配器

    private class MyChatAdapter extends BaseAdapter {

        Context context = null;
        ArrayList<HashMap<String, Object>> chatList = null;
        int[] layout;
        String[] from;
        int[] to;


        public MyChatAdapter(Context context,
                             ArrayList<HashMap<String, Object>> chatList, int[] layout,
                             String[] from, int[] to) {
            super();
            this.context = context;
            this.chatList = chatList;
            this.layout = layout;
            this.from = from;
            this.to = to;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return chatList.size();
        }

        @Override
        public Object getItem(int arg0) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        class ViewHolder {
            public ImageView imageView = null;
            public TextView textView = null;

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            ViewHolder holder = null;
            int who = (Integer) chatList.get(position).get("person");

            convertView = LayoutInflater.from(context).inflate(
                    layout[who == ME ? 0 : 1], null);
            holder = new ViewHolder();
            holder.imageView = (ImageView) convertView.findViewById(to[who * 2 + 0]);
            holder.textView = (TextView) convertView.findViewById(to[who * 2 + 1]);


           // holder.imageView2 = (ImageView) convertView.findViewById(to[who * 2 + 1]);


            System.out.println(holder);
            System.out.println("WHYWHYWHYWHYW");
            System.out.println(holder.imageView);
            holder.imageView.setBackgroundResource((Integer) chatList.get(position).get(from[0]));
            holder.textView.setText(chatList.get(position).get(from[1]).toString());
            return convertView;
        }

    }
    private class MyChatAdapter2 extends BaseAdapter {

        Context context = null;
        ArrayList<HashMap<String, Object>> chatList2 = null;
        int[] layout2;
        String[] from2;
        int[] to2;


        public MyChatAdapter2(Context context,
                             ArrayList<HashMap<String, Object>> chatList2, int[] layout,
                             String[] from, int[] to) {
            super();
            this.context = context;
            this.chatList2 = chatList2;
            this.layout2 = layout;
            this.from2 = from;
            this.to2 = to;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return chatList2.size();
        }

        @Override
        public Object getItem(int arg0) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        class ViewHolder {
            public ImageView imageView = null;

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            ViewHolder holder = null;
            int who = (Integer) chatList2.get(position).get("person");

            convertView = LayoutInflater.from(context).inflate(
                    layout[who == ME ? 0 : 1], null);
            holder = new ViewHolder();
            holder.imageView = (ImageView) convertView.findViewById(to2[who * 0 + 0]);



            // holder.imageView2 = (ImageView) convertView.findViewById(to[who * 2 + 1]);


            System.out.println(holder);
            System.out.println("WHYWHYWHYWHYW");
            System.out.println(holder.imageView);
            holder.imageView.setBackgroundResource((Integer) chatList2.get(position).get(from2[0]));
            return convertView;
        }

    }

    private void set_image(IMMessage msg) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        String path = ((ImageAttachment) msg.getAttachment()).getThumbPath();
        if (path == null) {
            Toast.makeText(this, "path为空，接收图片失败", Toast.LENGTH_SHORT).show();
            return;

        }else {
            Bitmap bitmap = BitmapFactory.decodeFile(path, options);
            bitmap_pictureIrev=bitmap;
            // real_image.setImageBitmap(bitmap);
        }//real_image.setImageBitmap(bitmap);
    }
    private void set_file(IMMessage msg)
    {
        String path=((FileAttachment)msg.getAttachment()).getThumbPath();
        if(path==null)
        {
            Toast.makeText(this,"path为空，接收文件失败",Toast.LENGTH_SHORT).show();
            return;
        }
        else
        {
            Toast.makeText(this,"我已接收到文件消息",Toast.LENGTH_SHORT).show();
         //   NIMClient.getService(MsgService.class).saveMessageToLocal(msg,false);
        }
        NIMClient.getService(MsgService.class).downloadAttachment(msg, true);//下载，但格式如何改?
        NIMClient.getService(MsgService.class).saveMessageToLocal(msg,false);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                Uri uri = data.getData(); //获得图片的Uri
                Log.e("uri", uri.toString());
                ContentResolver cr = this.getContentResolver();
                try {
                    Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));

                    //获取图片路径
                    String[] proj = {MediaStore.Images.Media.DATA};
                    Cursor cursor = managedQuery(uri, proj, null, null, null);
//               //获取索引值
                    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    cursor.moveToFirst();

                    //根据索引值获取图片路径
                    PATH = cursor.getString(column_index);
                    addTextToList("图片消息", ME);
                    // addTextToList1()
                    //更新数据列表！！！！！
                    newadapter.notifyDataSetChanged();
                    listView.setSelection(chatList.size() - 1);

                    send_image();//上传到服务器
                    // 将Bitmap设定到ImageView
                    bitmap_pictureIsend = bitmap;

                    //  real_image.setImageBitmap(bitmap);

                } catch (FileNotFoundException e) {
                    Log.e("Exception", e.getMessage(), e);
                }
                super.onActivityResult(requestCode, resultCode, data);
            } else if (requestCode == 2) {    //发送文件
               Uri uri = data.getData();//得到uri
               String[] pathculumn = {MediaStore.MediaColumns.DATA};
                Cursor cursor = managedQuery(uri, pathculumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex=cursor.getColumnIndex(pathculumn[0]);
                /*
                int actual_image_column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);*/
              /*  cursor.moveToFirst();*/
               // String img_path = cursor.getString(actual_image_column_index);
               String path=cursor.getString(columnIndex);
              // String path="/storage/emulated/0/Download/ytt123.txt";
                File file = new File(path);
                send_file(file);
                addTextToList("文件消息", ME);
                newadapter.notifyDataSetChanged();
                listView.setSelection(chatList.size() - 1);
                super.onActivityResult(requestCode, resultCode, data);
                // Toast.makeText(Chat.this, file.toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}