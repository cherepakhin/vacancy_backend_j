package ru.perm.v.vacancy_j.rest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.perm.v.vacancy_j.dto.CompanyDto;
import ru.perm.v.vacancy_j.entity.CompanyEntity;
import ru.perm.v.vacancy_j.service.CompanyService;
import ru.perm.v.vacancy_j.service.VacancyService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CompanyRestCacheTest {
    @Mock
    private CompanyService companyService;

    @Test
    public void checkCountCallforGetALL() {
        CompanyRest companyRest = new CompanyRest(companyService);
        CompanyDto companyDto1 = new CompanyDto();
        companyDto1.setN(1L);
        CompanyDto companyDto2 = new CompanyDto();
        companyDto2.setN(2L);
        when(companyService.getAll()).thenReturn(List.of(companyDto1, companyDto2));

        companyRest.getAll();
        companyRest.getAll();
        companyRest.getAll();
        ResponseEntity<?> response = companyRest.getAll();
        List<CompanyDto> dtos = (List<CompanyDto>) response.getBody();

        assertEquals(2, dtos.size());
        assertEquals(1L, dtos.get(0).getN());
        assertEquals(2L, dtos.get(1).getN());

        //TODO: почему 4? При прогоне одного теста все работает. При прогоне в./gradlew test НЕ РАБОТАЕТ
        verify(companyService, times(4)).getAll();
    }
}
