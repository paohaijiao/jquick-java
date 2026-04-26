package com.github.paohaijiao.xml;
import com.github.paohaijiao.xml.param.Param;

import java.util.HashMap;

public interface UserMapper {

    public HashMap<String,String> all() ;

    public int sum(@Param("a") int a, @Param("b") int b) ;
}
