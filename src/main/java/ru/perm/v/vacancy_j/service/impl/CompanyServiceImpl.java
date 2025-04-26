package ru.perm.v.vacancy_j.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.perm.v.vacancy_j.dto.CompanyDto;
import ru.perm.v.vacancy_j.entity.CompanyEntity;
import ru.perm.v.vacancy_j.mapper.CompanyMapper;
import ru.perm.v.vacancy_j.repository.ICompanyRepository;
import ru.perm.v.vacancy_j.service.CompanyService;

import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService {
    @Autowired
    private ICompanyRepository companyRepository;
    private CompanyMapper companyMapper = new CompanyMapper();
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
            return  companyMapper.toDto(companies.get(0));
        }
    }

    @Override
    public List<CompanyDto> getAll() {
        List<CompanyEntity> entites = companyRepository.findAll();
        return companyMapper.toListDto(entites);
    }

    @Override
    public List<CompanyDto> findByLikeName(String name) {
        List<CompanyEntity> entites = companyRepository.findByLikeName(name);
        return companyMapper.toListDto(entites);
    }

    @Override
    public CompanyDto create(CompanyDto companyDto) {
        CompanyEntity companyEntity = companyMapper.toEntity(companyDto);
        companyEntity.setN(getNextN());
        CompanyEntity createdEntity = companyRepository.save(companyEntity);
        return companyMapper.toDto(createdEntity);
    }

    @Override
    public Long getNextN() {
        Long nextN = companyRepository.getMaxN();
        if(nextN == null) {
            nextN = 0L;
        }
        return nextN + 1L;
    }
}
