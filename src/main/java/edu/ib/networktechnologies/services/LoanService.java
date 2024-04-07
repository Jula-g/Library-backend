package edu.ib.networktechnologies.services;


import edu.ib.networktechnologies.controllers.dto.user.GetUserDto;
import edu.ib.networktechnologies.controllers.dto.books.GetBookDto;
import edu.ib.networktechnologies.controllers.dto.loans.CreateLoanDto;
import edu.ib.networktechnologies.controllers.dto.loans.CreateLoanResponseDto;
import edu.ib.networktechnologies.controllers.dto.loans.GetLoanResponseDto;
import edu.ib.networktechnologies.entities.Book;
import edu.ib.networktechnologies.entities.Loan;
import edu.ib.networktechnologies.entities.User;
import edu.ib.networktechnologies.exceptions.BookNotFoundException;
import edu.ib.networktechnologies.exceptions.LoanNotFoundException;
import edu.ib.networktechnologies.exceptions.UserNotFoundException;
import edu.ib.networktechnologies.repositories.AuthRepository;
import edu.ib.networktechnologies.repositories.BookRepository;
import edu.ib.networktechnologies.repositories.LoanRepository;
import edu.ib.networktechnologies.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoanService extends OwnershipService {
    
    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    @Autowired
    public LoanService(LoanRepository loanRepository, BookRepository bookRepository, UserRepository userRepository, AuthRepository authRepository) {
        super(authRepository);
        this.loanRepository = loanRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    @PreAuthorize("hasRole('ADMIN') or this.isOwner(authentication.name, #userId)")
    public List<GetLoanResponseDto> getAll(Long userId) {
        List<Loan> loans;

        if(userId == null) {
            loans = loanRepository.findAll();
        } else {
            loans = loanRepository.findByUser_UserId(userId);
        }

        return loans.stream().map(this::mapLoan).collect(Collectors.toList());
    }

    @PostAuthorize("hasRole('ADMIN') or this.isOwner(authentication.name, returnObject.user.id)")
    public GetLoanResponseDto getOne(long id) {
        Loan loan = loanRepository.findById(id).orElseThrow(() -> LoanNotFoundException.create(id));
        return mapLoan(loan);
    }

    @PreAuthorize("hasRole('ADMIN') or this.isOwner(authentication.name, #loanDto.userId)")
    public CreateLoanResponseDto create(CreateLoanDto loanDto) {
        User user = userRepository.findById(loanDto.getUserId()).orElseThrow(() -> UserNotFoundException.createWithId(loanDto.getUserId()));
        Book book = bookRepository.findById(loanDto.getBookId()).orElseThrow(() -> BookNotFoundException.create(loanDto.getBookId()));

        Loan loan = new Loan();
        loan.setBook(book);
        loan.setUser(user);
        loan.setLoanDate(new Date(System.currentTimeMillis()));
        loan.setDueDate(loanDto.getDueDate());
        loanRepository.save(loan);

        return new CreateLoanResponseDto(loan.getLoanId(), loan.getLoanDate(), loan.getDueDate(), loan.getUser().getUserId(), loan.getBook().getBookId());
    }
    public void delete(long id) {
        loanRepository.deleteById(id);
    }

    private GetLoanResponseDto mapLoan(Loan loan) {
        GetUserDto user = new GetUserDto(
                loan.getUser().getUserId(),
                loan.getUser().getName(),
                loan.getUser().getLastName(),
                loan.getUser().getEmail());
        GetBookDto book = new GetBookDto(
                loan.getBook().getBookId(),
                loan.getBook().getIsbn(),
                loan.getBook().getTitle(),
                loan.getBook().getAuthor(),
                loan.getBook().getPublisher(),
                loan.getBook().getYearPublished(),
                loan.getBook().getAvailableCopies() > 0);
        return new GetLoanResponseDto(loan.getLoanId(), loan.getLoanDate(), loan.getDueDate(), user, book);
    }
}
