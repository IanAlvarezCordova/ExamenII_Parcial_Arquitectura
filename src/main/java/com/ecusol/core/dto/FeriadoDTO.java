package com.ecusol.core.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;

@Data
public class FeriadoDTO {
    @NotNull(message = "La fecha es obligatoria")
    private LocalDate date;

    @NotBlank(message = "El nombre del feriado es obligatorio")
    private String name;
}