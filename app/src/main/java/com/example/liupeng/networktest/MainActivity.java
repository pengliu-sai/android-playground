package com.example.liupeng.networktest;

import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import javax.xml.parsers.SAXParserFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int SHOW_RESPONSE = 0;

    private Button sendRequest;

    private TextView responseText;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SHOW_RESPONSE:
                    String response = (String) msg.obj;
                    responseText.setText(response);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sendRequest = (Button) findViewById(R.id.send_request);
        responseText = (TextView) findViewById(R.id.response_text);
        sendRequest.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.send_request) {
            sendRequestWithHttpURLConnection();
        }
    }

    private void sendRequestWithHttpURLConnection() {
        String address = "http://10.0.2.2:8080/get_data.xml";

        HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                parseXMLWithSAX(response);
            }

            @Override
            public void onError(Exception e) {

            }
        });


//        new Thread(new Runnable() {
//            @Override
//            public void run() {


//                HttpURLConnection connection = null;
//                try {
//                    URL url = new URL("http://www.baidu.com");
//                    connection = (HttpURLConnection) url.openConnection();
//                    connection.setRequestMethod("GET");
//                    connection.setConnectTimeout(8000);
//                    connection.setReadTimeout(8000);
//                    InputStream in = connection.getInputStream();
//
//                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
//                    StringBuffer response = new StringBuffer();
//                    String line;
//                    while ((line = reader.readLine()) != null) {
//                        response.append(line);
//                    }
//                    Message message = new Message();
//                    message.what = SHOW_RESPONSE;
//
//                    message.obj = response.toString();
//                    handler.sendMessage(message);

//                    URL url = new URL("http://10.0.2.2:8080/get_data.xml");
//                    URL url = new URL("http://10.0.2.2:8080/get_data.json");
//
//                    connection = (HttpURLConnection) url.openConnection();
//                    connection.setRequestMethod("GET");
//                    connection.setConnectTimeout(888000);
//                    connection.setReadTimeout(8000);
//                    InputStream in = connection.getInputStream();
//
//                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
//                    StringBuffer response = new StringBuffer();
//                    String line;
//                    while ((line = reader.readLine()) != null) {
//                        response.append(line);
//                    }

//                    parseXMLWithPull(response.toString());
//                    parseXMLWithSAX(response.toString());

//                    parseJSONWithJSONObject(response.toString());

//                    parseJSONWithGSON(response.toString());
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                } finally {
//                    if (connection != null) {
//                        connection.disconnect();
//                    }
//                }
//            }
//        }).start();
    }

    private void parseXMLWithPull(String xmlData) {

        Log.d("MainActivity xmlData", xmlData);

        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser = factory.newPullParser();
            xmlPullParser.setInput(new StringReader(xmlData));
            int eventType = xmlPullParser.getEventType();
            String id = "";
            String name = "";
            String version = "";
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String nodeName = xmlPullParser.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG: {
                        if ("id".equals(nodeName)) {
                            id = xmlPullParser.nextText();
                        } else if ("name".equals(nodeName)) {
                            name = xmlPullParser.nextText();
                        } else if ("version".equals(nodeName)) {
                            version = xmlPullParser.nextText();
                        }
                        break;
                    }
                    case XmlPullParser.END_TAG: {
                        if ("app".equals(nodeName)) {
                            Log.d("MainActivity", "id is " + id);
                            Log.d("MainActivity", "name is " + name);
                            Log.d("MainActivity", "version is " + version);
                        }
                        break;
                    }
                    default:
                        break;
                }
                eventType = xmlPullParser.next();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void parseXMLWithSAX(String xmlData) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            XMLReader xmlReader = factory.newSAXParser().getXMLReader();
            ContentHandler handler = new ContentHandler();
            xmlReader.setContentHandler(handler);
            xmlReader.parse(new InputSource(new StringReader(xmlData)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void parseJSONWithJSONObject(String jsonData) {
        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String id = jsonObject.getString("id");
                String name = jsonObject.getString("name");
                String version = jsonObject.getString("version");
                Log.d("MainActivity", "id is " + id);
                Log.d("MainActivity", "name is " + name);
                Log.d("MainActivity", "version is " + version);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void parseJSONWithGSON(String jsonData) {
        Gson gson = new Gson();
        List<App> appList = gson.fromJson(jsonData, new TypeToken<List<App>>() {}.getType());
        for (App app : appList) {
            Log.d("MainActivity", "id is " + app.getId());
            Log.d("MainActivity", "name is " + app.getName());
            Log.d("MainActivity", "version is " + app.getVersion());
        }

    }

}
