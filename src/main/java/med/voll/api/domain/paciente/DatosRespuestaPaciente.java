package med.voll.api.domain.paciente;

import med.voll.api.domain.direccion.Direccion;

public record DatosRespuestaPaciente(String nombre, String email, String telefono, String documentoIdentidad, Direccion direccion) {
    public DatosRespuestaPaciente(Paciente paciente) {
        this(paciente.getNombre(), paciente.getEmail(), paciente.getTelefono(), paciente.getDocumentoIdentidad(), paciente.getDireccion());
    }
}