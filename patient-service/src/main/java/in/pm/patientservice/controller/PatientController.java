package in.pm.patientservice.controller;

import in.pm.patientservice.dto.PatientRequestDTO;
import in.pm.patientservice.dto.PatientResponseDTO;
import in.pm.patientservice.dto.validators.CreatePatientValidationGroup;
import in.pm.patientservice.service.PatientService;
import jakarta.validation.Valid;
import jakarta.validation.groups.Default;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/patients")
public class PatientController {
    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping
    public ResponseEntity<List<PatientResponseDTO>> getpatients() {
        return ResponseEntity.ok().body(patientService.getPatientList());
    }

    /**
     * The @Validated({Default.class, CreatePatientValidationGroup.class}) annotation applies
     * multiple validation groups to the 'patientRequestDTO' object before the method logic is executed.
     *
     * - The Default.class group applies any default validation constraints that are not explicitly
     *   assigned to a specific group. This typically includes basic validation such as not null or not blank.
     * - The CreatePatientValidationGroup.class is a custom validation group that specifically applies
     *   validation rules for creating a new patient, such as ensuring required fields (e.g., 'registeredOn')
     *   are populated only during patient creation.
     *
     * This allows fine-grained control over which validations are applied depending on the context
     * (e.g., creation vs. update). If the input data passes all validations, the patient is created
     * and a PatientResponseDTO is returned.
     */
    @PostMapping
    public ResponseEntity<PatientResponseDTO> createPatient(@Validated({Default.class , CreatePatientValidationGroup.class}) @RequestBody PatientRequestDTO patientRequestDTO) {
        PatientResponseDTO patientResponseDTO = patientService.createPatient(patientRequestDTO);
        return ResponseEntity.ok().body(patientResponseDTO);
    }

    /**
     * The @Validated({Default.class}) annotation ensures that the validation rules defined
     * in the Default validation group are applied to the 'patientRequestDTO' object before the
     * method logic is executed. This ensures that any constraints without a specific validation
     * group are validated, such as required fields or format checks.
     *
     * The validation will be triggered on the input data to make sure it adheres to the required
     * constraints before updating the patient details.
     */
    @PutMapping("{id}")
    public ResponseEntity<PatientResponseDTO> updatePatient(@Validated({Default.class}) @RequestBody PatientRequestDTO patientRequestDTO, @PathVariable UUID id) {
        return ResponseEntity.ok().body(patientService.updatePatient(id, patientRequestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable UUID id) {
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }

}
