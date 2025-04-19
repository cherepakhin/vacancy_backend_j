package ru.perm.v.vacancy_j.mapper;

import java.util.List;

public interface IMapper<DTO, Entity> {
    DTO toDto(Entity entity);
    Entity toEntity(DTO dto);
    List<Entity> toListEntity(List<DTO> dtos);
    List<DTO> toListDto(List<Entity> entities);
}
