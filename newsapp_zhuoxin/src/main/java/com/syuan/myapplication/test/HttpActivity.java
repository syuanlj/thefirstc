package com.syuan.myapplication.test;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.Xml;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.syuan.myapplication.R;
import com.syuan.myapplication.entity.Person;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by sy on 2017/6/5.
 */

public class HttpActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Thread(new Runnable() {
            @Override
            public void run() {
                init();//HttpURLConnection
                init2();//okHttp
                initJson();
                initGson();

            }
        }).start();
        initXMLPull();
        initXMLSAX();
    }

    private void initXMLSAX() {
        try {
            InputStream is=getAssets().open("data.xml");
            BufferedReader buffer=new BufferedReader(new InputStreamReader(is));
            StringBuffer sb=new StringBuffer();
            String str;
            while ((str=buffer.readLine())!=null){
                sb.append(str);
            }
            SAXParserFactory factory = SAXParserFactory.newInstance();
            XMLReader reader = factory.newSAXParser().getXMLReader();
            MySAXHandler mySAXHandler = new MySAXHandler();
            reader.setContentHandler(mySAXHandler);
            reader.parse(new InputSource(new StringReader(sb.toString())));
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class MySAXHandler extends DefaultHandler {
        private String nodeName;
        private StringBuilder id;
        private StringBuilder name;
        private StringBuilder score;

        @Override//开始xml解析的时候调用
        public void startDocument() throws SAXException {
            id = new StringBuilder();
            name = new StringBuilder();
            score = new StringBuilder();
        }

        @Override//开始解析某个节点解析
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            nodeName = localName;
        }

        @Override//获得某个节点内容时调用
        public void characters(char[] ch, int start, int length) throws SAXException {
            switch (nodeName) {
                case "id":
                    id.append(ch, start, length);
                    break;
                case "name":
                    name.append(ch, start, length);
                    break;
                case "score":
                    score.append(ch, start, length);
                    break;
            }
        }

        @Override//完成解析某个节点解析
        public void endElement(String uri, String localName, String qName) throws SAXException {
            if ("app".equals(localName)) {
                Log.e("xmlSAX", "  id=" + id.toString().trim() + "  name=" + name.toString().trim() + "  score=" + score.toString().trim());
                //清空
                id.setLength(0);
                name.setLength(0);
                score.setLength(0);
            }
        }

        @Override//完成整个xml解析后调用
        public void endDocument() throws SAXException {
            super.endDocument();
        }
    }

    private void initXMLPull() {
        try {
            InputStream is = getAssets().open("data.xml");
//            XmlPullParserFactory factory=XmlPullParserFactory.newInstance();
//            XmlPullParser xmlPullParser=factory.newPullParser();
            XmlPullParser parser = Xml.newPullParser();//获得xml解析器
            parser.setInput(is, "UTF-8");
            String id = null;
            String name = null;
            String score = null;
            //获得节点
            int eventType = parser.getEventType();
            //循环判断当前的解析事件类型是否是文档结束类型
            while (eventType != XmlPullParser.END_DOCUMENT) {//(最后位置)
                String nodeName = parser.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG://开始节点
                        switch (nodeName) {
                            case "id":
                                id = parser.nextText();
                                break;
                            case "name":
                                name = parser.nextText();
                                break;
                            case "score":
                                score = parser.nextText();
                                break;
                        }
                        break;
                    case XmlPullParser.END_TAG://结束节点
                        if ("app".equals(nodeName)) {
//                            Log.e("xmlPull", "id=" + id + "\tname=" + name + "\tscore=" + score);
                        }
                        break;
                }
                eventType = parser.next();//获得下一个节点
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
    }

    private void initGson() {
        String url = "http://guolin.tech/api/china";
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();//发送一个http请求，创建一个Request对象
        Response response = null;
        try {
            response = okHttpClient.newCall(request).execute();
            String str = response.body().string();
            Gson gson = new Gson();//实例化
            //解析，数据需要通过借助TypeToken将期望解析成数据类型
            List<Person> personList = gson.fromJson(str, new TypeToken<List<Person>>() {
            }.getType());
            for (Person person : personList) {
//                System.out.println(person.toString());
            }
//            JSONArray ja=new JSONArray(str);//获得json数组[]
//            System.out.println("jsonArray:"+ja);
//            JSONObject jo=new JSONObject();
//            Gson gson=new Gson();
//            for (int i=0;i<ja.length();i++){
//                jo=ja.getJSONObject(i);//获得耽搁jso对象
//                Person person=gson.fromJson(jo.toString(),Person.class);
//                System.out.println(person.toString());
//            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initJson() {
        String url = "http://guolin.tech/api/china";
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();//发送一个http请求，创建一个Request对象

        try {
            Response response = okHttpClient.newCall(request).execute();
            String str = response.body().string();
            JSONArray ja = new JSONArray(str);//获得json数组[]
//            System.out.println("jsonArray:" + ja);
            JSONObject jo = new JSONObject();
            for (int i = 0; i < ja.length(); i++) {
                jo = ja.getJSONObject(i);//获得耽搁jso对象
//                System.out.println("jsonObject:" + jo);
                int id = jo.getInt("id");
                String name = jo.getString("name");
//                System.out.println("jsonObject:id=" + id + "  name=" + name);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void init2() {
        String url = "http://118.244.212.82:9092/newsClient/news_sort?ver=2&imei=2=2&subid=1&dir=1&nid= null&cnt=2";
        OkHttpClient okHttp = new OkHttpClient();//实例化一个OkHttpClient
        Request request = new Request.Builder().url(url).build();//发送一个http请求，创建一个Request对象
        try {
            Response response = okHttp.newCall(request).execute();//获得请求内容
            String str = response.body().string();
            Message msg = new Message();
            Bundle bundle = new Bundle();
            bundle.putString("data", str);
            msg.setData(bundle);
            msg.what = 99;
            handler.sendMessage(msg);
//            System.out.println("syuan++" + str);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 99:
                    Toast.makeText(HttpActivity.this, msg.getData().getString("data"), Toast.LENGTH_SHORT).show();
                    break;
            }
            return false;
        }
    });

    private void init() {
        HttpURLConnection connection = null;//声明
        try {
            URL url = new URL("http://118.244.212.82:9092/newsClient/news_sort?ver=2&imei=2");//网址
            connection = (HttpURLConnection) url.openConnection();//设置网址
            connection.setConnectTimeout(5000);//5s网络连接超时
            connection.setReadTimeout(5000);//5s读取超时
            connection.setRequestMethod("GET");//设置网络请求方式

            InputStream in = connection.getInputStream();//获得请求内容
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));//转换
            final StringBuffer buffer = new StringBuffer();
            String str;
            while ((str = reader.readLine()) != null) {
                buffer.append(str);//将str添加到buffer中
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(HttpActivity.this, buffer, Toast.LENGTH_SHORT).show();
                }
            });
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();//断开连接
            }
        }
    }
}
