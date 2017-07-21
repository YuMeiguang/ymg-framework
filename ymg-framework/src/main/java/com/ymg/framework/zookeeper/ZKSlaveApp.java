package com.ymg.framework.zookeeper;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;

/**
 * Created by yumg on 2017/7/17.
 */
public class ZKSlaveApp {

    private ZkClient zkClient;

    public ZkClient getZkClient() {
        return zkClient;
    }

    public void setZkClient(ZkClient zkClient) {
        this.zkClient = zkClient;
    }

    /**
     * 初始化zookeeper
     */
    public void initializ(){
        String connectionString="127.0.0.1:2181";
        int connectionTimeout=500000;
        zkClient = new ZkClient(connectionString,connectionTimeout);
        new Thread(new Runnable() {
            public void run() {
                zkClient.subscribeDataChanges("/root1", new IZkDataListener() {
                    public void handleDataChange(String dataPath, Object data) throws Exception {
                        System.out.println("the node 'dataPath'===>"+dataPath+", data has changed.it's data is "+String.valueOf(data));

                    }

                    public void handleDataDeleted(String s) throws Exception {
                        System.out.println("the node 'dataPath'===>");

                    }
                });
            }
        }).start();
    }

    /**
     * 函數入口
     * @param args
     */
    public static void main(String[] args) {
        ZKSlaveApp app = new ZKSlaveApp();
        app.initializ();
        try {
            Thread.sleep(100000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



}
