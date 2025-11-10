package com.protectora.backend.services;

import com.protectora.backend.dto.AdopcionDTO;
import com.protectora.backend.model.Adopcion;
import com.protectora.backend.repository.AdopcionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdopcionService {

    private final AdopcionRepository adopcionRepository;

    public AdopcionService(AdopcionRepository adopcionRepository) {
        this.adopcionRepository = adopcionRepository;
    }

    public List<AdopcionDTO> findAllDTO() {
        return adopcionRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    private AdopcionDTO toDTO(Adopcion adopcion) {
        AdopcionDTO dto = new AdopcionDTO();
        dto.setIdAdopcion(adopcion.getIdAdopcion());
        dto.setEstado(adopcion.getEstado().name());
        dto.setFechaSolicitud(adopcion.getFechaSolicitud());
        dto.setFechaAdopcion(adopcion.getFechaAdopcion());
        dto.setExperiencia(adopcion.getExperiencia());
        dto.setTipoVivienda(adopcion.getTipoVivienda());
        dto.setComentarios(adopcion.getComentarios());

        dto.setIdUsuario(adopcion.getUsuario().getIdUsuario());
        dto.setNombreUsuario(adopcion.getUsuario().getNombre());

        dto.setIdMascota(adopcion.getMascota().getIdMascota());
        dto.setNombreMascota(adopcion.getMascota().getNombre());

        return dto;
    }

    public Optional<Adopcion> findById(Integer id) {
        return adopcionRepository.findById(id);
    }

    public Adopcion save(Adopcion adopcion) {
        return adopcionRepository.save(adopcion);
    }

    public void deleteById(Integer id) {
        adopcionRepository.deleteById(id);
    }
}
