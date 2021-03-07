package com.rty.springboot.test.dao;

import com.rty.springboot.test.ApplicationTest;
import com.rty.springboot.web.mapper.UserMapper;
import com.syscxp.sdk.common.ZSClient;
import com.syscxp.sdk.common.ZSConfig;
import com.syscxp.sdk.tunnel.ExtMonitorIcmpDataAction;
import com.syscxp.sdk.tunnel.ExtMonitorIcmpDataResult;
import com.syscxp.sdk.tunnel.GetL3VlanAutoAction;
import com.syscxp.sdk.tunnel.GetL3VlanAutoResult;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class UserMapperTest extends ApplicationTest {

    @Autowired
    private UserMapper userDao;

    @Test
    public void testUser(){
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = format.format(date);

        String SecretId = "accountTJj9YPFmWDcPJbyF";
        String SecretKey = "nD46UF0MB2qgjTGU5T5oZPfbEr0tZK";

        long now=System.currentTimeMillis();
        ZSConfig.Builder builder = new ZSConfig.Builder()
                .setDefaultPollingTimeout(1, TimeUnit.HOURS)
                .setSecret(SecretId, SecretKey);
        ZSClient.configure(builder.build());

        ExtMonitorIcmpDataAction action = new ExtMonitorIcmpDataAction();
        action.extMonitorUuid = "308df8d7935c41009be1e6635c9bf78b";
        action.start = now - 10 * 6000;
        action.end = now;

        ExtMonitorIcmpDataResult result = action.call().throwExceptionIfError().value;
        System.out.println(ZSClient.toPrettyJson(result));
    }

    public static String getMD5(String plainText, int length) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");//获取MD5实例
            md.update(plainText.getBytes());//此处传入要加密的byte类型值
            byte[] digest = md.digest();//此处得到的是md5加密后的byte类型值

            /*
               下边的运算就是自己添加的一些二次小加密，记住这个千万不能弄错乱，
                   否则在解密的时候，你会发现值不对的（举例：在注册的时候加密方式是一种，
                在我们登录的时候是不是还需要加密它的密码然后和数据库的进行比对，但是
            最后我们发现，明明密码对啊，就是打不到预期效果，这时候你就要想一下，你是否
             有改动前后的加密方式）
            */
            int i;
            StringBuilder sb = new StringBuilder();
            for (int offset = 0; offset < digest.length; offset++) {
                i = digest[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    sb.append(0);
                sb.append(Integer.toHexString(i));//通过Integer.toHexString方法把值变为16进制
            }
            return sb.toString().substring(0, length);//从下标0开始，length目的是截取多少长度的值
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }


}
