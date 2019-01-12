package com.example.sergio.demodebug.parser;

import com.example.sergio.demodebug.POJO.User;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class UserXmlParser {
    public static List<User> parse(String content){
        boolean inDataItemTag=false;
        String tagName ="";
        User user = null;
        List<User> userList = new ArrayList<>();

        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(new StringReader(content));

            int eventType= parser.getEventType();

            while (eventType!=XmlPullParser.END_DOCUMENT){
                switch (eventType){
                    case XmlPullParser.START_TAG:
                        tagName = parser.getName();
                        if(tagName.equals("usuario")){
                            inDataItemTag=true;
                            user= new User();
                            userList.add(user);
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        tagName = parser.getName();
                        if(tagName.equals("usuario"))
                            inDataItemTag=false;
                        tagName="";
                        break;
                    case XmlPullParser.TEXT:
                        if (inDataItemTag && user!=null){
                            switch (tagName){
                                case"usuarioid":
                                    user.setId(Integer.parseInt(parser.getText()));
                                    break;
                                case"nombre":
                                    user.setName(parser.getText());
                                    break;
                                case"twitter":
                                    user.setUsername(parser.getText());
                                    break;
                            }
                        }
                        break;
                }
                eventType= parser.next();
            }
            return userList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
