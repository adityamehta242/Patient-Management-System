package in.pm.patientservice.service;

import in.pm.patientservice.dto.PatientRequestDTO;
import in.pm.patientservice.dto.PatientResponseDTO;
import in.pm.patientservice.exception.EmailAlreadyExistException;
import in.pm.patientservice.mapper.PatientMapper;
import in.pm.patientservice.model.Patient;
import in.pm.patientservice.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {
    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public List<PatientResponseDTO> getPatientList() {
        List<Patient> patientList = patientRepository.findAll();
        return patientList.stream().map(PatientMapper::toDTO).toList();
    }


    public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO) {
        if(patientRepository.existsByEmail(patientRequestDTO.getEmail()))
        {
            throw new EmailAlreadyExistException("Email already Present. "+ patientRequestDTO.getEmail());
        }
        Patient newPatient = patientRepository.save(PatientMapper.toPatient(patientRequestDTO));

        return PatientMapper.toDTO(newPatient);
    }
}
