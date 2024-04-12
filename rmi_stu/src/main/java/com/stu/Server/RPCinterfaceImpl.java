package com.stu.Server;

import com.stu.Com.RPCinterface;
import com.stu.Entity.Student;
import com.stu.mapper.Mapper;
import com.stu.util.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;


import java.rmi.RemoteException;
import java.util.List;

public class RPCinterfaceImpl  implements RPCinterface {

    public SqlSession session = null;
    public Mapper mapper = null;


    // 必须有一个显式构造函数，且抛出异常
    public RPCinterfaceImpl() throws RemoteException {
        super();
    }

    @Override
    public void init() throws RemoteException
    {
        session = MyBatisUtil.openSession();
        mapper = session.getMapper(Mapper.class);
    }

    @Override
    public boolean add(Student stu) throws RemoteException
    {
        int rows = 0;
        try{
            rows = mapper.insertStu(stu);
            System.out.println("Call add a Student info, rows="+rows);
            session.commit();
        }catch (Exception e)
        {
            session.rollback();
            e.printStackTrace();
        }
        if(rows >= 1) return true;
        else return false;
    }

    @Override
    public Student queryByID(String stuID) throws RemoteException
    {
        Student student = null;
        student = mapper.findStuById(stuID);
        System.out.println("Call queryByID, student info:"+student);
        return student;
    }

    @Override
    public List<Student> queryByName(String name) throws RemoteException
    {
        List<Student> studentList = null;
        studentList = mapper.findStuByName(name);
        System.out.println("Call queryByName,student info:"+studentList);
        return studentList;
    }

    @Override
    public boolean delete(String stuID) throws RemoteException
    {
        int rows = 0;
        try{
            rows = mapper.deleteStu(stuID);
            session.commit();
        }catch (Exception e)
        {
            session.rollback();
            e.printStackTrace();
        }

        System.out.println("Call delete, rows="+rows);
        if (rows >= 1) return true;
        else return false;
    }

    @Override
    public void destory() throws RemoteException
    {
        session.close();
    }


}
