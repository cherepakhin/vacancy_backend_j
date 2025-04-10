package ru.perm.v.vacancy_j.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.perm.v.vacancy_j.dto.VacancyDto;
import ru.perm.v.vacancy_j.entity.CompanyEntity;
import ru.perm.v.vacancy_j.entity.VacancyEntity;
import ru.perm.v.vacancy_j.mapper.CompanyMapper;
import ru.perm.v.vacancy_j.repository.IVacancyRepository;
import ru.perm.v.vacancy_j.service.VacancyService;

import java.util.List;

@Service
public class VacancyServiceImpl implements VacancyService {
    @Autowired
    private IVacancyRepository vacancyRepository;
    private CompanyMapper companyMapper = new CompanyMapper();

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
            vacancyDto.setCompleted(false); //TODO: set completed
            //TODO add mapper?
            return vacancyDto;
        }
    }
}
