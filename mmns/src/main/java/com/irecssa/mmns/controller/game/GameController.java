package com.irecssa.mmns.controller.game;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/12/05 22:22
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
@Controller
@RequestMapping("/game")
public class GameController {

  @RequestMapping("/qmmshare")
  private String qmmShare() {
    return "game/qmmshare";
  }

  @RequestMapping("/dzpshare")
  private String dzpShare() {
    return "game/dzpshare";
  }

  @RequestMapping("/mmnsshare")
  private String mmnsShare() {
    return "game/mmnsshare";
  }
}
