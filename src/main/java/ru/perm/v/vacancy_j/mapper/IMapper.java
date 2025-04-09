package ru.perm.v.vacancy_j.mapper;

public interface IMapper<DTO, Entity> {
    DTO toDto(Entity entity);
    Entity toEntity(DTO dto);
}
