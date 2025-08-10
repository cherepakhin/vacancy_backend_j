package ru.perm.v.vacancy_j.service.impl;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.perm.v.vacancy_j.dto.CompanyCriterySearch;
import ru.perm.v.vacancy_j.dto.CompanyDto;
import ru.perm.v.vacancy_j.entity.CompanyEntity;
import ru.perm.v.vacancy_j.entity.VacancyEntity;
import ru.perm.v.vacancy_j.mapper.CompanyMapper;
import ru.perm.v.vacancy_j.repository.ICompanyRepository;
import ru.perm.v.vacancy_j.service.CompanyService;
import ru.perm.v.vacancy_j.specs.CompanySpecifications;
import ru.perm.v.vacancy_j.specs.VacancySpecifications;

import java.util.List;
import java.util.Set;

import static java.lang.String.format;

@Service
public class CompanyServiceImpl implements CompanyService {
    @Autowired
    private ICompanyRepository companyRepository;
    private CompanyMapper companyMapper = new CompanyMapper();

    Logger log = LoggerFactory.getLogger(CompanyServiceImpl.class);

    public CompanyServiceImpl() {
        super();
    }

    public CompanyServiceImpl(ICompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    @Cacheable(value = "company", key = "#n")
    public CompanyDto getByN(Long n) throws Exception {
        log.info(format("get Company by n=%s", n));
        List<CompanyEntity> companies = companyRepository.findByN(n);
        if (companies.isEmpty()) {
            String err = format("Company N=%s not found", n);
            throw new Exception(err);
        } else {
            return companyMapper.toDto(companies.get(0));
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
    public CompanyDto create(CompanyDto companyDto) throws Exception {
        CompanyEntity companyEntity = companyMapper.toEntity(companyDto);
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<CompanyDto>> violations = validator.validate(companyDto);
        if (violations.size() > 0) {
            throw new Exception(violationsToString(violations));
        }
        companyEntity.setN(getNextN());
        CompanyEntity createdEntity = companyRepository.save(companyEntity);
        return companyMapper.toDto(createdEntity);
    }

    private String violationsToString(Set<ConstraintViolation<CompanyDto>> violations) {
        String err = "";
        err = violations.stream().sorted().map(e -> e.getMessage()).reduce("", String::concat);
        return err;
    }

    @Override
    public Long getNextN() {
        Long nextN = companyRepository.getMaxN();
        if (nextN == null) {
            nextN = 0L;
        }
        return nextN + 1L;
    }

    @Override
    public CompanyDto update(CompanyDto companyDto) throws Exception {
        // for check exist company
        List<CompanyEntity> companies = companyRepository.findByN(companyDto.getN());
        if (companies.size() == 0) {
            String err = format("Company N=%s not found", companyDto.getN());
            throw new Exception(err);
        }
        CompanyEntity entity = companyMapper.toEntity(companyDto);
        CompanyEntity saved = companyRepository.save(entity);
        return companyMapper.toDto(saved);
    }

    @Override
    public List<CompanyDto> findByExample(CompanyCriterySearch criterySearch) {
        log.info(format("Find companies by example: %s", criterySearch));
        Specification<CompanyEntity> spec = CompanySpecifications.hasNGreaterThan(-1L);

        if (criterySearch.getNn() != null && criterySearch.getNn().size() > 0) {
            log.info("add NN to critery: " + criterySearch.getNn());
            spec = spec.and(CompanySpecifications.N_In(criterySearch.getNn()));
        }
        if (!criterySearch.getByName().isEmpty()) {
            log.info("add NAME to critery: " + criterySearch.getByName());
            spec = spec.and(CompanySpecifications.hasNameLike(criterySearch.getByName()));
        }

        List<CompanyEntity> entities = companyRepository.findAll(spec, Sort.by(Sort.Order.asc("n")));

        return companyMapper.toListDto(entities);
    }
}
