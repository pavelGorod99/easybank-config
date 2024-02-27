package com.easybytes.loans.service.impl;

import com.easybytes.loans.constants.LoansConstants;
import com.easybytes.loans.dto.LoansDto;
import com.easybytes.loans.entity.Loans;
import com.easybytes.loans.exception.LoanAlreadyExistsException;
import com.easybytes.loans.mapper.LoansMapper;
import com.easybytes.loans.repository.LoansRepository;
import com.easybytes.loans.service.ILoansService;
import com.easybytes.loans.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class LoanServiceImpl implements ILoansService {

    private LoansRepository loansRepository;

    @Override
    public void createLoan(String mobileNumber) {
        Optional<Loans> optionalLoans = loansRepository.findByMobileNumber(mobileNumber);
        if (optionalLoans.isPresent()) {
            throw new LoanAlreadyExistsException("Loan with this phone number " + mobileNumber + " already exits!");
        }

        Loans loans = createNewLoan(mobileNumber);
        loansRepository.save(loans);
    }

    private Loans createNewLoan(String mobileNumber) {
        Loans newLoan = new Loans();
        long randomLoanNumber = 100000000000L + new Random().nextInt(900000000);
        newLoan.setLoanNumber(Long.toString(randomLoanNumber));
        newLoan.setMobileNumber(mobileNumber);
        newLoan.setLoanType(LoansConstants.HOME_LOAN);
        newLoan.setTotalLoan(LoansConstants.NEW_LOAN_LIMIT);
        newLoan.setAmountPaid(0);
        newLoan.setOutstandingAmount(LoansConstants.NEW_LOAN_LIMIT);
        return newLoan;
    }

    @Override
    public LoansDto fetchLoan(String mobileNumber) {
        Loans loans = returnLoanIfThisExists(mobileNumber);
        LoansDto loansDto = LoansMapper.mapToLoansDto(loans, new LoansDto());
        return loansDto;
    }

    @Override
    public boolean updateLoan(LoansDto loansDto) {
        Loans loans = returnLoanIfThisExists(loansDto.getMobileNumber());
        loans = LoansMapper.mapToLoans(loansDto, loans);
        loansRepository.save(loans);
        return true;
    }

    @Override
    public boolean deleteLoan(String mobileNumber) {
        Loans loans = returnLoanIfThisExists(mobileNumber);
        loansRepository.deleteById(loans.getId());
        return true;
    }

    private Loans returnLoanIfThisExists(String mobileNumber) {
        Loans loans = loansRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Loan", "mobileNumber", mobileNumber)
        );
        return loans;
    }
}
