package com.irecssa.mmns.service;

import com.irecssa.mmns.entity.Track;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/11/29 10:30
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public interface TrackService {

  int insertTrack(Track track);

  Track queryTrack(String proorderId);
}
