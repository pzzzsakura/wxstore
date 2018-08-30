package com.irecssa.mmns.service.serviceImpl;

import com.irecssa.mmns.dao.TrackDao;
import com.irecssa.mmns.entity.Track;
import com.irecssa.mmns.exceptions.TrackExxception;
import com.irecssa.mmns.service.TrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/11/29 10:31
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
@Service
public class TrackServiceImpl implements TrackService {

  @Autowired
  private TrackDao trackDao;
 @Transactional
  public int insertTrack(Track track) {
    int result;
    try {
      result = trackDao.insertTrack(track);
    }catch (Exception e){
      throw new TrackExxception("insertTrack error"+e.getMessage());
    }
    return result;
  }

  public Track queryTrack(String proorderId) {
    return trackDao.queryTrack(proorderId);
  }
}
