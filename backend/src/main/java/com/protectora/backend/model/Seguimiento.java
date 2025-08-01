package com.protectora.backend.model;

// ==================== SEGUIMIENTOS ====================
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

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

    @ManyToOne
    @JoinColumn(name = "id_adopcion")
    private Adopcion adopcion;

    private LocalDate fecha;
    private String mensaje;

    @Column(name = "url_imagen")
    private String urlImagen;

    @Column(name = "url_video")
    private String urlVideo;

    @Enumerated(EnumType.STRING)
    private Tipo tipo;

    @Column(name = "enlace_videollamada")
    private String enlaceVideollamada;

    public enum Tipo {
        texto, foto, video, videollamada
    }
}
