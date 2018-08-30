package com.irecssa.mmns.dao;

import com.irecssa.mmns.entity.Track;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/11/29 10:25
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public interface TrackDao {

  int insertTrack(Track track);

  Track queryTrack(String proorderId);
}
