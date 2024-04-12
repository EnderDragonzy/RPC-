package com.stu.test;

import com.stu.Entity.Student;
import com.stu.util.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CRUDTest {
    private SqlSession session;

    @Before
    public void init()
    {
        session = MyBatisUtil.openSession();
    }

    @Test
    public void findStuListTest(){
        List<Student> studentList = session.selectList("com.stu.mapper.Mapper.findStuList");
        System.out.println(studentList);
    }

    @Test
    public void findStuByNameTest()
    {
        List<Student> studentList = session.selectList("com.stu.mapper.Mapper.findStuByName","张三");
        System.out.println(studentList);
    }

    @Test
    public void findStuByScoreTest()
    {
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("minScore",0);
        map.put("maxScore",100);
        List<Student> studentList = session.selectList("com.stu.mapper.Mapper.findStuByScore",map);
        System.out.println(studentList);
    }
    @Test
    public void testInsertStu(){
        try{
            Student student = new Student("77","s22tu",330);
            int rows = session.insert("com.stu.mapper.Mapper.insertStu",student);
            System.out.println(rows);
            session.commit();
        } catch (Exception e){
            session.rollback();
        }
    }

    @Test
    public void testUpdateStu()
    {
        try{
            Student student = session.selectOne("stump.findStuById","1");
            student.setScore(69);
            int rows = session.update("stump.updateStu",student);
            System.out.println(rows);
            session.commit();
        } catch (Exception e)
        {
            session.rollback();
        }
    }

    @Test
    public void testDeleteStu()
    {
        try{
            int rows = session.delete("stump.deleteStu","2");
            System.out.println(rows);
            session.commit();
        }catch (Exception e)
        {
            session.rollback();
            e.printStackTrace();
        }
    }

    @After
    public void destory()
    {
        session.close();
    }

}
