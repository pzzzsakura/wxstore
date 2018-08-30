package com.irecssa.mmns.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;


/**
 * @Title ProvinceAndCityUtils
 * @Date Created in 2017/7/18
 * @Author:Back.T
 * @Description:
 */
public class ProvinceAndCityUtils {

  private static final String source = Thread.currentThread().getContextClassLoader()
      .getResource("/").getPath();


  //各地区xml文件路径
  private static final String LOCAL_LIST_PATH = getLocalPath();
  //所有国家名称List
  private static final List<String> COUNTRY_REGION = new ArrayList<String>();
  private static ProvinceAndCityUtils localutil;

  private SAXReader reader;
  private Document document;
  private Element rootElement;        //根元素

  //初始化
  private ProvinceAndCityUtils() {
    //1.读取
    reader = new SAXReader();
    try {
      document = reader.read(LOCAL_LIST_PATH);

    } catch (DocumentException e) {
      e.printStackTrace();
    }
    //2.获得根元素
    rootElement = document.getRootElement();
    //3.初始化所有国家名称列表
    Iterator it = rootElement.elementIterator();
    Element ele = null;
    while (it.hasNext()) {
      ele = (Element) it.next();
      COUNTRY_REGION.add(ele.attributeValue("Name"));
    }
  }


  private static String getLocalPath() {

    String url = "/local.properties";
    try {
      InputStream in = new BufferedInputStream(new FileInputStream(source + url));
      Properties p = new Properties();
      p.load(in);
      return p.getProperty("local");
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * @return String[]
   * @author LiuJinan
   * @ 功能：	获取所有国家名称
   * @time 2016-8-26 上午9:02:05.
   */
  public List<String> getCountry() {
    return COUNTRY_REGION;
  }

  /**
   * @param countryName 国家名，从getCountry()从取出
   * @return List<Element>
   * @author LiuJinan
   * @ 功能：	根据国家名获取该国所有省份
   * @time 2016-8-26 上午9:07:21
   */
  private List<Element> provinces(String countryName) {

    Iterator it = rootElement.elementIterator();
    List<Element> provinces = new ArrayList<Element>();
    Element ele = null;
    while (it.hasNext()) {
      ele = (Element) it.next();
      COUNTRY_REGION.add(ele.attributeValue("Name"));
      if (ele.attributeValue("Name").equals(countryName)) {
        provinces = ele.elements();
        break;
      }
    }
    return provinces;
  }

  /**
   * @param countryName 国家名，从getCountry()从取出.
   * @return List<Element>
   * @author LiuJinan
   * @功能： 根据国家名获取该国所有省份
   * @time 2016-8-26 上午9:07:21
   */
  public List<String> getProvinces(String countryName) {
    List<Element> tmp = this.provinces(countryName);
    List<String> list = new ArrayList<String>();
    for (int i = 0; i < tmp.size(); i++) {
      list.add(tmp.get(i).attributeValue("Name"));
    }
    return list;
  }

  /**
   * @author LiuJinan
   * @ 功能：根据国家名和省份名，获取该省城市名列表
   * @time 2016-8-26 上午9:15:24
   */
  private List<Element> cities(String countryName, String provinceName) {
    List<Element> provinces = this.provinces(countryName);
    List<Element> cities = new ArrayList<Element>();
    if (provinces == null || provinces.size() == 0) {        //没有这个城市
      return cities;
    }

    for (int i = 0; i < provinces.size(); i++) {
      if (provinces.get(i).attributeValue("Name").equals(provinceName)) {
        cities = provinces.get(i).elements();
        break;
      }
    }
    return cities;
  }

  /**
   * 判断区是否正确
   */
  private Boolean Region(String countryName, String provinceName, String cityName,
      String regionsName) {
    List<Element> provinces = this.provinces(countryName);
    List<Element> cities = this.cities(countryName, provinceName);
    List<Element> regions = new ArrayList<Element>();
    if (provinces == null || provinces.size() == 0) {        //没有这个城市
      return false;
    } else if (cities == null || cities.size() == 0) {
      return false;
    }

    for (int i = 0; i < cities.size(); i++) {
      if (cities.get(i).attributeValue("Name").equals(cityName)) {
        regions = cities.get(i).elements();
        break;
      }
    }
    if (regions.size() == 0 && (regionsName == null || regionsName == "")) {
      return true;
    } else if (regions.size() > 0 && regionsName == null) {
      return true;
    }
    for (int i = 0; i < regions.size(); i++) {
      if (regions.get(i).attributeValue("Name").equals(regionsName)) {

        return true;
      }
    }
    return false;
  }


  /**
   * @return List<String>
   * @author LiuJinan
   * @ 功能：根据国家名和省份名获取城市列表
   * @time 2016-8-26 下午4:55:55
   */
  public List<String> getCities(String countryName, String provinceName) {
    List<Element> tmp = this.cities(countryName, provinceName);
    List<String> cities = new ArrayList<String>();
    for (int i = 0; i < tmp.size(); i++) {
      cities.add(tmp.get(i).attributeValue("Name"));
    }
    return cities;
  }

  public static Boolean IsRight(String local) {

    String[] locals = local.split(" ");
    String provice = locals[0];
    String city = locals[1];
    String area = null;
    if (locals.length == 3) {
      area = locals[2];
    }
    if (!(provice == "" || city == "" || provice == null
        || city == null)) {
      List<String> CityList = getInstance().getCities("中国", provice);
      if (CityList.contains(city)) {

        Boolean isReg = getInstance().Region("中国", provice, city, area);
        return isReg;

      } else {
        return false;
      }
    } else {
      return false;
    }
  }

  public static ProvinceAndCityUtils getInstance() {
    if (localutil == null) {
      localutil = new ProvinceAndCityUtils();
    }
    return localutil;
  }


}
