/**
 * Service for managing stores in the application.
 * <p>
 * This service provides methods to create, retrieve, update, delete, and search
 * stores. It interacts with the {@link StoreRepository} and performs operations
 * on {@link Store}.
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

import br.com.gamehub.dto.request.StoreRequestDTO;
import br.com.gamehub.dto.response.StoreResponseDTO;
import br.com.gamehub.mapper.StoreMapper;
import br.com.gamehub.model.Store;
import br.com.gamehub.repository.StoreRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class StoreService {

   private final StoreRepository storeRepository;

   /**
    * Constructor for initializing the service with the necessary repository.
    *
    * @param storeRepository the repository for stores.
    */
   @Autowired
   public StoreService(StoreRepository storeRepository) {
      this.storeRepository = storeRepository;
   }

   /**
    * Creates a new store using the provided request DTO.
    *
    * @param storeRequestDTO the store request DTO containing the store details.
    * @return the response DTO of the created store.
    */
   public StoreResponseDTO createStore(StoreRequestDTO storeRequestDTO) {
      Store store = StoreMapper.toEntity(storeRequestDTO);
      store = storeRepository.save(store);

      return StoreMapper.toResponse(store);
   }

   /**
    * Retrieves a store by its ID.
    *
    * @param id the ID of the store to retrieve.
    * @return the response DTO of the store.
    * @throws EntityNotFoundException if no store with the provided ID is found.
    */
   public StoreResponseDTO getStoreById(Long id) {
      Store store = storeRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Store with id " + id + " not found"));

      return StoreMapper.toResponse(store);
   }

   /**
    * Retrieves all stores in the system.
    *
    * @return a list of response DTOs of all stores.
    */
   public List<StoreResponseDTO> getAllStores() {
      List<Store> stores = storeRepository.findAll();
      return stores.stream().map(StoreMapper::toResponse).toList();
   }

   /**
    * Updates an existing store based on the provided ID and request DTO.
    *
    * @param id              the ID of the store to update.
    * @param storeRequestDTO the store request DTO containing the updated details.
    * @return the response DTO of the updated store.
    * @throws EntityNotFoundException if no store with the provided ID is found.
    */
   public StoreResponseDTO updateStore(Long id, StoreRequestDTO storeRequestDTO) {
      Store store = storeRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Store with id " + id + " not found"));

      store.setName(storeRequestDTO.name());
      store.setUrl(storeRequestDTO.url());

      store = storeRepository.save(store);

      return StoreMapper.toResponse(store);
   }

   /**
    * Deletes a store by its ID.
    *
    * @param id the ID of the store to delete.
    */
   public void deleteStore(Long id) {
      storeRepository.deleteById(id);
   }

   /**
    * Searches for stores based on the provided parameters, including pagination
    * and sorting.
    *
    * @param name       the name filter for the search (can be partial).
    * @param pageNumber the page number for pagination.
    * @param pageSize   the size of each page for pagination.
    * @param orderBy    the field to order by.
    * @param direction  the direction of sorting (asc or desc).
    * @return a page of response DTOs for the matching stores.
    */
   public Page<StoreResponseDTO> searchStores(String name, Integer pageNumber, Integer pageSize, String orderBy,
         String direction) {
      Sort sort = Sort.by(Sort.Direction.fromString(direction), orderBy);
      Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

      Page<Store> stores = storeRepository.search(name, pageable);

      return stores.map(StoreMapper::toResponse);
   }
}
