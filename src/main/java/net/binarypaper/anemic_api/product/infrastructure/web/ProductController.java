package net.binarypaper.anemic_api.product.infrastructure.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("products")
public class ProductController {

  @GetMapping
  public String viewAllProducts(
      @RequestHeader(name = "Hx-Request", required = false) String hxRequest, Model model) {
    if (hxRequest != null) {
      return "product/products :: main";
    }
    return "product/products";
  }
}
