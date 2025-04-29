package ru.perm.v.vacancy_j.mapper;

import org.junit.jupiter.api.Test;
import ru.perm.v.vacancy_j.dto.CompanyDto;
import ru.perm.v.vacancy_j.dto.VacancyDto;
import ru.perm.v.vacancy_j.entity.CompanyEntity;
import ru.perm.v.vacancy_j.entity.VacancyEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VacancyMapperTest {
    VacancyMapper vacancyMapper = new VacancyMapper();
    @Test
    void toDto() {
        VacancyEntity vacancyEntity = new VacancyEntity();
        Long N = 100L;
        String TITLE = "TITLE";
        vacancyEntity.setN(N);
        vacancyEntity.setTitle(TITLE);

        CompanyEntity companyEntity = new CompanyEntity();
        Long COMPANY_N = 100L;
        String COMPANY_NAME = "COMPANY_NAME";
        companyEntity.setN(COMPANY_N);
        companyEntity.setName(COMPANY_NAME);

        vacancyEntity.setCompanyEntity(companyEntity);

        VacancyDto vacancyDto = vacancyMapper.toDto(vacancyEntity);

        assertEquals(N, vacancyDto.getN());
        assertEquals(TITLE, vacancyDto.getTitle());
        assertEquals(COMPANY_N, vacancyDto.getCompany().getN());
        assertEquals(COMPANY_NAME, vacancyDto.getCompany().getName());
    }

    @Test
    void toEntity() {
        CompanyDto companyDto = new CompanyDto();
        Long COMPANY_N = 100L;
        String COMPANY_NAME = "COMPANY_NAME";
        companyDto.setN(COMPANY_N);
        companyDto.setName(COMPANY_NAME);

        VacancyDto vacancyDto = new VacancyDto();
        Long VACANCY_N = 10L;
        String VACANCY_TITLE = "VACANCY_TITLE";
        String VACANCY_DESCRIPTION = "VACANCY_DESCRIPTION";

        vacancyDto.setN(VACANCY_N);
        vacancyDto.setTitle(VACANCY_TITLE);
        vacancyDto.setDescription(VACANCY_DESCRIPTION);
        vacancyDto.setCompany(companyDto);

        VacancyMapper vacancyMapper = new VacancyMapper();

        VacancyEntity vacancyEntity = vacancyMapper.toEntity(vacancyDto);

        assertEquals(VACANCY_N, vacancyEntity.getN());
        assertEquals(VACANCY_TITLE, vacancyEntity.getTitle());
        assertEquals(VACANCY_DESCRIPTION, vacancyEntity.getDescription());
        assertEquals(COMPANY_N, vacancyEntity.getCompanyEntity().getN());
        assertEquals(COMPANY_NAME, vacancyEntity.getCompanyEntity().getName());
    }

    @Test
    void toListDto() {
        Long N10 = 10L;
        Long COMPANY_N_100 = 100L;
        String COMPANY_NAME_100 = "COMPANY_NAME_100";

        Long VACANCY_N_10 = 10L;
        String VACANCY_TITLE_10 = "VACANCY_TITLE_10";
        String VACANCY_DESCRIPTION_10 = "VACANCY_DESCRIPTION_10";

        CompanyEntity companyEntity100 = new CompanyEntity(COMPANY_N_100, COMPANY_NAME_100);
        String LINK_10="LINK_10";
        String COMMENT_10 = "COMMENT_10";
        String STATUS_10 = "STATUS_10";
        VacancyEntity entity100 = new VacancyEntity(N10, VACANCY_TITLE_10,companyEntity100,
                VACANCY_DESCRIPTION_10, LINK_10, COMMENT_10, STATUS_10);

        Long COMPANY_N_200 = 200L;
        String COMPANY_NAME_200 = "COMPANY_NAME_200";
        CompanyEntity companyEntity200 = new CompanyEntity(COMPANY_N_200, COMPANY_NAME_200);
        Long VACANCY_N_20 = 200L;
        String VACANCY_TITLE_20 = "VACANCY_TITLE_20";
        String VACANCY_DESCRIPTION_20 = "VACANCY_DESCRIPTION_20";
        String LINK_20="LINK_20";
        String COMMENT_20 = "COMMENT_20";
        String STATUS = "STATUS";
        VacancyEntity entity200 = new VacancyEntity(VACANCY_N_20, VACANCY_TITLE_20,companyEntity200, VACANCY_DESCRIPTION_20,
                LINK_20, COMMENT_20, STATUS);

        List<VacancyDto> dtos = new VacancyMapper().toListDto(List.of(entity100, entity200));

        CompanyDto companyDto100 = new CompanyDto();
        companyDto100.setN(COMPANY_N_100);
        companyDto100.setName(COMPANY_NAME_100);

        VacancyDto vacancyDto10 = new VacancyDto();

        vacancyDto10.setN(VACANCY_N_10);
        vacancyDto10.setTitle(VACANCY_TITLE_10);
        vacancyDto10.setDescription(VACANCY_DESCRIPTION_10);
        vacancyDto10.setCompany(companyDto100);
        vacancyDto10.setSource("LINK_10");
        vacancyDto10.setComment("COMMENT_10");
        assertEquals(vacancyDto10, dtos.get(0));

        CompanyDto companyDto200 = new CompanyDto();
        companyDto200.setN(COMPANY_N_200);
        companyDto200.setName(COMPANY_NAME_200);

        VacancyDto vacancyDto20 = new VacancyDto();

        vacancyDto20.setN(VACANCY_N_20);
        vacancyDto20.setTitle(VACANCY_TITLE_20);
        vacancyDto20.setDescription(VACANCY_DESCRIPTION_20);
        vacancyDto20.setCompany(companyDto200);
        vacancyDto20.setSource("LINK_20");
        vacancyDto20.setComment(COMMENT_20);

        assertEquals(vacancyDto20, dtos.get(1));
    }

    @Test
    void toListEntity() {
        Long N10 = 10L;
        Long COMPANY_N_100 = 100L;
        String COMPANY_NAME_100 = "COMPANY_NAME_100";
        String COMMENT_10 = "COMMENT_10";
        String COMMENT_20 = "COMMENT_20";

        Long VACANCY_N_10 = 10L;
        String VACANCY_TITLE_10 = "VACANCY_TITLE_10";
        String VACANCY_DESCRIPTION_10 = "VACANCY_DESCRIPTION_10";
        String LINK_10="LINK_10";
        String LINK_20="LINK_20";
        String STATUS_10="STATUS_10";
        String STATUS_20="STATUS_20";

        VacancyDto vacancyDto10 = new VacancyDto();

        vacancyDto10.setN(VACANCY_N_10);
        vacancyDto10.setTitle(VACANCY_TITLE_10);
        vacancyDto10.setDescription(VACANCY_DESCRIPTION_10);
        vacancyDto10.setSource(LINK_10);
        vacancyDto10.setComment(COMMENT_10);

        CompanyDto companyDto100 = new CompanyDto(COMPANY_N_100, COMPANY_NAME_100);
        vacancyDto10.setCompany(companyDto100);
        vacancyDto10.setStatus(STATUS_10);

        VacancyDto vacancyDto20 = new VacancyDto();

        Long VACANCY_N_20 = 20L;
        String VACANCY_TITLE_20 = "VACANCY_TITLE_20";
        String VACANCY_DESCRIPTION_20 = "VACANCY_DESCRIPTION_20";

        vacancyDto20.setN(VACANCY_N_20);
        vacancyDto20.setTitle(VACANCY_TITLE_20);
        vacancyDto20.setDescription(VACANCY_DESCRIPTION_20);
        vacancyDto20.setSource(LINK_20);
        vacancyDto20.setComment(COMMENT_20);

        Long COMPANY_N_200 = 200L;
        String COMPANY_NAME_200 = "COMPANY_NAME_200";
        CompanyDto companyDto200 = new CompanyDto(COMPANY_N_200, COMPANY_NAME_200);
        vacancyDto20.setCompany(companyDto200);
        vacancyDto20.setStatus(STATUS_20);

        List<VacancyEntity> entities = new VacancyMapper().toListEntity(List.of(vacancyDto10, vacancyDto20));

        CompanyEntity companyEntity100 = new CompanyEntity(COMPANY_N_100, COMPANY_NAME_100);

        CompanyEntity companyEntity200 = new CompanyEntity(COMPANY_N_200, COMPANY_NAME_200);
        VacancyEntity vacancyEntity10 = new VacancyEntity(VACANCY_N_10, VACANCY_TITLE_10,companyEntity100, VACANCY_DESCRIPTION_10, LINK_10, COMMENT_10, STATUS_10);
        VacancyEntity vacancyEntity20 = new VacancyEntity(VACANCY_N_20, VACANCY_TITLE_20,companyEntity200, VACANCY_DESCRIPTION_20, LINK_20, COMMENT_20, STATUS_20);

        assertEquals(vacancyEntity10, entities.get(0));
        assertEquals(vacancyEntity20, entities.get(1));
    }
}
