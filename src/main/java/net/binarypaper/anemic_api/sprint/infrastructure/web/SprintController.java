package net.binarypaper.anemic_api.sprint.infrastructure.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("sprints")
public class SprintController {

  @GetMapping
  public String viewAllSprints(
      @RequestHeader(name = "Hx-Request", required = false) String hxRequest, Model model) {
    if (hxRequest != null) {
      return "sprint/sprints :: main";
    }
    return "sprint/sprints";
  }
}
