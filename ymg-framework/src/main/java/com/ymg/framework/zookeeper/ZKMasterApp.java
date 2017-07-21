package com.ymg.framework.zookeeper;

import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.CreateMode;

import java.util.UUID;

/**
 * Created by yumg on 2017/7/17.
 * 本示例为zookeeper测试示例
 * 1.确保安装zookeeper
 * 2.确保启动zookeeper 默认本地
 * 3.mac 安装zookeeper方式为  brew install zookeeper
 * 4.启动zookeeper方式为：zkServer start
 * 5.运行如下类
 */
public class ZKMasterApp {

    private ZkClient zkClient;


    public ZkClient getZkClient() {
        return zkClient;
    }

    public void setZkClient(ZkClient zkClient) {
        this.zkClient = zkClient;
    }


    public static void main(String[] args) {
        ZKMasterApp bootstrap = new ZKMasterApp();
        bootstrap.initialize();
        try {
            Thread.sleep(100000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void initialize() {

        String connectionString = "127.0.0.1:2181";
        int connectionTimeout=50000;
        zkClient=new ZkClient(connectionString, connectionTimeout);
        if(!zkClient.exists("/root1")) {
            zkClient.create("/root1", new Long(System.currentTimeMillis()), CreateMode.EPHEMERAL);
        }
        new Thread(new RootNodeChangeThread()).start();
    }

    private class RootNodeChangeThread implements Runnable{

        public void run() {
            while (true){
                try {
                    Thread.sleep(20000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                String uuidStr= UUID.randomUUID().toString();


                System.out.println(">>>>>>>>>> 产生随机的 uuid string,'uuidStr'===>"+uuidStr);
                zkClient.writeData("/root1", uuidStr);
            }
        }
    }

}


