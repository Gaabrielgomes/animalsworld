package animalsworld.api.domain.employee;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("employee")
@PreAuthorize("hasRole('EMPLOYEE')")
public class EmployeeController {

    private final EmployeeService employeeS;

    public EmployeeController(EmployeeService employeeS) {
        this.employeeS = employeeS;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> employeeDetails(@PathVariable Long id) {
        EmployeeInfoDTO e = employeeS.getEmployeeInfoById(id);
        return ResponseEntity.ok(e);
    }

    @GetMapping
    public ResponseEntity<?> listActiveEmployees(@PageableDefault(size = 5, sort = {"name"}) Pageable page) {
        Page<EmployeeListingDTO> currentPage = employeeS.listEmployeesPerPage(page);
        return ResponseEntity.ok(currentPage);
    }

    @PostMapping("/registeremployee")
    public ResponseEntity<?> registerEmployee(@RequestBody @Valid EmployeeRegisterDTO dto, UriComponentsBuilder uriBuilder) {
        Employee e = employeeS.signUp(dto);
        var uri = uriBuilder.path("/employee/{id}").buildAndExpand(e.getId()).toUri();
        return ResponseEntity.created(uri).body(new EmployeeInfoDTO(e));
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody @Valid EmployeeUpdateDTO dto) {
        employeeS.updateEmployeeInfo(dto);
        return ResponseEntity.ok("Info updated.");
    }

    @PatchMapping("/inactive/{id}")
    public ResponseEntity<?> inactive(@PathVariable Long id) {
        employeeS.inactiveEmployeeById(id);
        return ResponseEntity.ok("Employee became inactive.");
    }
}
