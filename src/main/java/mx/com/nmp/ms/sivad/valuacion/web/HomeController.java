package mx.com.nmp.ms.sivad.valuacion.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author osanchez
 */
@Controller
public class HomeController {

    @RequestMapping("/")
    public String handleRequestHome(ModelAndView modelAndView) {
        modelAndView.setViewName("index");
        return "index.html";
    }

}
