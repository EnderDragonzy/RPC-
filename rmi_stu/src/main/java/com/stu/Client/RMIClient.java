package com.stu.Client;

import com.stu.Com.RPCinterface;
import com.stu.Entity.Student;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

public class RMIClient {
    public static void main(String args[])
    {
        try{
            String name = "StuCRUD";
            String serverIP = "127.0.0.1";
            int serverPort = 1099;
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            Registry registry = LocateRegistry.getRegistry(serverIP,serverPort);
            RPCinterface stuCRUD = (RPCinterface) registry.lookup(name);
            // 初始化,连接远程数据库
            stuCRUD.init();
            System.out.println("--------RPC版学籍管理系统--------");
            while(true)
            {
                System.out.println("请选择功能(输入对应序号后回车）:");
                System.out.println("1.添加一条学生信息");
                System.out.println("2.查询指定ID的学生信息");
                System.out.println("3.查询指定姓名的学生信息");
                System.out.println("4.删除指定学号的学生信息");
                System.out.println("5.退出程序");

                String op = stdIn.readLine();
                if(op.equals("1"))
                {
                    Student student = new Student();
                    System.out.println("请输入学号:");
                    student.setId(stdIn.readLine());
                    System.out.println("请输入姓名:");
                    student.setName(stdIn.readLine());
                    System.out.println("请输入成绩:");
                    student.setScore(Double.parseDouble(stdIn.readLine()));
                    // RPC调用
                    if(stuCRUD.add(student))
                        System.out.println("添加成功");
                    else
                        System.out.println("添加失败");
                }
                else if(op.equals("2") )
                {
                    String id = new String();
                    System.out.println("请输入学号:");
                    id = stdIn.readLine();
                    Student student = stuCRUD.queryByID(id);
                    if(student == null)
                    {
                        System.out.println("没有该学生");
                    }
                    else
                    {
                        System.out.println(student);
                    }
                }
                else if(op.equals("3") )
                {
                    System.out.println("请输入姓名:");
                    String stuname = stdIn.readLine();
                    List<Student> studentList = stuCRUD.queryByName(stuname);
                    if(studentList==null)
                    {
                        System.out.println("没有该学生");
                    }
                    else
                    {
                        System.out.println(studentList);
                    }
                }
                else if(op.equals("4") )
                {
                    System.out.println("请输入学号:");
                    String id = stdIn.readLine();
                    if(stuCRUD.delete(id))
                    {
                        System.out.println("删除成功！");
                    }
                    else
                    {
                        System.out.println("删除失败！没有该学生");
                    }
                }
                else if(op.equals("5"))
                {
                    System.out.println("退出");
                    break;
                }
                else
                {
                    System.out.println("无效输入");
                    continue;
                }
            }
            // 断开与数据库的连接
            stuCRUD.destory();

        }catch (Exception e)
        {
            System.err.println("Exception:" + e);
            e.printStackTrace();
        }
    }
}
