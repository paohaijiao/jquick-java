package com.github.paohaijiao.xml;

import com.github.paohaijiao.xml.element.JQuickXmlElement;

import java.util.Arrays;
import java.util.List;

public class JQuickJavaXmlElement implements JQuickXmlElement {
    @Override
    public String getNameSpaceName() {
        return "namespace";
    }

    @Override
    public String getRootElementTagName() {
        return "javas";
    }

    @Override
    public List<String> getChildElementTagName() {
        return Arrays.asList(new String[]{"java"});
    }

    @Override
    public String getMethodName() {
        return "name";
    }

    @Override
    public String getMethodReturnClass() {
        return "returnClass";
    }

    @Override
    public String getMethodParamClass() {
        return "paramClass";
    }

    @Override
    public String getValue() {
        return "value";
    }
}
