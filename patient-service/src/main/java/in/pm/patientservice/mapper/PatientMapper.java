package in.pm.patientservice.mapper;

import in.pm.patientservice.dto.PatientRequestDTO;
import in.pm.patientservice.dto.PatientResponseDTO;
import in.pm.patientservice.model.Gender;
import in.pm.patientservice.model.Patient;

import java.time.LocalDate;

public class PatientMapper {
    public static PatientResponseDTO toDTO(Patient patient) {
        PatientResponseDTO patientDTO = new PatientResponseDTO();
        patientDTO.setId(patient.getId().toString());
        patientDTO.setName(patient.getName());
        patientDTO.setGender(patient.getGender().toString());
        patientDTO.setAddress(patient.getAddress());
        patientDTO.setEmail(patient.getEmail());
        patientDTO.setDateOfBirth(patient.getDateOfBirth().toString());
        return patientDTO;
    }

    public static Patient toPatient(PatientRequestDTO patientRequestDTO) {
        Patient patient = new Patient();

        patient.setName(patientRequestDTO.getName());
        patient.setAddress(patientRequestDTO.getAddress());
        patient.setEmail(patientRequestDTO.getEmail());
        patient.setDateOfBirth(LocalDate.parse(patientRequestDTO.getDateOfBirth()));
        patient.setGender(Gender.valueOf(patientRequestDTO.getGender()));
        patient.setRegisteredOn(LocalDate.parse(patientRequestDTO.getRegisteredOn()));
        return patient;
    }
}
