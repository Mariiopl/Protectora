package com.protectora.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    @NotNull(message = "La adopción es obligatoria")
    private Adopcion adopcion;

    @NotNull(message = "La fecha es obligatoria")
    private LocalDate fecha;

    @Size(max = 500, message = "El mensaje no puede superar los 500 caracteres")
    private String mensaje;

    @Size(max = 255, message = "La URL de la imagen no puede superar los 255 caracteres")
    @Column(name = "url_imagen")
    private String urlImagen;

    @Size(max = 255, message = "La URL del video no puede superar los 255 caracteres")
    @Column(name = "url_video")
    private String urlVideo;

    @NotNull(message = "El tipo de seguimiento es obligatorio")
    @Enumerated(EnumType.STRING)
    private Tipo tipo;

    @Size(max = 255, message = "El enlace de videollamada no puede superar los 255 caracteres")
    @Column(name = "enlace_videollamada")
    private String enlaceVideollamada;

    public enum Tipo {
        texto, foto, video, videollamada
    }
}
// {
// "adopcion": {
// "idAdopcion": 1
// },
// "fecha": "2025-09-19",
// "mensaje": "El perro ha progresado mucho en su socialización.",
// "urlImagen": "https://example.com/fotos/progreso.jpg",
// "urlVideo": "https://example.com/videos/progreso.mp4",
// "tipo": "foto",
// "enlaceVideollamada": ""
// }
