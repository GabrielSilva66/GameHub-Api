/**
 * Service for managing developers in the application.
 * <p>
 * This service provides methods to create, retrieve, update, delete, and search developers.
 * It interacts with the {@link DeveloperRepository} and performs necessary operations on {@link Developer}.
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

import br.com.gamehub.dto.request.DeveloperRequestDTO;
import br.com.gamehub.dto.response.DeveloperResponseDTO;
import br.com.gamehub.mapper.DeveloperMapper;
import br.com.gamehub.model.Developer;
import br.com.gamehub.repository.DeveloperRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class DeveloperService {

   private final DeveloperRepository developerRepository;

   /**
    * Constructor for initializing the service with the necessary repository.
    *
    * @param developerRepository the repository for developers.
    */
   @Autowired
   public DeveloperService(DeveloperRepository developerRepository) {
      this.developerRepository = developerRepository;
   }

   /**
    * Creates a new developer using the provided request DTO.
    *
    * @param developerRequestDTO the developer request DTO containing the developer
    *                            details.
    * @return the response DTO of the created developer.
    */
   public DeveloperResponseDTO createDeveloper(DeveloperRequestDTO developerRequestDTO) {
      Developer developer = DeveloperMapper.toEntity(developerRequestDTO);
      developer = developerRepository.save(developer);

      return DeveloperMapper.toResponse(developer);
   }

   /**
    * Retrieves a developer by its ID.
    *
    * @param id the ID of the developer to retrieve.
    * @return the response DTO of the developer.
    * @throws EntityNotFoundException if no developer with the provided ID is
    *                                 found.
    */
   public DeveloperResponseDTO getDeveloperById(Long id) {
      Developer developer = developerRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Developer with id " + id + " not found"));

      return DeveloperMapper.toResponse(developer);
   }

   /**
    * Retrieves all developers in the system.
    *
    * @return a list of response DTOs of all developers.
    */
   public List<DeveloperResponseDTO> getAllDevelopers() {
      List<Developer> developers = developerRepository.findAll();
      return developers.stream().map(DeveloperMapper::toResponse).toList();
   }

   /**
    * Updates an existing developer based on the provided ID and request DTO.
    *
    * @param id                  the ID of the developer to update.
    * @param developerRequestDTO the developer request DTO containing the updated
    *                            details.
    * @return the response DTO of the updated developer.
    * @throws EntityNotFoundException if no developer with the provided ID is
    *                                 found.
    */
   public DeveloperResponseDTO updateDeveloper(Long id, DeveloperRequestDTO developerRequestDTO) {
      Developer developer = developerRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Developer with id " + id + " not found"));

      developer.setName(developerRequestDTO.name());
      developer.setYearOfFoundation(developerRequestDTO.yearOfFoundation());
      developer.setHostCountry(developerRequestDTO.hostCountry());

      developer = developerRepository.save(developer);

      return DeveloperMapper.toResponse(developer);
   }

   /**
    * Deletes a developer by its ID.
    *
    * @param id the ID of the developer to delete.
    * @throws EntityNotFoundException if no developer with the provided ID is
    *                                 found.
    */
   public void deleteDeveloper(Long id) {
      Developer developer = developerRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Developer with id " + id + " not found"));

      developerRepository.delete(developer);
   }

   /**
    * Searches for developers based on the provided parameters, including
    * pagination and sorting.
    *
    * @param name      the name filter for the search (can be partial).
    * @param page      the page number for pagination.
    * @param size      the size of each page for pagination.
    * @param orderBy   the field to order by.
    * @param direction the direction of sorting (asc or desc).
    * @return a page of response DTOs for the matching developers.
    */
   public Page<DeveloperResponseDTO> searchDevelopers(
         String name,
         Integer page,
         Integer size,
         String orderBy,
         String direction) {
      Sort sort = Sort.by(Sort.Direction.fromString(direction), orderBy);
      Pageable pageable = PageRequest.of(page, size, sort);
      Page<Developer> developers = developerRepository.search(name, pageable);

      return developers.map(DeveloperMapper::toResponse);
   }
}
