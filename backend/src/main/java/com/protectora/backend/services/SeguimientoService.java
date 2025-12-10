package com.protectora.backend.services;

import com.protectora.backend.dto.SeguimientoDto;
import com.protectora.backend.model.Seguimiento;
import com.protectora.backend.repository.SeguimientoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SeguimientoService {

    private final SeguimientoRepository seguimientoRepository;
    private final String uploadDir = "uploads/seguimientos/";

    public SeguimientoService(SeguimientoRepository seguimientoRepository) {
        this.seguimientoRepository = seguimientoRepository;
        new File(uploadDir).mkdirs();
    }

    public List<SeguimientoDto> getByAdopcion(Integer idAdopcion) {
        return seguimientoRepository.findByAdopcionIdAdopcion(idAdopcion)
                .stream()
                .map(SeguimientoDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public Seguimiento save(Seguimiento s) {
        return seguimientoRepository.save(s);
    }

    public Seguimiento getById(Integer id) {
        return seguimientoRepository.findById(id).orElse(null);
    }

    public String guardarFoto(MultipartFile archivo, Integer idSeguimiento) throws IOException {
        if (archivo == null || archivo.isEmpty())
            return null;

        String extension = archivo.getOriginalFilename().substring(
                archivo.getOriginalFilename().lastIndexOf('.') + 1);
        String nombre = idSeguimiento + "_" + UUID.randomUUID() + "." + extension;

        Path path = Paths.get(uploadDir + nombre);
        Files.write(path, archivo.getBytes());

        return "/api/seguimientos/archivo/" + nombre;
    }
}
