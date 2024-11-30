package net.binarypaper.anemic_api.home;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@Controller
public class HomeController {

  @GetMapping
  public String home(@RequestHeader(name = "Hx-Request", required = false) String hxRequest) {
    if (hxRequest != null) {
      return "index :: main";
    }
    return "index";
  }
}
