package ru.perm.v.vacancy_j.specs;

import org.springframework.data.jpa.domain.Specification;
import ru.perm.v.vacancy_j.entity.VacancyEntity;

import java.util.List;

public class VacancySpecifications {
//    public static Specification<VacancyEntity> hasCourseWithTitle(String courseTitle) {
//        return (root, query, criteriaBuilder) -> {
//            Join<Student, Course> courses = root.join("courses", JoinType.INNER);
//            return criteriaBuilder.equal(courses.get("title"), courseTitle);
//        };
//    }

    public static Specification<VacancyEntity> hasNGreaterThan(int n) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThan(root.get("n"), n);
    }

    public static Specification<VacancyEntity> N_In(List<Long> nn) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.in(root.get("n")).in(nn);
    }

    public static Specification<VacancyEntity> hasTitleLike(String likeTitle) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("title"), likeTitle);
    }
}
