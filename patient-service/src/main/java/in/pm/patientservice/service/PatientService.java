package in.pm.patientservice.service;

import in.pm.patientservice.dto.PatientRequestDTO;
import in.pm.patientservice.dto.PatientResponseDTO;
import in.pm.patientservice.exception.EmailAlreadyExistException;
import in.pm.patientservice.exception.PatientNotFoundException;
import in.pm.patientservice.mapper.PatientMapper;
import in.pm.patientservice.model.Gender;
import in.pm.patientservice.model.Patient;
import in.pm.patientservice.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

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
        if (patientRepository.existsByEmail(patientRequestDTO.getEmail())) {
            throw new EmailAlreadyExistException("Email already Present. " + patientRequestDTO.getEmail());
        }
        Patient newPatient = patientRepository.save(PatientMapper.toPatient(patientRequestDTO));

        return PatientMapper.toDTO(newPatient);
    }

    public PatientResponseDTO updatePatient(UUID id, PatientRequestDTO patientRequestDTO) {
        Patient patient = patientRepository.findById(id).orElseThrow(()-> new PatientNotFoundException("Patient not found with Id : " +  id));

        /**
         * same patient id and email -> false
         * different patient email and id -> true
         * any email and id -> false
         * patientRepository.existsByEmailAndIdNot(patientRequestDTO.getEmail(),id) is checking , is Email is already use by another patients. if true then throw the exceptions.
         */
        if(patientRepository.existsByEmailAndIdNot(patientRequestDTO.getEmail(),id))
        {
            throw  new EmailAlreadyExistException("Email already Present. " + patientRequestDTO.getEmail());
        }

        patient.setName(patientRequestDTO.getName());
        patient.setGender(Gender.valueOf(patientRequestDTO.getGender()));
        patient.setAddress(patientRequestDTO.getAddress());
        patient.setDateOfBirth(LocalDate.parse(patientRequestDTO.getDateOfBirth()));
        patient.setEmail(patientRequestDTO.getEmail());

        return PatientMapper.toDTO(patientRepository.save(patient));
    }

    public void deletePatient(UUID id) {
        patientRepository.deleteById(id);
    }
}
