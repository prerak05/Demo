package com.prerak.demo.recyclerViewwithcheckbox;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.prerak.demo.R;
import com.prerak.demo.recyclerViewwithcheckbox.adapter.MyAdapter;
import com.prerak.demo.recyclerViewwithcheckbox.model.Student;

import java.util.ArrayList;
import java.util.List;

public class RecyclerCheckBoxActivity extends AppCompatActivity {
    // variable declaration
    private RecyclerView recyclerView;
    private Button btnSelected;
    private List<Student> studentList;
    private MyAdapter myAdapter;
    private List<Student> image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_check_box);
        //initialization
        init();
        studentList= new ArrayList<>();
        for (int i=0; i<=100; i++){
            Student student=new Student("student" +i, "android" + i + "@gmail.com",false);
            studentList.add(student);
        }

        image=getImage();


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        myAdapter=new MyAdapter(this,studentList,image);
        recyclerView.setAdapter(myAdapter);
        btnSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data="";
                List<Student> students= myAdapter.getStudent();
                for (int i=0; i<students.size(); i++){
                    Student student=students.get(i);
                    if (student.isSelected() == true){
                        data= data + "\n"+ student.getName() +" " + student.getEmailID();
//                        Toast.makeText(MainActivity.this, data, Toast.LENGTH_SHORT).show();
                    }
                }
                Intent intent=new Intent(RecyclerCheckBoxActivity.this,DisplayCheckboxActivity.class);
                intent.putExtra("data",data);
                startActivity(intent);

            }
        });
    }
    private List<Student> getImage() {
        List<Student> image= new ArrayList<>();

        Student student=new Student();
        student.setImg("https://static.pexels.com/photos/257360/pexels-photo-257360.jpeg");
        image.add(student);

        return image;
    }

    private void init() {
        recyclerView= (RecyclerView) findViewById(R.id.my_recycler_view);
        btnSelected= (Button) findViewById(R.id.btnShow);
    }
}
