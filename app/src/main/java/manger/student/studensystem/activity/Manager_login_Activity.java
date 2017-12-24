package manger.student.studensystem.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import manger.student.studensystem.R;
import manger.student.studensystem.utils.MydatabaseHelper;

/**
 * 管理员登录界面
 * Created by Darry.r on 2017/12/18.
 */

public class Manager_login_Activity extends AppCompatActivity{

    private EditText manager_name;//管理员用户名
    private EditText manager_pass;//管理员密码
    private Button btn_login;//登录按钮
    private TextView forgetNum;//忘记密码
    private TextView registe;//注册
    private MydatabaseHelper dbHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.manager_login_layout);
        initViews();
        dbHelper=MydatabaseHelper.getInstance(this);

    }

    public  void initViews(){
        manager_name = findViewById(R.id.manager_login_name_input);
        manager_pass = findViewById(R.id.manager_login_pass_input);
        btn_login = findViewById(R.id.manager_login);
        forgetNum = findViewById(R.id.manager_login_activity_forgetNum);
        registe = findViewById(R.id.manager_login_activity_register);
        Intent intent=getIntent();
        String username=intent.getStringExtra("username");
        manager_name.setText(username);

    }

    //跳转到登录后的管理界面
    public void login(View v){
        String name_info=manager_name.getText().toString().trim();
        String pass_info=manager_pass.getText().toString().trim();
        SQLiteDatabase db=dbHelper.getReadableDatabase();
        //通过输入的用户名查询数据库中的密码
        Cursor cursor=db.rawQuery("select password from manager where name=?",new String[]{name_info});
        String pi=null;
        if (cursor.moveToNext()){
            //获取密码
            pi=cursor.getString(cursor.getColumnIndex("password"));
        }
        //密码正确跳转到管理员界面
        if (pass_info.equals(pi)){
            Intent intent=new Intent(this,Manager_Activity.class);
            startActivity(intent);
            cursor.close();
        }else {
            Toast.makeText(this, "用户名或密码错误", Toast.LENGTH_SHORT).show();

        }
    }

    //自定义AlertDialog用于注册
    public void register(View v){
        Intent intent=new Intent(this,Manager_register_activity.class);
        startActivity(intent);
       /* AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater factory = LayoutInflater.from(this);
        View TextEntryView = factory.inflate(R.layout.manager_register_layout,null);
        builder.setTitle("用户注册");
        builder.setView(TextEntryView);

        final EditText code = TextEntryView.findViewById(R.id.manafer_register_info);
        final EditText name =TextEntryView.findViewById(R.id.manager_register_name);
        final EditText firstPassword =TextEntryView.findViewById(R.id.manager_register_first_password);
        final EditText secondPassword =TextEntryView.findViewById(R.id.manager_register_second_password);

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String codeinfo=code.getText().toString().trim();
                //注册码要为10086
                if (codeinfo.equals("10086")){
                    String nameinfo=name.getText().toString().trim();
                    String firstpassinfo=firstPassword.getText().toString().trim();
                    String secondpassinfo=secondPassword.getText().toString().trim();
                    //拿到一个数据库对象
                    SQLiteDatabase db=dbHelper.getReadableDatabase();
                    //检测密码是否是六个纯数字
                    if (firstpassinfo.matches("[0-9]{6}")){
                        //判断两次密码是否相同
                          if (firstpassinfo.equals(secondpassinfo)){
                              Cursor cursor=db.rawQuery("select name from manager where name=? ",new String[]{nameinfo});
                          //判断用户名是否存在
                              if(cursor.moveToNext()){
                                  Toast.makeText(Manager_login_Activity.this,"用户名已存在",Toast.LENGTH_LONG).show();
                              }else {
                                  //将用户名,密码存入数据库
                                  db.execSQL("insert into manager (name,password) values(?,?)",new String[]{nameinfo,firstpassinfo});
                              }
                          }else {

                              Toast.makeText(Manager_login_Activity.this,"两次密码不相同",Toast.LENGTH_LONG).show();
                          }
                    }else {
                        Toast.makeText(Manager_login_Activity.this,"密码为6个纯数字",Toast.LENGTH_LONG).show();

                    }
                }else {
                    Toast.makeText(Manager_login_Activity.this,"注册码错误",Toast.LENGTH_LONG).show();
                }
            }
        });
        builder.create().show();*/

    }
    //忘记密码监听
    public void forget(View v){
        Toast.makeText(this,"此功能暂不开放",Toast.LENGTH_LONG).show();
    }

}
