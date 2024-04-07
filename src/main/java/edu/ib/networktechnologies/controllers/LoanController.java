package edu.ib.networktechnologies.controllers;

import edu.ib.networktechnologies.controllers.dto.loans.CreateLoanDto;
import edu.ib.networktechnologies.controllers.dto.loans.CreateLoanResponseDto;
import edu.ib.networktechnologies.controllers.dto.loans.GetLoanResponseDto;
import edu.ib.networktechnologies.services.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loan")
@PreAuthorize("isAuthenticated()")
public class LoanController {

    private final LoanService loanService;
    @Autowired
    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @PostMapping("/create")
    public ResponseEntity<CreateLoanResponseDto> addLoan(@Validated @RequestBody CreateLoanDto loan) {
        var newLoan = loanService.create(loan);
        return new ResponseEntity<>(newLoan, HttpStatus.CREATED);
    }
    @GetMapping("/getAll")
    public ResponseEntity<List<GetLoanResponseDto>> getAll(@RequestParam(required = false) Long userId) {
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

}
