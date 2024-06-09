package med.voll.api.domain.consulta;

import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRespository;
import med.voll.api.domain.paciente.Paciente;
import med.voll.api.domain.paciente.PacienteRepository;
import med.voll.api.infra.errores.ValidacionDeIntegridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgendaDeConsultaService {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private MedicoRespository medicoRespository;

    @Autowired
    private ConsultaRespository consultaRespository;

    public void agendarConsulta(DatosAgendarConsulta datosAgendarConsulta) {


        if (pacienteRepository.findById(datosAgendarConsulta.idPaciente()).isPresent()) {
            throw new ValidacionDeIntegridad("El id de paciente no fue encontrado");
        }
        if (datosAgendarConsulta.idMedico() != null && medicoRespository.existsById(datosAgendarConsulta.idMedico())) {
            throw new ValidacionDeIntegridad("El id de medico no fue encontrado");
        }
        Paciente paciente = pacienteRepository.findById(datosAgendarConsulta.idMedico()).get();

        Medico medico = seleccionarMedico(datosAgendarConsulta);

        Consulta consulta = new Consulta(null, medico, paciente, datosAgendarConsulta.fecha());

        consultaRespository.save(consulta);

    }

    private Medico seleccionarMedico(DatosAgendarConsulta datosAgendarConsulta) {
        if (datosAgendarConsulta.idMedico() != null){
            return medicoRespository.getReferenceById(datosAgendarConsulta.idMedico());
        }
        if (datosAgendarConsulta.especialidad() == null){
            throw new ValidacionDeIntegridad("Debe seleccionarse una especialidad para el medico");
        }

        return medicoRespository.obtenerMedicoPorEspecialidadYFecha(datosAgendarConsulta.especialidad(), datosAgendarConsulta.fecha());
    }
}
