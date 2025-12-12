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

/**
 * Servicio para manejar la lógica de negocio de los seguimientos de adopciones.
 * Gestiona creación, actualización, consulta y almacenamiento de fotos de
 * seguimientos.
 */
@Service
public class SeguimientoService {

    private final SeguimientoRepository seguimientoRepository;

    // Directorio donde se almacenan las fotos de los seguimientos
    private final String uploadDir = "uploads/seguimientos/";

    public SeguimientoService(SeguimientoRepository seguimientoRepository) {
        this.seguimientoRepository = seguimientoRepository;

        // Crear el directorio si no existe
        new File(uploadDir).mkdirs();
    }

    // =================================================
    // CONSULTAS
    // =================================================

    /**
     * Obtiene todos los seguimientos de una adopción específica.
     * 
     * @param idAdopcion ID de la adopción
     * @return Lista de SeguimientoDto
     */
    public List<SeguimientoDto> getByAdopcion(Integer idAdopcion) {
        return seguimientoRepository.findByAdopcionIdAdopcion(idAdopcion)
                .stream()
                .map(SeguimientoDto::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene un seguimiento por su ID.
     * 
     * @param id ID del seguimiento
     * @return Seguimiento encontrado o null si no existe
     */
    public Seguimiento getById(Integer id) {
        return seguimientoRepository.findById(id).orElse(null);
    }

    // =================================================
    // CREACIÓN / ACTUALIZACIÓN
    // =================================================

    /**
     * Guarda o actualiza un seguimiento en la base de datos.
     * 
     * @param s Seguimiento a guardar
     * @return Seguimiento guardado
     */
    @Transactional
    public Seguimiento save(Seguimiento s) {
        return seguimientoRepository.save(s);
    }

    // =================================================
    // MANEJO DE FOTOS
    // =================================================

    /**
     * Guarda la foto de un seguimiento en el servidor.
     * 
     * @param archivo       Archivo a guardar
     * @param idSeguimiento ID del seguimiento asociado
     * @return URL relativa para acceder a la foto
     * @throws IOException si ocurre un error al escribir el archivo
     */
    public String guardarFoto(MultipartFile archivo, Integer idSeguimiento) throws IOException {
        if (archivo == null || archivo.isEmpty())
            return null;

        // Obtener extensión del archivo
        String extension = archivo.getOriginalFilename().substring(
                archivo.getOriginalFilename().lastIndexOf('.') + 1);

        // Generar nombre único con UUID
        String nombre = idSeguimiento + "_" + UUID.randomUUID() + "." + extension;

        // Ruta completa donde se guardará el archivo
        Path path = Paths.get(uploadDir + nombre);

        // Escribir archivo en disco
        Files.write(path, archivo.getBytes());

        // Devolver ruta accesible desde el backend
        return "/api/seguimientos/archivo/" + nombre;
    }
}
