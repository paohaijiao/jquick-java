package com.github.paohaijiao.model;

import com.github.paohaijiao.support.JQuickJavaTypeReference;
import lombok.Data;

@Data
public class JQuickJavaTypeReferenceAndValue {

    private JQuickJavaTypeReference<?> typeArguments;

    private  Object data;
}
