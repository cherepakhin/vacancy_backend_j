package ru.perm.v.vacancy_j.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.perm.v.vacancy_j.entity.CompanyEntity;

import java.util.List;


@Repository
public interface ICompanyRepository extends JpaRepository<CompanyEntity, Long>,
        JpaSpecificationExecutor<CompanyEntity> {
    List<CompanyEntity> findByNameOrderByNDesc(String name);

    List<CompanyEntity> findByN(Long n);

    List<CompanyEntity> findAll();

    @Query("select c from CompanyEntity c where upper(name) like upper(:name)")
    List<CompanyEntity> findByLikeName(String name);

    @Query("select max(n) from CompanyEntity")
    Long getMaxN();
}
