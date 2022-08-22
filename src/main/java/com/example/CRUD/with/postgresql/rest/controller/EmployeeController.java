package com.example.CRUD.with.postgresql.rest.controller;

import com.example.CRUD.with.postgresql.config.password.PasswordUtil;
import com.example.CRUD.with.postgresql.rest.exception.ResourceNotFound;
import com.example.CRUD.with.postgresql.rest.dtos.EmployeeDTO;
import com.example.CRUD.with.postgresql.model.Employee;
import com.example.CRUD.with.postgresql.model.PasswordResetToken;
import com.example.CRUD.with.postgresql.rest.mapper.EmployeeMapperImpl;
import com.example.CRUD.with.postgresql.service.EmployeeService;
import com.example.CRUD.with.postgresql.service.PasswordTokenService;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v2/employees")
@Tag( name = "Employees",description ="Rest Api For Employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private PasswordTokenService passwordTokenService;
    @Autowired
    private EmployeeMapperImpl employeeMapper;
    @Autowired
    private PasswordUtil passwordUtil;

    @GetMapping
    @Operation(summary = "This method is used to get all employees.")
    public ResponseEntity<?> getAllEmployees() {
            List<EmployeeDTO> employeeDTOList = employeeMapper.toEmployeeDTOs(employeeService.getAllEmployees());
            return ResponseEntity.status(HttpStatus.CREATED).body(employeeDTOList);
    }

    @Operation(summary = "This method is used to get employee.")
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getDepartmentById(@PathVariable String id)
            throws ResourceNotFound {
        Employee employee = employeeService.getEmployeeByEmail(id)
                .orElseThrow(() -> new ResourceNotFound("Employee Not Found"));
        EmployeeDTO employeeDTO = employeeMapper.toEmployeeDTO(employee);
        return ResponseEntity.ok(employeeDTO);
    }

    @PostMapping("/forgetPassword")
    @Operation(summary = "This method for if user forget password.")
    public ResponseEntity<?> forgetPassword(@RequestBody Employee employee ) throws ResourceNotFound {
        Employee user = employeeService.getEmployeeByEmail(employee.getEmail()).get();
        System.out.println("\n\n\n USER ||  "+user.getEmail()+"\n\n\n");
        if (user == null) {
            throw new ResourceNotFound("Not Found");
        }
        String token = UUID.randomUUID().toString();
        System.out.println("Reset Password:  "+token);
        passwordTokenService.createPasswordResetTokenForUser(user, token);
        //mailSender.send(constructResetTokenEmail(getAppUrl(request),
          //      request.getLocale(), token, user));
        return ResponseEntity.ok().body(token);
    }

    @PutMapping ("/resetpassword/{resetToken}")
    @Operation(summary = "This method is used to reset password.")
    public ResponseEntity<?> resetPassword(@RequestBody Employee employee, @PathVariable String resetToken){

        PasswordResetToken token = passwordTokenService.getResetToken(resetToken).get();

//        BCryptPasswordEncoder passwordEncoder =new BCryptPasswordEncoder();
//        if(! passwordEncoder.matches(resetToken,token.getToken())){
//            return ResponseEntity.status(404).body("This reset token not found......");
//        }

        if(token.getToken().isEmpty()){
            return ResponseEntity.status(404).body("This reset token not found......");
        }
        String result = passwordUtil.validatePasswordResetToken(resetToken);
        if(result != null){
          return  ResponseEntity.status(401).body("Reset Token Expired......");
        }
        String email = token.getEmployee().getEmail();
//        Employee employee1 = employeeService.getEmployeeByEmail(email).get();
        employeeService.updatePassword(email,employee.getPassword());

        return ResponseEntity.status(200).body("the password changed.....");
    }

    @PostMapping("/register")
    @Operation(summary = "This method is used sign up.")
    public ResponseEntity<EmployeeDTO> createEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee employee = employeeMapper.toEmployee(employeeDTO);
        employeeService.createEmployee(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeDTO);
    }

    @PatchMapping("/updatePassword/{newPassword}")
    @Operation(summary = "This method is used change password.")
    public ResponseEntity<?> changeUserPassword(@RequestBody Employee employee,@PathVariable String newPassword) {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Employee employeeId = employeeService.getEmployeeByEmail(email).get();
        BCryptPasswordEncoder passwordEncoder =new BCryptPasswordEncoder();

        if(passwordEncoder.matches(employee.getPassword(),employeeId.getPassword())){
            employeeService.updatePassword(email,newPassword);
            return ResponseEntity.status(200).body("successfully updated......");
        }
        else return ResponseEntity.status(403).body("incorrect password");
    }

    @PutMapping("/{id}")
    @Operation(summary = "This method is used to update employee profile.")
    public ResponseEntity<EmployeeDTO> update(@PathVariable String id, @RequestBody EmployeeDTO employeeDTO) throws ResourceNotFound {
        Employee employee = employeeMapper.toEmployee(employeeDTO);

        Employee employeeId = employeeService.getEmployeeByEmail(id)
                .orElseThrow(() -> new ResourceNotFound("Employee Not Found"));

        employee.setEmail(id);
        employee.setName(employee.getName() != null ? employee.getName() : employeeId.getName());
        employee.setPassword(employeeId.getPassword());
        employee.setDepartment(employee.getDepartment() != null ? employee.getDepartment() : employeeId.getDepartment());
        employee.setJob(employee.getJob() != null ? employee.getJob() : employeeId.getJob());
        employee.setRoles(employee.getRoles() != null ? employee.getRoles() : employeeId.getRoles());
        employeeService.update(employee);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(employeeDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "This method is used to delete employee.")
    public ResponseEntity<?> delete(@PathVariable String id) {
        employeeService.delete(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
