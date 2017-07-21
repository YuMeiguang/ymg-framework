package com.ymg.bean;

import java.util.List;
import java.util.Map;

/**
 * Created by yumg on 2017/7/16.
 */
public class DisplayJobBean {

    private String name;
    private String group;
    private String cronExpression;

    private Map baseInfo;

    private List<String> personName;

    public DisplayJobBean(String name, String group) {
        this.name = name;
        this.group = group;
    }

    public DisplayJobBean(String name, String group, String cronExpression) {
        this.name = name;
        this.group = group;
        this.cronExpression = cronExpression;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public Map getBaseInfo() {
        return baseInfo;
    }

    public void setBaseInfo(Map baseInfo) {
        this.baseInfo = baseInfo;
    }

    public List<String> getPersonName() {
        return personName;
    }

    public void setPersonName(List<String> personName) {
        this.personName = personName;
    }

    @Override
    public String toString() {
        return "DisplayJobBean{" +
                "name='" + name + '\'' +
                ", group='" + group + '\'' +
                ", cronExpression='" + cronExpression + '\'' +
                '}';
    }
}
