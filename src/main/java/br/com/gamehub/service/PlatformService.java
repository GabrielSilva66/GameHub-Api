/**
 * Service for managing platform data.
 * <p>
 * This service provides methods to create, retrieve, update, delete, and search platforms.
 * It interacts with the {@link PlatformRepository} and performs necessary operations on {@link Platform}.
 * </p>
 * 
 * @author Pedro Lucas
 * @since 2025-01-23
 */
package br.com.gamehub.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.gamehub.dto.request.PlatformRequestDTO;
import br.com.gamehub.dto.response.PlatformResponseDTO;
import br.com.gamehub.mapper.PlatformMapper;
import br.com.gamehub.model.Platform;
import br.com.gamehub.repository.PlatformRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class PlatformService {
   private final PlatformRepository platformRepository;

   /**
    * Constructor for PlatformService.
    *
    * @param platformRepository the repository for platform data.
    */
   @Autowired
   public PlatformService(PlatformRepository platformRepository) {
      this.platformRepository = platformRepository;
   }

   /**
    * Creates a new platform.
    *
    * @param platformRequestDTO the DTO containing platform creation data.
    * @return the response DTO of the created platform.
    */
   public PlatformResponseDTO createPlatform(PlatformRequestDTO platformRequestDTO) {
      Platform platform = PlatformMapper.toEntity(platformRequestDTO);
      platform = platformRepository.save(platform);

      return PlatformMapper.toResponse(platform);
   }

   /**
    * Retrieves a platform by its ID.
    *
    * @param id the ID of the platform.
    * @return the response DTO of the found platform.
    * @throws EntityNotFoundException if the platform is not found.
    */
   public PlatformResponseDTO getPlatformById(Long id) {
      Platform platform = platformRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Platform with id " + id + " not found"));

      return PlatformMapper.toResponse(platform);
   }

   /**
    * Retrieves all platforms.
    *
    * @return a list of response DTOs for all platforms.
    */
   public List<PlatformResponseDTO> getAllPlatforms() {
      List<Platform> platforms = platformRepository.findAll();
      return platforms.stream().map(PlatformMapper::toResponse).toList();
   }

   /**
    * Updates an existing platform.
    *
    * @param id                 the ID of the platform to update.
    * @param platformRequestDTO the DTO containing updated platform data.
    * @return the response DTO of the updated platform.
    * @throws EntityNotFoundException if the platform is not found.
    */
   public PlatformResponseDTO updatePlatform(Long id, PlatformRequestDTO platformRequestDTO) {
      Platform platform = platformRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Platform with id " + id + " not found"));

      platform.setName(platformRequestDTO.name());

      platform = platformRepository.save(platform);

      return PlatformMapper.toResponse(platform);
   }

   /**
    * Deletes a platform by its ID.
    *
    * @param id the ID of the platform to delete.
    */
   public void deletePlatform(Long id) {
      platformRepository.deleteById(id);
   }

   /**
    * Searches for platforms by name with pagination and sorting options.
    *
    * @param name       the name of the platform to search for.
    * @param pageNumber the page number to retrieve (0-based).
    * @param pageSize   the number of records per page.
    * @param orderBy    the field to sort by.
    * @param direction  the sort direction ("ASC" or "DESC").
    * @return a page of response DTOs for the platforms matching the search
    *         criteria.
    */
   public Page<PlatformResponseDTO> searchPlatforms(
         String name,
         Integer pageNumber,
         Integer pageSize,
         String orderBy,
         String direction) {
      Sort sort = Sort.by(Sort.Direction.fromString(direction), orderBy);
      Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

      Page<Platform> platforms = platformRepository.search(name, pageable);
      return platforms.map(PlatformMapper::toResponse);
   }
}
