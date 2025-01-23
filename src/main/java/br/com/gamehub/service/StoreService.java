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

   @Autowired
   public StoreService(StoreRepository storeRepository) {
      this.storeRepository = storeRepository;
   }

   public StoreResponseDTO createStore(StoreRequestDTO storeRequestDTO) {
      Store store = StoreMapper.toEntity(storeRequestDTO);
      store = storeRepository.save(store);

      return StoreMapper.toResponse(store);
   }

   public StoreResponseDTO getStoreById(Long id) {
      Store store = storeRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Store with id " + id + " not found"));

      return StoreMapper.toResponse(store);
   }

   public List<StoreResponseDTO> getAllStores() {
      List<Store> stores = storeRepository.findAll();
      return stores.stream().map(StoreMapper::toResponse).toList();
   }

   public StoreResponseDTO updateStore(Long id, StoreRequestDTO storeRequestDTO) {
      Store store = storeRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Store with id " + id + " not found"));

      store.setName(storeRequestDTO.name());
      store.setUrl(storeRequestDTO.url());

      store = storeRepository.save(store);

      return StoreMapper.toResponse(store);
   }

   public void deleteStore(Long id) {
      storeRepository.deleteById(id);
   }

   public Page<StoreResponseDTO> searchStores(String name, Integer pageNumber, Integer pageSize, String orderBy,
         String direction) {
      Sort sort = Sort.by(Sort.Direction.fromString(direction), orderBy);
      Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

      Page<Store> stores = storeRepository.search(name, pageable);

      return stores.map(StoreMapper::toResponse);
   }
}
