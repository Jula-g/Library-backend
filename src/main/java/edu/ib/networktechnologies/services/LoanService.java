package edu.ib.networktechnologies.services;


import edu.ib.networktechnologies.controllers.dto.books.BookDetailsDto;
import edu.ib.networktechnologies.controllers.dto.books.GetBookDetailsDto;
import edu.ib.networktechnologies.controllers.dto.loans.*;
import edu.ib.networktechnologies.controllers.dto.user.GetUserDto;
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
import java.time.LocalDate;
import java.util.Collections;
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

        if (userId == null) {
            return Collections.emptyList();
        }

        loans = loanRepository.findByUser_UserId(userId);

        return loans.stream().map(this::mapLoan).collect(Collectors.toList());
    }

    @PostAuthorize("hasRole('ADMIN') or this.isOwner(authentication.name, returnObject.user.id)")
    public GetLoanResponseDto getOne(long id) {
        Loan loan = loanRepository.findById(id).orElseThrow(() -> LoanNotFoundException.create(id));
        return mapLoan(loan);
    }

    public CreateLoanResponseDto create(long userId, CreateLoanDto loanDto) {
        User user = userRepository.findById(userId).orElseThrow(() -> UserNotFoundException.createWithId(userId));
        Book book = bookRepository.findById(loanDto.getBookId()).orElseThrow(() -> BookNotFoundException.create(loanDto.getBookId()));

        if (getAll(userId).stream().anyMatch(loan -> loan.getBook().getBookId() == loanDto.getBookId())) {
            throw new IllegalArgumentException("User already has this book");
        }

        if (book.getAvailableCopies() > 0) {
            book.setAvailableCopies(book.getAvailableCopies() - 1);
            bookRepository.save(book);
        } else {
            throw new IllegalArgumentException("No available copies of this book");
        }
        List<Loan> allLoans = loanRepository.findByUser_UserId(userId);
        List<Loan> notReturnedLoans = allLoans.stream().filter(loan -> loan.getReturnDate() == null).toList();
        if(notReturnedLoans.toArray().length > 3){
            throw new IllegalArgumentException("User has too many books");
        }

        Loan loan = new Loan();
        loan.setBook(book);
        loan.setUser(user);
        loan.setLoanDate(new Date(System.currentTimeMillis()));
        loan.setDueDate(new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 30));
        loanRepository.save(loan);

        return new CreateLoanResponseDto(loan.getLoanDate(), loan.getDueDate(), loan.getUser().getUserId(), loan.getBook().getBookId());
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
        GetBookDetailsDto book = new GetBookDetailsDto(
                loan.getBook().getBookId(),
                loan.getBook().getIsbn(),
                loan.getBook().getTitle(),
                loan.getBook().getAuthor(),
                loan.getBook().getPublisher(),
                loan.getBook().getYearPublished(),
                loan.getBook().getAvailableCopies(),
                new BookDetailsDto(
                        loan.getBook().getBookDetails().getGenre(),
                        loan.getBook().getBookDetails().getSummary(),
                        loan.getBook().getBookDetails().getCoverImageURL()));
        return new GetLoanResponseDto(loan.getLoanId(), loan.getLoanDate(), loan.getDueDate(), user, book, loan.getReturnDate());
    }

    public UpdateLoanResponseDto updateLoan(UpdateLoanDto loanDto) {
        Book book = bookRepository.findById(loanDto.getBookId()).orElseThrow(() -> BookNotFoundException.create(loanDto.getBookId()));

        book.setAvailableCopies(book.getAvailableCopies() + 1);
        bookRepository.save(book);

        Loan loan = loanRepository.findById(loanDto.getLoanId()).orElseThrow(() -> LoanNotFoundException.create(loanDto.getLoanId()));
        loan.setReturnDate(Date.valueOf(LocalDate.now()));
        loanRepository.save(loan);

        return new UpdateLoanResponseDto(loan.getLoanDate(), loan.getDueDate(), loan.getUser().getUserId(), loan.getBook().getBookId(), loan.getReturnDate());
    }
}
