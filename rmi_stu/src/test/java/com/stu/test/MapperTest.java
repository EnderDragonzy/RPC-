package com.stu.test;

import com.stu.Entity.Student;
import com.stu.util.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import com.stu.mapper.Mapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class MapperTest {
    private SqlSession session;
    private Mapper mapper;

    @Before
    public void init()
    {
        session = MyBatisUtil.openSession();
        mapper = session.getMapper(Mapper.class);

    }

    @Test
    public void findStuList()
    {
        List<Student> studentList = mapper.findStuList();
        System.out.println(studentList);
    }
    @Test
    public void insert()
    {
        try{
            Student student = new Student("744","s22232tu",130);
            int rows = mapper.insertStu(student);
            System.out.println(rows);
            session.commit(); // 更新操作必须加上commit,否则数据库不会更新
        } catch (Exception e){
            session.rollback();
        }
    }

    @After
    public void destroy()
    {
        session.close();
    }

}
