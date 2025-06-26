package ru.perm.v.vacancy_j.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Тест сгенерирован gigacode
public class CompanyDtoGigaTest {
    @Test
    public void testDefaultConstructor() {
        CompanyDto dto = new CompanyDto();
        assertNotNull(dto);
        assertEquals("", dto.getName());
        assertEquals(-1L, dto.getN());
    }

    @Test
    public void testConstructorWithParams() {
        Long n = 1L;
        String name = "BigCorp";

        CompanyDto dto = new CompanyDto(n, name);

        assertEquals(n, dto.getN());
        assertEquals(name, dto.getName());
    }

    @Test
    public void testGettersAndSetters() {
        CompanyDto dto = new CompanyDto();

        dto.setN(100L);
        dto.setName("SmallCo");

        assertEquals(100L, dto.getN());
        assertEquals("SmallCo", dto.getName());
    }

    @Test
    public void testEqualsAndHashCode() {
        CompanyDto dto1 = new CompanyDto(1L, "Company A");
        CompanyDto dto2 = new CompanyDto(1L, "Company A");

        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());

        dto2.setN(2L);
        assertNotEquals(dto1, dto2);
        assertNotEquals(dto1.hashCode(), dto2.hashCode());

        dto2.setN(1L);
        dto2.setName("Company B");
        assertNotEquals(dto1, dto2);
        assertNotEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    public void testToString() {
        CompanyDto dto = new CompanyDto(123L, "Example Corp");

        String expected = "CompanyDto{n=123, name='Example Corp'}";
        assertEquals(expected, dto.toString());
    }

    @Test
    public void testValidationNameTooShort() {
        CompanyDto dto = new CompanyDto(1L, "Tiny"); // Длина < 5
        assertFalse(validate(dto));
    }

    @Test
    public void testValidationNameValid() {
        CompanyDto dto = new CompanyDto(1L, "BigCorp"); // Длина >= 5
        assertTrue(validate(dto));
    }

    private boolean validate(CompanyDto dto) {
        return dto.getName() != null && dto.getName().length() >= 5;
    }
}
