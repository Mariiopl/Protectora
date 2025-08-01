package com.protectora.backend.model;

// ==================== EMPLEADOS ====================
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

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
    private Usuario usuario;

    @Enumerated(EnumType.STRING)
    private Rol rol;

    private Double salario;

    private String horario;

    @Column(name = "fecha_alta")
    private LocalDate fechaAlta;

    @OneToMany(mappedBy = "empleado")
    private List<Turno> turnos;

    @OneToMany(mappedBy = "empleado")
    private List<HorasTrabajadas> horasTrabajadas;

    @OneToMany(mappedBy = "empleado")
    private List<TareaAsignada> tareasAsignadas;

    public enum Rol {
        cuidador, limpieza, veterinario, administrador, adopciones, stock
    }
}
