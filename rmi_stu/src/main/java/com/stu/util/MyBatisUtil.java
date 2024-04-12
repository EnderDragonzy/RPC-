package com.stu.util;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class MyBatisUtil {
    private static SqlSessionFactory sessionFactory;

    static {

        try{
            InputStream is = Resources.getResourceAsStream("mybatis.xml");

            sessionFactory = new SqlSessionFactoryBuilder().build(is);

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static SqlSession openSession(){
        return sessionFactory.openSession();
    }
}
