package ru.perm.v.vacancy_j.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

//тест простой конвертации JSON в объект
class MapperTest {

    @Test
    void convertJsonArrayToList() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String jsonCarArray =
                "[{ \"color\" : \"Black\", \"type\" : \"BMW\" }, { \"color\" : \"Red\", \"type\" : \"FIAT\" }]";
        List<Car> listCar = mapper.readValue(jsonCarArray, new TypeReference<>() {
        });

        assertEquals(2, listCar.size());
        assertEquals(new Car("Black", "BMW"), listCar.get(0));
        assertEquals(new Car("Red", "FIAT"), listCar.get(1));
    }
}
