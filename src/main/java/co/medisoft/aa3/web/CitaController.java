package co.medisoft.aa3.web;

import co.medisoft.aa3.model.Cita;
import co.medisoft.aa3.service.CitaService;
import jakarta.validation.Valid;
import java.util.Optional;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/** Controlador MVC que conecta formularios web con el servicio de citas. */
@Controller
@RequestMapping("/citas")
public class CitaController {
    private final CitaService citaService;

    public CitaController(CitaService citaService) {
        this.citaService = citaService;
    }

    /** GET muestra el listado y opcionalmente filtra por identificador. */
    @GetMapping
    public String listar(String id, Model model) {
        if (id != null && !id.isBlank()) {
            try {
                Optional<Cita> resultado = citaService.buscarPorId(Long.parseLong(id));
                resultado.ifPresentOrElse(cita -> model.addAttribute("citas", java.util.List.of(cita)),
                        () -> {
                            model.addAttribute("citas", java.util.List.of());
                            model.addAttribute("mensaje", "No se encontró una cita con ese ID.");
                        });
            } catch (NumberFormatException exception) {
                model.addAttribute("citas", java.util.List.of());
                model.addAttribute("mensaje", "Escribe un ID numérico válido.");
            }
        } else {
            model.addAttribute("citas", citaService.listarTodas());
        }
        return "citas/lista";
    }

    /** GET presenta el formulario vacío para registrar una cita. */
    @GetMapping("/nueva")
    public String nueva(Model model) {
        model.addAttribute("cita", new Cita());
        model.addAttribute("titulo", "Registrar cita");
        model.addAttribute("accion", "/citas");
        return "citas/formulario";
    }

    /** POST valida y persiste el registro nuevo. */
    @PostMapping
    public String crear(@Valid Cita cita, BindingResult resultado, Model model, RedirectAttributes mensajes) {
        if (resultado.hasErrors()) {
            model.addAttribute("titulo", "Registrar cita");
            model.addAttribute("accion", "/citas");
            return "citas/formulario";
        }
        citaService.guardar(cita);
        mensajes.addFlashAttribute("mensaje", "Cita registrada correctamente (ID " + cita.getId() + ").");
        return "redirect:/citas";
    }

    /** GET carga el formulario de edición usando el ID de la ruta. */
    @GetMapping("/{id}/editar")
    public String editar(@PathVariable long id, Model model, RedirectAttributes mensajes) {
        Optional<Cita> cita = citaService.buscarPorId(id);
        if (cita.isEmpty()) {
            mensajes.addFlashAttribute("mensaje", "No se encontró la cita solicitada.");
            return "redirect:/citas";
        }
        model.addAttribute("cita", cita.get());
        model.addAttribute("titulo", "Editar cita " + id);
        model.addAttribute("accion", "/citas/" + id);
        return "citas/formulario";
    }

    /** POST aplica los cambios de un registro. */
    @PostMapping("/{id}")
    public String actualizar(@PathVariable long id, @Valid Cita cita, BindingResult resultado,
                             Model model, RedirectAttributes mensajes) {
        if (resultado.hasErrors()) {
            cita.setId(id);
            model.addAttribute("titulo", "Editar cita " + id);
            model.addAttribute("accion", "/citas/" + id);
            return "citas/formulario";
        }
        if (citaService.actualizar(id, cita)) {
            mensajes.addFlashAttribute("mensaje", "Cita actualizada correctamente.");
        } else {
            mensajes.addFlashAttribute("mensaje", "No se encontró la cita solicitada.");
        }
        return "redirect:/citas";
    }

    /** POST elimina una cita; la operación no se ejecuta mediante GET. */
    @PostMapping("/{id}/eliminar")
    public String eliminar(@PathVariable long id, RedirectAttributes mensajes) {
        mensajes.addFlashAttribute("mensaje", citaService.eliminar(id)
                ? "Cita eliminada correctamente." : "No se encontró la cita solicitada.");
        return "redirect:/citas";
    }
}
