package com.protectora.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "Empleados")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Empleado {

    @Id
    @Column(name = "id_empleado")
    private Integer idEmpleado; // mismo que id_usuario

    @OneToOne
    @MapsId
    @JoinColumn(name = "id_empleado")
    @NotNull(message = "El usuario es obligatorio")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Usuario usuario;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "El rol es obligatorio")
    private Rol rol;

    @NotNull(message = "El salario es obligatorio")
    @Min(value = 0, message = "El salario no puede ser negativo")
    private Double salario;

    @Size(max = 100, message = "El horario no puede superar los 100 caracteres")
    private String horario;

    @Column(name = "fecha_alta")
    @NotNull(message = "La fecha de alta es obligatoria")
    private LocalDate fechaAlta;

    @OneToMany(mappedBy = "empleado")
    @JsonIgnore
    private List<Turno> turnos;

    @OneToMany(mappedBy = "empleado")
    @JsonIgnore
    private List<HorasTrabajadas> horasTrabajadas;

    @OneToMany(mappedBy = "empleado")
    @JsonIgnore
    private List<TareaAsignada> tareasAsignadas;

    public enum Rol {
        cuidador, limpieza, veterinario, administrador, adopciones, stock
    }
}
// {
// "usuario": {
// "idUsuario": 2
// },
// "rol": "veterinario",
// "salario": 1800.50,
// "horario": "Lunes a Viernes 9:00-17:00",
// "fechaAlta": "2025-09-19"
// }
