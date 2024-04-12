package com.stu.Com;

import com.stu.Entity.Student;
import com.stu.mapper.Mapper;
import org.apache.ibatis.session.SqlSession;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface RPCinterface extends Remote {
    public SqlSession session = null;
    public Mapper mapper = null;
    void init() throws RemoteException;

    boolean add(Student stu) throws RemoteException;

    Student queryByID(String stuID) throws RemoteException;

    List<Student> queryByName(String name) throws RemoteException;

    boolean delete(String stuID) throws RemoteException;

    void destory() throws RemoteException;
}

