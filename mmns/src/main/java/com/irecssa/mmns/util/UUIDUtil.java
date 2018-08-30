package com.irecssa.mmns.util;

import java.util.Random;
import java.util.UUID;

/**
 * Ma.li.ran 2017/11/20 0020 16:11
 */
public class UUIDUtil {

  public static String createUUID(){
    return UUID.randomUUID().toString().replace("-","").substring(0,32);
  }

  public static String createNum(){
  return Integer.toString(new Random().nextInt(89999)+10000)+Long.toString(System.currentTimeMillis());
  }
//6大写字母+6随机数+时间戳
  public static String createOrderNum(){
    return (UUID.randomUUID().toString().replace("-","").substring(0,6).toUpperCase())
        +(Integer.toString(new Random().nextInt(899999)+100000))
    +(Long.toString(System.currentTimeMillis()));
  }

  public static void main(String[] args) {
    System.out.println(UUIDUtil.createUUID());
    System.out.println(UUIDUtil.createNum());
  }
}
