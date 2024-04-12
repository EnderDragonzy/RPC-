package com.stu.Server;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import com.stu.Com.RPCinterface;
public class RMIServer {
    public static void main(String[] args) throws Exception {
        try{
            String name = "StuCRUD";
            RPCinterface engine = new com.stu.Server.RPCinterfaceImpl();
            RPCinterface skeleton = (RPCinterface) UnicastRemoteObject.exportObject(engine,0);

            Registry registry = LocateRegistry.getRegistry("127.0.0.1",1099);
            System.out.println("Registering RPCinterface object");
            registry.rebind(name,skeleton);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
