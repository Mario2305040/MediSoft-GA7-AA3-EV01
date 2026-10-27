package co.medisoft.aa3.repository;

import co.medisoft.aa3.model.Cita;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

/** Ejecuta las operaciones CRUD con JDBC parametrizado a través de JdbcTemplate. */
@Repository
public class CitaRepository {
    private static final String SELECT_COLUMNAS =
            "id, documento_paciente, nombre_paciente, especialidad, medico, fecha_hora, estado";
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Cita> mapper = (resultSet, rowNumber) -> {
        Cita cita = new Cita();
        cita.setId(resultSet.getLong("id"));
        cita.setDocumentoPaciente(resultSet.getString("documento_paciente"));
        cita.setNombrePaciente(resultSet.getString("nombre_paciente"));
        cita.setEspecialidad(resultSet.getString("especialidad"));
        cita.setMedico(resultSet.getString("medico"));
        cita.setFechaHora(resultSet.getTimestamp("fecha_hora").toLocalDateTime());
        cita.setEstado(resultSet.getString("estado"));
        return cita;
    };

    public CitaRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /** Retorna el listado completo en orden cronológico. */
    public List<Cita> listarTodas() {
        return jdbcTemplate.query("SELECT " + SELECT_COLUMNAS + " FROM citas ORDER BY fecha_hora, id", mapper);
    }

    /** Busca una cita por su llave primaria. */
    public Optional<Cita> buscarPorId(long id) {
        return jdbcTemplate.query("SELECT " + SELECT_COLUMNAS + " FROM citas WHERE id = ?", mapper, id)
                .stream().findFirst();
    }

    /** Inserta una cita y asigna la llave primaria generada por MySQL. */
    public void insertar(Cita cita) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            var statement = connection.prepareStatement(
                    "INSERT INTO citas (documento_paciente, nombre_paciente, especialidad, medico, fecha_hora, estado) "
                    + "VALUES (?, ?, ?, ?, ?, ?)", new String[] {"id"});
            asignarParametros(statement, cita);
            return statement;
        }, keyHolder);
        if (keyHolder.getKey() != null) {
            cita.setId(keyHolder.getKey().longValue());
        }
    }

    /** Actualiza los datos de una cita existente. */
    public boolean actualizar(long id, Cita cita) {
        int filas = jdbcTemplate.update(
                "UPDATE citas SET documento_paciente = ?, nombre_paciente = ?, especialidad = ?, medico = ?, "
                        + "fecha_hora = ?, estado = ? WHERE id = ?",
                cita.getDocumentoPaciente(), cita.getNombrePaciente(), cita.getEspecialidad(), cita.getMedico(),
                Timestamp.valueOf(cita.getFechaHora()), cita.getEstado(), id);
        return filas > 0;
    }

    /** Elimina una cita por su llave primaria. */
    public boolean eliminar(long id) {
        return jdbcTemplate.update("DELETE FROM citas WHERE id = ?", id) > 0;
    }

    private static void asignarParametros(java.sql.PreparedStatement statement, Cita cita)
            throws java.sql.SQLException {
        statement.setString(1, cita.getDocumentoPaciente());
        statement.setString(2, cita.getNombrePaciente());
        statement.setString(3, cita.getEspecialidad());
        statement.setString(4, cita.getMedico());
        statement.setTimestamp(5, Timestamp.valueOf(cita.getFechaHora()));
        statement.setString(6, cita.getEstado());
    }
}
