package in.pm.patientservice.repository;


import in.pm.patientservice.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PatientRepository extends JpaRepository<Patient, UUID> {
    boolean existsByEmail(String email);
    String findByEmail(String email);
    boolean existsByEmailAndIdNot(String email, UUID id);
}
