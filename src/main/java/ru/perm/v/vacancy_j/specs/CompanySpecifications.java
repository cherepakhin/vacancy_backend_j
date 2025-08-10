package ru.perm.v.vacancy_j.specs;

import org.springframework.data.jpa.domain.Specification;
import ru.perm.v.vacancy_j.entity.CompanyEntity;
import ru.perm.v.vacancy_j.entity.VacancyEntity;

import java.util.List;

import static org.springframework.data.jpa.domain.Specification.where;

public class CompanySpecifications {
//    public static Specification<VacancyEntity> hasCourseWithTitle(String courseTitle) {
//        return (root, query, criteriaBuilder) -> {
//            Join<Student, Course> courses = root.join("courses", JoinType.INNER);
//            return criteriaBuilder.equal(courses.get("title"), courseTitle);
//        };
//    }

    public static Specification<CompanyEntity> hasNGreaterThan(Long n) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThan(root.get("n"), n);
    }

    public static Specification<CompanyEntity> N_In(List<Long> nn) {
        Specification<CompanyEntity> specification = hasNGreaterThan(0L);
        Specification<CompanyEntity> finalSpec = specification.and(where(specification)
                .and((root, query, cb) -> root.get("n").in(nn)));
        return finalSpec;
    }

    public static Specification<CompanyEntity> hasNameLike(String name) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("name"), name);
    }
}
