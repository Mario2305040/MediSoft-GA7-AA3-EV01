package co.medisoft.aa3.service;

import co.medisoft.aa3.model.Cita;
import co.medisoft.aa3.repository.CitaRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Coordina las reglas de aplicación para administrar citas. */
@Service
@Transactional
public class CitaService {
    private final CitaRepository citaRepository;

    public CitaService(CitaRepository citaRepository) {
        this.citaRepository = citaRepository;
    }

    @Transactional(readOnly = true)
    public List<Cita> listarTodas() { return citaRepository.listarTodas(); }

    @Transactional(readOnly = true)
    public Optional<Cita> buscarPorId(long id) { return citaRepository.buscarPorId(id); }

    public void guardar(Cita cita) { citaRepository.insertar(cita); }

    public boolean actualizar(long id, Cita cita) { return citaRepository.actualizar(id, cita); }

    public boolean eliminar(long id) { return citaRepository.eliminar(id); }
}
