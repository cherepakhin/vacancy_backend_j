package ru.perm.v.vacancy_j.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.perm.v.vacancy_j.entity.CompanyEntity;


@Repository
public interface ICompanyRepository extends JpaRepository<CompanyEntity, Long> {
    List<CompanyEntity> findByNameOrderByNDesc(String name);
    List<CompanyEntity> findByN(Long n);
    List<CompanyEntity> findAll();

    @Query("select c from CompanyEntity c where name like :name")
    List<CompanyEntity> findByLikeName(String name);
}
