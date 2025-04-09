package ru.perm.v.vacancy_j.service.impl;

import org.springframework.stereotype.Service;
import ru.perm.v.vacancy_j.dto.CompanyDto;
import ru.perm.v.vacancy_j.entity.CompanyEntity;
import ru.perm.v.vacancy_j.repository.ICompanyRepository;
import ru.perm.v.vacancy_j.service.CompanyService;

import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService {
    private ICompanyRepository companyRepository;
    public CompanyServiceImpl() {
        super();
    }
    public CompanyServiceImpl(ICompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public CompanyDto getByN(Long n) throws Exception {
        List<CompanyEntity> companies = companyRepository.findByN(n);
        if (companies.isEmpty()) {
            throw new Exception("Company not found");
        } else {
            //TODO add mapper?
            return new CompanyDto(companies.get(0).getN(), companies.get(0).getName());
        }
    }
}
