package ru.perm.v.vacancy_j.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.perm.v.vacancy_j.dto.VacancyCriterySearch;
import ru.perm.v.vacancy_j.dto.VacancyDto;
import ru.perm.v.vacancy_j.entity.CompanyEntity;
import ru.perm.v.vacancy_j.entity.VacancyEntity;
import ru.perm.v.vacancy_j.mapper.CompanyMapper;
import ru.perm.v.vacancy_j.mapper.VacancyMapper;
import ru.perm.v.vacancy_j.repository.IVacancyRepository;
import ru.perm.v.vacancy_j.service.VacancyService;
import ru.perm.v.vacancy_j.specs.VacancySpecifications;

import java.util.List;

import static java.lang.String.format;

@Service
public class VacancyServiceImpl implements VacancyService {
    @Autowired
    private IVacancyRepository vacancyRepository;
    private CompanyMapper companyMapper = new CompanyMapper();

    private VacancyMapper vacancyMapper = new VacancyMapper();

    Logger log = LoggerFactory.getLogger(VacancyServiceImpl.class);

    public VacancyServiceImpl() {
        super();
    }

    public VacancyServiceImpl(IVacancyRepository vacancyRepository) {
        this.vacancyRepository = vacancyRepository;
    }

    @Override
    public VacancyDto getByN(Long n) throws Exception {
        List<VacancyEntity> vacancies = vacancyRepository.findByN(n);
        if (vacancies.isEmpty()) {
            throw new Exception("Vacancy not found");
        } else {
            VacancyDto vacancyDto = new VacancyDto();
            vacancyDto.setN(vacancies.get(0).getN());
            vacancyDto.setTitle(vacancies.get(0).getTitle());
            vacancyDto.setDescription(vacancies.get(0).getDescription());
            CompanyEntity companyEntity = vacancies.get(0).getCompanyEntity();
            CompanyMapper mapper = new CompanyMapper();
            vacancyDto.setCompany(companyMapper.toDto(companyEntity));
            //TODO add Source
            vacancyDto.setSource("TODO");
            //TODO add Comment
            vacancyDto.setComment("");
            //TODO add mapper?
            return vacancyDto;
        }
    }

    @Override
    public List<VacancyDto> getAll() {
        List<VacancyEntity> entities = vacancyRepository.findAll();
        return vacancyMapper.toListDto(entities);
    }

    @Override
    public List<VacancyDto> findByName(String title) {
        log.info(format("Find vacancy by title: %s", title));
        VacancyEntity query = new VacancyEntity();
        query.setTitle(title);
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnorePaths("n", "companyEntity", "description", "link", "comment")
                .withIncludeNullValues()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<VacancyEntity> example = Example.of(query, matcher);
        List<VacancyEntity> entities = vacancyRepository.findAll(example);
        for (VacancyEntity v : entities) {
            log.info(format("Find vacancy by title %s", v.toString()));
        }
        return vacancyMapper.toListDto(entities);
    }

    @Override
    public List<VacancyDto> findByCritery(VacancyCriterySearch criterySearch) {
        log.info(format("Find vacancy by criterySearch: %s", criterySearch));

        Specification<VacancyEntity> spec = VacancySpecifications.hasNGreaterThan(-1);

        if (criterySearch.getNn().size() > 0) {
            log.info("Nn");
            spec = spec.and(VacancySpecifications.N_In(criterySearch.getNn()));
        }

        if (!criterySearch.getByName().isEmpty()) {
            spec = spec.and(VacancySpecifications.hasTitleLike(criterySearch.getByName()));
        }

        List<VacancyEntity> entities = vacancyRepository.findAll(spec);
        for (VacancyEntity v : entities) {
            log.info(format("Find vacancy by title %s", v.toString()));
        }
        return vacancyMapper.toListDto(entities);
    }

}
