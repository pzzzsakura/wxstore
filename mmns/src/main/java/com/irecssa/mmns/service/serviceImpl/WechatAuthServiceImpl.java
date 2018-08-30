package com.irecssa.mmns.service.serviceImpl;

import com.irecssa.mmns.dao.PersonInfoDao;
import com.irecssa.mmns.dao.WechatAuthDao;
import com.irecssa.mmns.dto.WechatUser;
import com.irecssa.mmns.dto.execution.WechatAuthExecution;
import com.irecssa.mmns.entity.PersonInfo;
import com.irecssa.mmns.entity.WechatAuth;
import com.irecssa.mmns.enums.WechatAuthStateEnum;
import com.irecssa.mmns.exceptions.WechatAuthOperationException;
import com.irecssa.mmns.service.WechatAuthService;
import com.irecssa.mmns.util.wechat.WechatUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Ma.li.ran 2017/11/20 0020 14:46
 */
@Service
public class WechatAuthServiceImpl implements WechatAuthService {

  @Value("${power}")
  private String power;
  @Value("${chancedzp}")
  private String chanceDzp;
  @Value("${chanceqmm}")
  private String chanceQmm;
  @Autowired
  private WechatAuthDao wechatAuthDao;
  @Autowired
  private PersonInfoDao personInfoDao;

  @Transactional
  public WechatAuthExecution insertWechatAuth(WechatUser user, int count) {
    PersonInfo personInfo = WechatUtil.personInfoFromRequest();
    personInfo.setPower(Integer.valueOf(power));
    personInfo.setChanceDzp(Integer.valueOf(chanceDzp));
    personInfo.setChanceQmm(Integer.valueOf(chanceQmm));
    WechatAuth wechatAuth = WechatUtil.wechatAuthFromRequest(personInfo, user, count);
    int result;
    if (wechatAuth != null && wechatAuth.getPersonInfo() != null) {
      try {
        personInfoDao.insertPersonInfo(personInfo);
        wechatAuthDao.insertWechatAuth(wechatAuth);
        return new WechatAuthExecution(WechatAuthStateEnum.SUCCESS, wechatAuth);
      } catch (Exception e) {
        throw new WechatAuthOperationException(e.getMessage());
      }
    } else {
      return new WechatAuthExecution(WechatAuthStateEnum.NULL_AUTH_INFO);
    }

  }

  public WechatAuthExecution getWechatAuthServiceByOpenId(String openId) {
    if ("".equals(openId) || openId == null) {
      return new WechatAuthExecution(WechatAuthStateEnum.LOGINFAIL);
    } else {

      WechatAuth wechatAuth = new WechatAuth();
      wechatAuth = wechatAuthDao.queryWechatAuthByOpenId(openId);
      return new WechatAuthExecution(WechatAuthStateEnum.SUCCESS, wechatAuth);
    }
  }

  public int getWechatAuthCount() {
    int result = wechatAuthDao.queryWechatAuthCount();
    return result + 1;
  }

  public WechatAuthExecution getWechatAuthListOrderByGameIntegral() {
    return new WechatAuthExecution(WechatAuthStateEnum.SUCCESS,wechatAuthDao.queryWechatAuthListOrderByGameIntegral());
  }
}
