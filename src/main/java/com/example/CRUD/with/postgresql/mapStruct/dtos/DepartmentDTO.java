package com.example.CRUD.with.postgresql.mapStruct.dtos;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.validation.constraints.NotEmpty;


@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentDTO {

    @NotEmpty
    private String name;

}
