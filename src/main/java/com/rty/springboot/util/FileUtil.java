package com.rty.springboot.util;

import com.rty.springboot.bean.KafkaSaveBean;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class FileUtil {
    public static String getJsonString() {
        return null;
    }

    public static Properties loadProperties() {
        Properties properties = new Properties();
        try (InputStream in = FileUtil.class.getClassLoader().getResourceAsStream("conf/server.properties")) {
            // 使用properties对象加载输入流
            properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    public static KafkaSaveBean loadYamlConfig(String yamlPath) {
        Yaml yaml = new Yaml();
        try (InputStream is = KafkaSaveBean.class.getResourceAsStream(yamlPath)) {
            return yaml.loadAs(is, KafkaSaveBean.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
