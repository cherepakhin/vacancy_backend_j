package ru.perm.v.vacancy_j.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.perm.v.vacancy_j.entity.VacancyEntity;

import java.util.List;

@Repository
public interface IVacancyRepository extends JpaRepository<VacancyEntity, Long>,
        JpaSpecificationExecutor<VacancyEntity> {

    List<VacancyEntity> findByN(Long n);
}
