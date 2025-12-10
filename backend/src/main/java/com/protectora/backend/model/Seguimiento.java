package com.protectora.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "Seguimientos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Seguimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_seguimiento")
    private Integer idSeguimiento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_adopcion", nullable = false)
    @NotNull(message = "La adopción es obligatoria")
    private Adopcion adopcion;

    @NotNull(message = "La fecha programada es obligatoria")
    @Column(name = "fecha_programada")
    private LocalDate fechaProgramada;

    @Column(name = "fecha_realizada")
    private LocalDateTime fechaRealizada;

    @Size(max = 500, message = "El mensaje no puede superar los 500 caracteres")
    private String mensaje;

    @Size(max = 255, message = "La URL de la imagen no puede superar los 255 caracteres")
    @Column(name = "url_imagen")
    private String urlImagen;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Estado estado = Estado.pendiente;

    public enum Estado {
        pendiente, completado
    }
}
