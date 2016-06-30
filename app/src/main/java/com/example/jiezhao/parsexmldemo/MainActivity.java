package com.example.jiezhao.parsexmldemo;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;

public class MainActivity extends Activity implements View.OnClickListener{
    private static final boolean DEBUG = false;
    private static final String TAG = "ParseXmlDemo";
    private Button parseXmlByPull;
    //读取文件需要添加相应的权限
    private final String FileName = "/sdcard/personal/ums/AndroidManifest.xml";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        parseXmlByPull = (Button) findViewById(R.id.parsexmlbypull_btn);
        parseXmlByPull.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.parsexmlbypull_btn:
                parseXmlByPull(readFile(FileName));
                break;
        }
    }

    private void parseXmlByPull(String s) {
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(new StringReader(s));
            int event = parser.getEventType();
            event = parser.next();
//            while (event != XmlPullParser.END_DOCUMENT) {
//                switch (event) {
//                    case XmlPullParser.START_DOCUMENT:
//                        break;
//
//                    case XmlPullParser.START_TAG:
//                        if(parser.getName() != null)
//                        Log.e(TAG, "NodeName = " + parser.getName() + parser.getAttributeCount());
////                        getTag(parser, "application", true, true);
//                        break;
//                    case XmlPullParser.END_TAG:
//                        break;
//                    case XmlPullParser.END_DOCUMENT:
//                        break;
//                }
//                //必须要有parser.next() ,否则会进入死循环
//                event = parser.next();
//            }
            if(event == XmlPullParser.START_TAG) {
                Log.e(TAG, "NodeName = " + parser.getName() + parser.getAttributeCount());
            }

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } //catch (IOException e) {
        catch (IOException e) {
            e.printStackTrace();
        }
        //            e.printStackTrace();
//        }
    }

    private void getTag(XmlPullParser parser, String tagName, boolean getAttr, boolean getText) {
        if (parser != null && tagName != null && tagName.length() > 0) {
            String nodeName = parser.getName();
            if (nodeName != null && tagName.equals(nodeName)) {
                if (getAttr) {
                    int attrsize = parser.getAttributeCount();
                    if(attrsize > 0) {
                        for (int i = 0; i < attrsize; i++) {
                            Log.e(TAG, tagName + " Attr " + i + " " + parser.getAttributeName(i) + "=" + parser.getAttributeValue(i));
                        }
                    }
                }
                if (getText) {
                    try {
                        String text = parser.nextText();
                        Log.e(TAG, tagName + " text " + text);
                    } catch (XmlPullParserException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * @param filePath 文件路径
     * */
    private String readFile(String filePath) {
        BufferedReader br = null;
        StringBuffer sb = new StringBuffer();
        try {
            FileReader reader = new FileReader(filePath);

            br = new BufferedReader(reader);
            String s = null;
            while ((s = br.readLine()) != null) {
                sb.append(s);
                if(DEBUG) {
                    Log.e(TAG,s);
                }
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG,e.getMessage());
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}
