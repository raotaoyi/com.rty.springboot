package com.rty.springboot.bean;


import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * yaml的使用,集成到spring中，可以使用springboot的配置进行读取
 */
public class KafkaSaveBean {
    private List<KafkaSaveDb> kafkaSaveDbs;

    public static class KafkaSaveDb {
        private String topic;
        private String groupId;
        private boolean enable;
        private List<KafkaSaveDbConf> saves;


        public List<KafkaSaveDbConf> getSaves() {
            return saves;
        }

        public void setSaves(List<KafkaSaveDbConf> saves) {
            this.saves = saves;
        }

        public String getTopic() {
            return topic;
        }

        public void setTopic(String topic) {
            this.topic = topic;
        }

        public String getGroupId() {
            return groupId;
        }

        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }

        public boolean isEnable() {
            return enable;
        }

        public void setEnable(boolean enable) {
            this.enable = enable;
        }
    }

    public static class KafkaSaveDbConf {
        private String type;
        private Map<String, String> params;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Map<String, String> getParams() {
            return params;
        }

        public void setParams(Map<String, String> params) {
            this.params = params;
        }
    }


    public List<KafkaSaveDb> getKafkaSaveDbs() {
        return kafkaSaveDbs;
    }

    public void setKafkaSaveDbs(List<KafkaSaveDb> kafkaSaveDbs) {
        this.kafkaSaveDbs = kafkaSaveDbs;
    }
}
