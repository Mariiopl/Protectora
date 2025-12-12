package com.protectora.backend.services;

import com.protectora.backend.dto.MascotaDto;
import com.protectora.backend.model.Mascota;
import com.protectora.backend.repository.MascotaRepository;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.List;

/**
 * Servicio para manejar la lógica de negocio relacionada con mascotas.
 * Gestiona operaciones de creación, actualización, eliminación,
 * recuperación de mascotas y manejo de imágenes asociadas.
 */
@Service
public class MascotaService {

    private final MascotaRepository mascotaRepository;

    // Ruta relativa para almacenar imágenes dentro del backend
    private final Path uploadDir = Paths.get("uploads/mascotas");

    public MascotaService(MascotaRepository mascotaRepository) {
        this.mascotaRepository = mascotaRepository;

        // Crear el directorio de imágenes si no existe
        try {
            Files.createDirectories(uploadDir);
        } catch (IOException e) {
            throw new RuntimeException("No se pudo crear el directorio de imágenes", e);
        }
    }

    // =================================================
    // CONSULTAS
    // =================================================

    /**
     * Obtiene todas las mascotas registradas.
     * 
     * @return Lista de mascotas
     */
    public List<Mascota> getAll() {
        return mascotaRepository.findAll();
    }

    /**
     * Obtiene una mascota por su ID.
     * 
     * @param id ID de la mascota
     * @return Mascota encontrada
     * @throws RuntimeException si no existe
     */
    public Mascota getById(Integer id) {
        return mascotaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mascota no encontrada"));
    }

    /**
     * Obtiene todas las mascotas adoptables.
     * 
     * @return Lista de mascotas adoptables
     */
    public List<Mascota> getAdoptable() {
        return mascotaRepository.findByEstadoAdopcion(Mascota.EstadoAdopcion.adoptable);
    }

    /**
     * Obtiene todas las mascotas adoptadas.
     * 
     * @return Lista de mascotas adoptadas
     */
    public List<Mascota> getAdoptado() {
        return mascotaRepository.findByEstadoAdopcion(Mascota.EstadoAdopcion.adoptado);
    }

    // =================================================
    // CREACIÓN / ACTUALIZACIÓN / ELIMINACIÓN
    // =================================================

    /**
     * Crea una nueva mascota y opcionalmente guarda su foto.
     * 
     * @param dto  Datos de la mascota
     * @param foto Imagen opcional
     * @return Mensaje de éxito
     */
    public String create(MascotaDto dto, MultipartFile foto) {
        Mascota mascota = dto.toEntity();
        mascota.setFechaIngreso(LocalDate.now());
        mascota.setEstadoAdopcion(Mascota.EstadoAdopcion.adoptable);

        // Guardar en base de datos
        mascota = mascotaRepository.save(mascota);

        // Guardar foto si existe
        if (foto != null && !foto.isEmpty()) {
            String fileName = guardarImagen(foto);
            mascota.setFoto(fileName);
            mascotaRepository.save(mascota);
        }

        return "Mascota creada correctamente";
    }

    /**
     * Actualiza una mascota existente y opcionalmente su foto.
     * 
     * @param id   ID de la mascota a actualizar
     * @param dto  Datos actualizados
     * @param foto Imagen opcional
     * @return Mensaje de éxito
     */
    public String update(Integer id, MascotaDto dto, MultipartFile foto) {
        Mascota mascota = getById(id);
        dto.updateEntity(mascota);

        // Actualizar foto si se proporciona
        if (foto != null && !foto.isEmpty()) {
            String fileName = guardarImagen(foto);
            mascota.setFoto(fileName);
        }

        mascotaRepository.save(mascota);
        return "Mascota actualizada correctamente";
    }

    /**
     * Elimina una mascota por su ID.
     * 
     * @param id ID de la mascota
     * @return Mensaje de éxito
     */
    public String delete(Integer id) {
        Mascota mascota = getById(id);
        mascotaRepository.delete(mascota);
        return "Mascota eliminada correctamente";
    }

    // =================================================
    // MANEJO DE IMÁGENES
    // =================================================

    /**
     * Recupera la imagen de una mascota por nombre de archivo.
     * 
     * @param filename Nombre del archivo de imagen
     * @return Resource con la imagen
     * @throws IOException si no existe o no se puede leer
     */
    public Resource getImage(String filename) throws IOException {
        Path file = uploadDir.resolve(filename);

        if (!Files.exists(file)) {
            throw new RuntimeException("La imagen no existe");
        }

        return new UrlResource(file.toUri());
    }

    /**
     * Guarda una imagen en el servidor.
     * 
     * @param foto Archivo a guardar
     * @return Nombre del archivo guardado
     */
    private String guardarImagen(MultipartFile foto) {
        try {
            // Evitar colisiones usando timestamp
            String nombreArchivo = System.currentTimeMillis() + "_" + foto.getOriginalFilename();
            Path destino = uploadDir.resolve(nombreArchivo);

            Files.copy(foto.getInputStream(), destino, StandardCopyOption.REPLACE_EXISTING);

            return nombreArchivo;
        } catch (IOException e) {
            throw new RuntimeException("Error al guardar la imagen", e);
        }
    }
}
