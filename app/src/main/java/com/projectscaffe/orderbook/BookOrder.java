package com.projectscaffe.orderbook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BookOrder extends AppCompatActivity {

    EditText name,number,address,amt,date,time;
    Button upload_data;
    CheckBox photo,video,pre_wedding,cinematic,candid;
    DatePickerDialog.OnDateSetListener onDateSetListener;
    TimePickerDialog.OnTimeSetListener onTimeSetListener;
    int hour,min,Hour,Min;
    long maxId = 0;


    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;
    Customers customers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_order);



        name=findViewById(R.id.csName);
        number=findViewById(R.id.csNumber);
        address=findViewById(R.id.csAddr);
        amt=findViewById(R.id.amt);
        date=findViewById(R.id.csDate);
        time=findViewById(R.id.csTime);
        upload_data = findViewById(R.id.submit);
        photo=findViewById(R.id.Photo);
        video=findViewById(R.id.Video);
        pre_wedding=findViewById(R.id.Prewedding);
        cinematic=findViewById(R.id.Cenematic);
        candid=findViewById(R.id.Candid);

        ActionBar actionBar = getSupportActionBar();

        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);




        final Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int date_of = calendar.get(Calendar.DAY_OF_MONTH);



        customers = new Customers();

        myRef = FirebaseDatabase.getInstance().getReference().child("Customers");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists())
                {


                    maxId = snapshot.getChildrenCount();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(BookOrder.this,R.style.ThemeOverlay_AppCompat_DayNight, onDateSetListener,year,month,date_of);
                datePickerDialog.getWindow();
                datePickerDialog.show();
            }
        });

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        BookOrder.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int hh, int mm) {
                                hour = hh;
                                min = mm;

                                calendar.set(0,0,0,hour,min);
                                time.setText(DateFormat.format("hh:mm aa",calendar));
                              //  time.setText(DateFormat.format("hh:mm aa", calendar));
                            }
                        },12,0,false
                );
                timePickerDialog.updateTime(hour,min);
                timePickerDialog.show();
            }
        });


      onDateSetListener = new DatePickerDialog.OnDateSetListener(){

            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day_of_mon) {

                month = month+1;
                String datem = date_of+"/"+month+"/"+year;
                date.setText(datem);

            }
        };





      upload_data.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              String nm = name.getText().toString();
              String no = address.getText().toString();
              String addr = number.getText().toString();
              String dt = date.getText().toString();
              String tm = time.getText().toString();
              String aamt = amt.getText().toString();
              String pht;
              String vdo;
              String prewd;
              String cnmtc;
              String cnd;


              Pattern p = Pattern.compile("(0|91)?[6-9][0-9]{9}");

              Matcher m = p.matcher(number.getText().toString());


              if (name.getText().toString().equals("")|| name.getText().toString().length()<3)
              {
                  name.setError("Please Enter Valid Name");

              }
              else if(address.getText().toString().equals("")||address.getText().toString().length()<3)
              {
                  address.setError("Please Proper Address");
              }
              else if(number.getText().toString().equals("")||number.getText().toString().length()<10||number.getText().toString().equals("1234567890")|| m.find() && m.group().equals(no))
              {
                  number.setError("Please Enter Valid Number");
              }
              else if(date.getText().toString().equals(""))
              {
                  date.setError("Please Select Date");
              }
              else if(time.getText().toString().equals(""))
              {
                  time.setError("Please Select Time");
              }else if (amt.getText().toString().equals(""))
              {
                  amt.setError("Please Enter Any Value");
              }


                  customers.setName(nm);
                  customers.setNumber(no);
                  customers.setAddress(addr);
                  customers.setDate(dt);
                  customers.setTime(tm);
                  customers.setAmount(aamt);


                  if(photo.isChecked())
                  {
                      pht = "YES";
                      customers.setPhoto(pht);
                  }
//              else {
//                  pht = "NO";
//                  customers.setPhoto(pht);
//              }
                  if(video.isChecked())
                  {
                      vdo = "YES";
                      customers.setVideo(vdo);
                  }
//              else {
//                  vdo = "NO";
//                  customers.setPhoto(vdo);
//              }
                  if(pre_wedding.isChecked())
                  {
                      prewd = "YES";
                      customers.setPre_wedding(prewd);
                  }
//              else {
//                  prewd = "NO";
//                  customers.setPhoto(prewd);
//              }
                  if(cinematic.isChecked())
                  {
                      cnmtc = "YES";
                      customers.setCinematic(cnmtc);
                  }
//              else {
//                  cnmtc = "NO";
//                  customers.setPhoto(cnmtc);
//              }
                  if(candid.isChecked())
                  {
                      cnd = "YES";
                      customers.setCandid(cnd);
                  }
                  if (!photo.isChecked())
                  {
                      pht = "NO";
                      customers.setPhoto(pht);
                  }
                  if (!video.isChecked())
                  {
                      vdo = "NO";
                      customers.setVideo(vdo);
                  }
                  if (!pre_wedding.isChecked())
                  {
                      prewd = "NO";
                      customers.setPre_wedding(prewd);
                  }
                  if (!cinematic.isChecked())
                  {
                      cnmtc = "NO";
                      customers.setCinematic(cnmtc);
                  }
                  if (!candid.isChecked())
                  {
                      cnd = "NO";
                      customers.setCandid(cnd);
                  }




                  boolean installed = isAppInstalled("com.whatsapp");

                  if (installed)
                  {
                      String text = "Thanks For Choosing Us, You Have Successfully Booked Order";
                      Intent intent = new Intent(Intent.ACTION_VIEW);
                      intent.setData(Uri.parse("http://api.whatsapp.com/send?phone=+91"+number.getText().toString()+"&text="+name.getText().toString()+text+"and Advance Paid By You : "+amt.getText().toString()+"- Mallikarjun Photos Baramati."));
                      startActivity(intent);

                  }
                  else {
                      Toast.makeText(BookOrder.this, "Please Install WhatsApp...", Toast.LENGTH_LONG).show();
                  }



              myRef.child(String.valueOf(maxId+1)).setValue(customers);
              Toast.makeText(BookOrder.this, "Order Booked Successfully", Toast.LENGTH_LONG).show();


//              else {
//                  cnd = "-";
//                  customers.setPhoto(cnd);
//              }









          }
      });



    }




    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }



    private boolean isAppInstalled(String s) {

        PackageManager packageManager = getPackageManager();
        boolean isInstalled;

        try {
            packageManager.getPackageInfo(s,PackageManager.GET_ACTIVITIES);
            isInstalled = true;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            isInstalled = false;
        }


        return isInstalled;
    }
}