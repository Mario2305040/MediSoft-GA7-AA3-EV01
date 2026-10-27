package co.medisoft.aa3.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

/** Modelo de datos del caso de uso de gestión de citas médicas. */
public class Cita {
    private Long id;

    @NotBlank(message = "El documento del paciente es obligatorio.")
    @Size(max = 30, message = "El documento admite máximo 30 caracteres.")
    private String documentoPaciente;

    @NotBlank(message = "El nombre del paciente es obligatorio.")
    @Size(max = 120, message = "El nombre admite máximo 120 caracteres.")
    private String nombrePaciente;

    @NotBlank(message = "La especialidad es obligatoria.")
    @Size(max = 100, message = "La especialidad admite máximo 100 caracteres.")
    private String especialidad;

    @NotBlank(message = "El médico es obligatorio.")
    @Size(max = 120, message = "El nombre del médico admite máximo 120 caracteres.")
    private String medico;

    @NotNull(message = "La fecha y hora son obligatorias.")
    private LocalDateTime fechaHora;

    @NotBlank(message = "Selecciona el estado de la cita.")
    @Pattern(regexp = "PROGRAMADA|ATENDIDA|CANCELADA", message = "El estado seleccionado no es válido.")
    private String estado = "PROGRAMADA";

    public Cita() { }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getDocumentoPaciente() { return documentoPaciente; }
    public void setDocumentoPaciente(String documentoPaciente) { this.documentoPaciente = documentoPaciente; }
    public String getNombrePaciente() { return nombrePaciente; }
    public void setNombrePaciente(String nombrePaciente) { this.nombrePaciente = nombrePaciente; }
    public String getEspecialidad() { return especialidad; }
    public void setEspecialidad(String especialidad) { this.especialidad = especialidad; }
    public String getMedico() { return medico; }
    public void setMedico(String medico) { this.medico = medico; }
    public LocalDateTime getFechaHora() { return fechaHora; }
    public void setFechaHora(LocalDateTime fechaHora) { this.fechaHora = fechaHora; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
