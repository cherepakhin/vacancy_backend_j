package ru.perm.v.vacancy_j.repository;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.stereotype.Repository;
import ru.perm.v.vacancy_j.entity.VacancyEntity;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

@Repository
public interface IVacancyRepository extends JpaRepository<VacancyEntity, Long>,
        JpaSpecificationExecutor<VacancyEntity> {

    List<VacancyEntity> findByN(Long n);
}
