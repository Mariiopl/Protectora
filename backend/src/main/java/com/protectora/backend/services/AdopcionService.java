package com.protectora.backend.services;

import com.protectora.backend.dto.AdopcionDto;
import com.protectora.backend.model.Adopcion;
import com.protectora.backend.model.Mascota;
import com.protectora.backend.model.Usuario;
import com.protectora.backend.repository.AdopcionRepository;
import com.protectora.backend.repository.MascotaRepository;
import com.protectora.backend.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdopcionService {

    private final AdopcionRepository adopcionRepository;
    private final UsuarioRepository usuarioRepository;
    private final MascotaRepository mascotaRepository;

    public AdopcionService(AdopcionRepository adopcionRepository,
            UsuarioRepository usuarioRepository,
            MascotaRepository mascotaRepository) {
        this.adopcionRepository = adopcionRepository;
        this.usuarioRepository = usuarioRepository;
        this.mascotaRepository = mascotaRepository;
    }

    // ======================
    // CONSULTAS
    // ======================
    public List<AdopcionDto> findAllDTO() {
        return adopcionRepository.findAll()
                .stream()
                .map(AdopcionDto::fromEntity)
                .collect(Collectors.toList());
    }

    public List<AdopcionDto> findByUsuarioDTO(Integer idUsuario) {
        return adopcionRepository.findByUsuario_IdUsuario(idUsuario)
                .stream()
                .map(AdopcionDto::fromEntity)
                .collect(Collectors.toList());
    }

    public Adopcion getById(Integer id) {
        return adopcionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Adopción no encontrada"));
    }

    // ======================
    // CREACIÓN / ACTUALIZACIÓN
    // ======================
    public Adopcion create(Integer idUsuario, Integer idMascota, AdopcionDto dto) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Mascota mascota = mascotaRepository.findById(idMascota)
                .orElseThrow(() -> new RuntimeException("Mascota no encontrada"));

        Adopcion adopcion = new Adopcion();
        adopcion.setUsuario(usuario);
        adopcion.setMascota(mascota);
        adopcion.setEstado(dto.getEstado() != null ? dto.getEstado() : Adopcion.Estado.pendiente);
        adopcion.setFechaSolicitud(LocalDate.now());
        adopcion.setFechaAdopcion(dto.getFechaAdopcion());
        adopcion.setExperiencia(dto.getExperiencia());
        adopcion.setTipoVivienda(dto.getTipoVivienda());
        adopcion.setComentarios(dto.getComentarios());

        return adopcionRepository.save(adopcion);
    }

    public Adopcion update(Integer idAdopcion, AdopcionDto dto) {
        Adopcion adopcion = getById(idAdopcion);

        if (dto.getEstado() != null)
            adopcion.setEstado(dto.getEstado());
        if (dto.getFechaAdopcion() != null)
            adopcion.setFechaAdopcion(dto.getFechaAdopcion());
        if (dto.getExperiencia() != null)
            adopcion.setExperiencia(dto.getExperiencia());
        if (dto.getTipoVivienda() != null)
            adopcion.setTipoVivienda(dto.getTipoVivienda());
        if (dto.getComentarios() != null)
            adopcion.setComentarios(dto.getComentarios());

        return adopcionRepository.save(adopcion);
    }

    public void delete(Integer idAdopcion) {
        Adopcion adopcion = getById(idAdopcion);
        adopcionRepository.delete(adopcion);
    }

}
