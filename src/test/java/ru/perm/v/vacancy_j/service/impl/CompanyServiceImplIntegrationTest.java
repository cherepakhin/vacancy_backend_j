package ru.perm.v.vacancy_j.service.impl;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.perm.v.vacancy_j.dto.CompanyDto;
import ru.perm.v.vacancy_j.repository.ICompanyRepository;
import ru.perm.v.vacancy_j.service.CompanyService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
@Transactional
public class CompanyServiceImplIntegrationTest {
    @Autowired
    private ICompanyRepository companyRepository;

    @Test
    void getAll() {
        CompanyService companyService  = new CompanyServiceImpl(companyRepository);
        List<CompanyDto> dtos = companyService.getAll();
        assertEquals(4, dtos.size());
    }

    @Test
    void getByN() {
        CompanyService companyService  = new CompanyServiceImpl(companyRepository);
        try {
            CompanyDto dto = companyService.getByN(2L);
            assertEquals(2L, dto.getN());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void findByLikeName() {
        CompanyService companyService  = new CompanyServiceImpl(companyRepository);
        try {
            List<CompanyDto> dtos = companyService.findByLikeName("COMPANY%");
            assertEquals(2, dtos.size());
            assertEquals(1L, dtos.get(0).getN());
            assertEquals(2L, dtos.get(1).getN());
        } catch (Exception e) {
            fail();
        }
    }
}
