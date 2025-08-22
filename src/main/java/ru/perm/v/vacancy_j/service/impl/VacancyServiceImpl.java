package ru.perm.v.vacancy_j.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.perm.v.vacancy_j.dto.VacancyCriterySearch;
import ru.perm.v.vacancy_j.dto.VacancyDto;
import ru.perm.v.vacancy_j.entity.VacancyEntity;
import ru.perm.v.vacancy_j.entity.VacancySort;
import ru.perm.v.vacancy_j.mapper.VacancyMapper;
import ru.perm.v.vacancy_j.repository.IVacancyRepository;
import ru.perm.v.vacancy_j.rest.validator.ValidatorVacancyDto;
import ru.perm.v.vacancy_j.service.VacancyService;
import ru.perm.v.vacancy_j.specs.VacancySpecifications;

import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

@Service
public class VacancyServiceImpl implements VacancyService {
    @Autowired
    private IVacancyRepository vacancyRepository;

    private final VacancyMapper vacancyMapper = new VacancyMapper();

    Logger log = LoggerFactory.getLogger(VacancyServiceImpl.class);

    public VacancyServiceImpl() {
        super();
    }

    public VacancyServiceImpl(IVacancyRepository vacancyRepository) {
        this();
        this.vacancyRepository = vacancyRepository;
    }

    @Override
    public VacancyDto getByN(Long n) throws Exception {
        List<VacancyEntity> vacancies = vacancyRepository.findByN(n);
        if (vacancies.isEmpty()) {
            throw new Exception(format("Vacancy with N %s not found.", n));
        } else {
            VacancyDto vacancyDto = vacancyMapper.toDto(vacancies.get(0));
            return vacancyDto;
        }
    }

    @Override
    public List<VacancyDto> getAll() {
        List<VacancyEntity> entities = vacancyRepository.findAll(Sort.by(Sort.Order.asc(VacancySort.N)));
        return vacancyMapper.toListDto(entities);
    }

    @Override
    public List<VacancyDto> getAll(String sortColumn) throws Exception {
        valdateSortColumn(sortColumn);
        List<VacancyEntity> entities = vacancyRepository.findAll(Sort.by(Sort.Order.asc(sortColumn)));
        return vacancyMapper.toListDto(entities);
    }

    public void valdateSortColumn(String sortColumn) throws Exception {
        if (!sortColumn.equals("n") &&
                !sortColumn.equals("title")) {
            throw new IllegalArgumentException(format("Name sort column %s is wrong.", sortColumn));
        }
    }

    @Override
    public List<VacancyDto> getAll(VacancySort vacancySort) throws Exception {
        return getAll(vacancySort.toString());
    }

    @Override
    public List<VacancyDto> findByTitle(String title) {
        log.info(format("Find vacancy by title: %s", title));
        VacancyEntity query = new VacancyEntity();
        query.setTitle(title);
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnorePaths("n", "companyEntity", "description", "link", "comment", "status", "dateChanged")
                .withIncludeNullValues()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                .withIgnoreCase();
        Example<VacancyEntity> example = Example.of(query, matcher);
        List<VacancyEntity> entities = vacancyRepository.findAll(example, Sort.by(Sort.Order.asc("n")));
        for (VacancyEntity v : entities) {
            log.info(format("Find vacancy by title %s", v.toString()));
        }
        return vacancyMapper.toListDto(entities);
    }

    @Override
    public List<VacancyDto> findByCritery(VacancyCriterySearch criterySearch) {
        log.info(format("Find vacancy by criterySearch: %s", criterySearch));

        Specification<VacancyEntity> spec = VacancySpecifications.hasNGreaterThan(-1L);

        if (!criterySearch.getNn().isEmpty()) {
            log.info("add NN to critery");
            spec = spec.and(VacancySpecifications.N_In(criterySearch.getNn()));
        }

        if (!criterySearch.getByTitle().isEmpty()) {
            log.info("add NAME to critery");
            spec = spec.and(VacancySpecifications.hasTitleLike(criterySearch.getByTitle()));
        }

        List<VacancyEntity> entities = vacancyRepository.findAll(spec, Sort.by(Sort.Order.asc("n")));
        for (VacancyEntity v : entities) {
            log.info(format("Find vacancy by title %s", v.toString()));
        }
        return vacancyMapper.toListDto(entities);
    }

    @Override
    public VacancyDto create(VacancyDto vacancyDto) throws Exception {
        log.info(format("VacancyDTO for create %s", vacancyDto));

        List<String> violations = ValidatorVacancyDto.validate(vacancyDto);
        if (violations.size() > 0) {
            String errors = "";
            for (String s : violations) {
                errors += s + "\n";
            }
            throw new Exception(errors);
        }

        VacancyEntity entity = vacancyMapper.toEntity(vacancyDto);
        Long n = getNextMaxN();
        entity.setN(n);
        log.info(format("New entity %s", entity));
        VacancyEntity saved = vacancyRepository.save(entity);
        log.info(format("Saved entity %s", entity));

        return vacancyMapper.toDto(saved);
    }

    @Override
    public VacancyDto update(VacancyDto vacancyDto) throws Exception {
        //TODO: validate
        if (vacancyDto == null) {
            String error = "VacancyDto for update is null";
            log.info(error);
            throw new Exception(error);
        }
        if (vacancyDto.getN() == null) {
            String error = "ID VacancyDto for update is null";
            log.info(error);
            throw new Exception(error);
        }
        boolean exist = vacancyRepository.existsById(vacancyDto.getN());
        if (!exist) {
            String error = format("VacancyDto with N= %s not exist", vacancyDto.getN());
            log.info(error);
            throw new Exception(error);
        }

        Optional<VacancyEntity> optionalVacancy = vacancyRepository.findById(vacancyDto.getN());
        if (optionalVacancy.isEmpty()) {
            String error = format("VacancyDto with N= %s not found", vacancyDto.getN());
            log.info(error);
            throw new Exception(error);
        }
        VacancyEntity entity = vacancyMapper.toEntity(vacancyDto);
        VacancyEntity saved = vacancyRepository.save(entity);
        return vacancyMapper.toDto(saved);
    }

    @Override
    public void deleteByN(Long n) throws Exception {
        boolean exist = vacancyRepository.existsById(n);
        if (!exist) {
            String error = format("VacancyDto with N=%s not exist", n);
            log.info(error);
            throw new Exception(error);
        }
        try {
            vacancyRepository.deleteById(n);
        } catch (Exception e) {
            String error = format("Error for delete n=%s: %s", n, e.getMessage());
            log.info(error);
            throw new Exception(error);
        }
    }

    @Override
    public Long getNextMaxN() {
        Long nextN = vacancyRepository.getMaxN();
        if (nextN == null) {
            nextN = 0L;
        }
        return nextN + 1L;
    }
}
