package com.protectora.backend.model;

// ==================== FOTOS MASCOTAS ====================
import jakarta.persistence.*;

@Entity
@Table(name = "FotosMascotas")
public class FotoMascota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_foto")
    private Integer idFoto;

    @ManyToOne
    @JoinColumn(name = "id_mascota")
    private Mascota mascota;

    @Column(name = "url_imagen")
    private String urlImagen;
}
