package com.example.sergio.demodebug;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sergio.demodebug.POJO.User;
import com.example.sergio.demodebug.adapters.MyAdapter;
import com.example.sergio.demodebug.adapters.RecyclerAdapter;
import com.example.sergio.demodebug.parser.UserJsonParser;
import com.example.sergio.demodebug.parser.UserXmlParser;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    TextView txtView;
    ProgressBar progressBar;
    List<MyAsyncTask> taskList;
    List<User> userList;
    String ParserType="";
    //ListView listview;
    //MyAdapter adapter;
    RecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnXml = findViewById(R.id.buttonXml);
        Button btnJson = findViewById(R.id.buttonJson);
        Button btnSec = findViewById(R.id.buttonJsonSec);
        Button btnSend = findViewById(R.id.buttonSendParams);
        txtView = findViewById(R.id.textview);

        //listview = findViewById(R.id.listview);

        recyclerView = findViewById(R.id.recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        //para cardview
        recyclerView.setHasFixedSize(true);

        txtView.setMovementMethod(new ScrollingMovementMethod());
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        progressBar.setMax(100);
        taskList = new ArrayList<>();
        userList = new ArrayList<>();

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isOnline()){
                    ParserType="SEND";
                    sendDatos("http://maloschistes.com/maloschistes.com/jose/webservicesend.php");
                }else{
                    Toast.makeText(getApplicationContext(),"No hay conexion",Toast.LENGTH_LONG).show();
                }
            }
        });
        btnSec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isOnline()){
                    ParserType="SEC";
                    pedirDatos("http://maloschistes.com/maloschistes.com/jose/s/webservice.php");
                }else{
                    Toast.makeText(getApplicationContext(),"No hay conexion",Toast.LENGTH_LONG).show();
                }
            }
        });
        btnJson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isOnline()){
                    ParserType="JSON";
                    //pedirDatos("http://maloschistes.com/maloschistes.com/jose/webservice.php");
                    pedirDatos("http://maloschistes.com/maloschistes.com/jose/webserviceI.php");
                }else{
                    Toast.makeText(getApplicationContext(),"No hay conexion",Toast.LENGTH_LONG).show();
                }
            }
        });
        btnXml.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isOnline()){
                    ParserType="XML";
                    pedirDatos("http://maloschistes.com/maloschistes.com/jose/usuarios.xml");
                }else{
                    Toast.makeText(getApplicationContext(),"No hay conexion",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public void pedirDatos(String uri){
        /*MyAsyncTask task = new MyAsyncTask();
        task.execute(uri);*/
    }
    public void sendDatos(String uri){
        MyAsyncTask task = new MyAsyncTask();

        Request request = new Request();
        //request.setMethod("GET");
        request.setMethod("POST");
        request.setUri(uri);
        request.setParams("lolipop","5.0");
        request.setParams("kitkat","4.4");
        request.setParams("oreo","8.0");

        task.execute(request);
    }
    public void loadData(){
        /*if(userList!=null){
            for (User user: userList) {
                txtView.append("\n"+user.toString());
            }

        }*/
        /* adapter = new MyAdapter(userList,getApplicationContext());
        listview.setAdapter(adapter);*/
        recyclerAdapter = new RecyclerAdapter(userList,getApplicationContext());
        recyclerView.setAdapter(recyclerAdapter);
    }
    public void loadData(String datos){
        txtView.append(datos +"\n");
    }

    public boolean isOnline(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }

    //private class MyAsyncTask extends AsyncTask<String,Integer,String>{
    private class MyAsyncTask extends AsyncTask<Request,Integer,String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if(taskList.size()==0)
                progressBar.setVisibility(View.VISIBLE);
            taskList.add(this);
        }

        /*@Override
        protected String doInBackground(String... params) {
            String content = HttpManager.getData(params[0],"pepito","pepito");*/
        @Override
        protected String doInBackground(Request... params) {

            String content = HttpManager.getData(params[0]);
            return content;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            //loadDataView("num "+ values[0]);
            progressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if (result==null){
                Toast.makeText(getApplicationContext(),"Error en el resultado del servidor",Toast.LENGTH_LONG).show();
            }else{
                if (ParserType.equals("JSON") || ParserType.equals("SEC"))
                    userList= UserJsonParser.parse(result);
                else if (ParserType.equals("XML"))
                    userList= UserXmlParser.parse(result);

                if (ParserType.equals("SEND"))
                    loadData(result);
                else
                    loadData();

            }

            taskList.remove(this);
            if(taskList.size()==0)
                progressBar.setVisibility(View.GONE);
        }
    }
}
