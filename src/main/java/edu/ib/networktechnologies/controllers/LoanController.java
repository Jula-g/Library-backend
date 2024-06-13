package edu.ib.networktechnologies.controllers;

import edu.ib.networktechnologies.controllers.dto.loans.*;
import edu.ib.networktechnologies.services.JwtService;
import edu.ib.networktechnologies.services.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loan")
@PreAuthorize("isAuthenticated()")
public class LoanController {

    private final LoanService loanService;
    private final JwtService jwtservice;

    @Autowired
    public LoanController(LoanService loanService, JwtService jwtService) {
        this.loanService = loanService;
        this.jwtservice = jwtService;
    }

    @PostMapping("/create")
    public ResponseEntity<CreateLoanResponseDto> addLoan(@RequestHeader("Authorization") String token, @RequestBody CreateLoanDto loan) {
        var newLoan = loanService.create(jwtservice.getUserIdFromToken(token), loan);
        return new ResponseEntity<>(newLoan, HttpStatus.CREATED);
    }
    @GetMapping("/getAll")
    public ResponseEntity<List<GetLoanResponseDto>> getAll(@RequestHeader("Authorization") String token) {
        long userId = jwtservice.getUserIdFromToken(token);
        List<GetLoanResponseDto> dto = loanService.getAll(userId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetLoanResponseDto> getOne(@PathVariable long id) {
        GetLoanResponseDto dto = loanService.getOne(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        loanService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/update")
    public ResponseEntity<UpdateLoanResponseDto> update(@RequestBody UpdateLoanDto loan) {
        UpdateLoanResponseDto dto = loanService.updateLoan(loan);
        return ResponseEntity.ok(dto);
    }
}
