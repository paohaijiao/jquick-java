package com.github.paohaijiao.xml;

import com.github.paohaijiao.xml.element.JQuickXmlElement;
import com.github.paohaijiao.xml.handler.JQuickParseHandler;
import com.github.paohaijiao.xml.invocation.JQuickXmlInvocationHandler;

public class JQuickJavaXmlParseFactory implements JQuickParseHandler {
    @Override
    public JQuickXmlElement createJQuickXmlElement() {
        return new JQuickJavaXmlElement();
    }

    @Override
    public JQuickXmlInvocationHandler createlInvocationHandler() {
        return new JQuickJavaXmlInvocationHandler();
    }
}
