package com.github.paohaijiao.xml;

import com.github.paohaijiao.xml.factory.JQuickFactory;
import com.github.paohaijiao.xml.factory.JQuickXmlFactory;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;

public class JQuickJavaTest {
    @Test
    public void tesstAction() throws IOException {
        JQuickJavaXmlParseFactory handler=new JQuickJavaXmlParseFactory();
        JQuickFactory factory = new JQuickXmlFactory(handler,"jquick-java.xml");
        UserMapper userApi = factory.createApi(UserMapper.class);
        int sum= userApi.sum(1,2);
        System.out.println(sum);
    }

}
