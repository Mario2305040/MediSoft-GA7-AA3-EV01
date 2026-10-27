package co.medisoft.aa3.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/** Redirige la página inicial al módulo funcional de citas. */
@Controller
public class InicioController {
    @GetMapping("/")
    public String inicio() {
        return "redirect:/citas";
    }
}
