package br.com.gamehub.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.gamehub.dto.request.StoreRequestDTO;
import br.com.gamehub.dto.response.StoreResponseDTO;
import br.com.gamehub.service.StoreService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/stores")
@CrossOrigin
public class StoreController {
   private final StoreService storeService;

   @Autowired
   public StoreController(StoreService storeService) {
      this.storeService = storeService;
   }

   @PostMapping
   public ResponseEntity<StoreResponseDTO> createStore(@Valid @RequestBody StoreRequestDTO storeRequestDTO) {
      StoreResponseDTO storeResponseDTO = storeService.createStore(storeRequestDTO);

      return new ResponseEntity<StoreResponseDTO>(storeResponseDTO, HttpStatus.CREATED);
   }

   @GetMapping("/{id}")
   public ResponseEntity<StoreResponseDTO> getStoreById(@PathVariable("id") Long id) {
      StoreResponseDTO storeResponseDTO = storeService.getStoreById(id);

      return new ResponseEntity<StoreResponseDTO>(storeResponseDTO, HttpStatus.OK);
   }

   @GetMapping
   public ResponseEntity<List<StoreResponseDTO>> getAllStores() {
      List<StoreResponseDTO> storeResponseDTOs = storeService.getAllStores();

      return new ResponseEntity<List<StoreResponseDTO>>(storeResponseDTOs, HttpStatus.OK);
   }

   @PutMapping("/{id}")
   public ResponseEntity<StoreResponseDTO> updateStore(@PathVariable("id") Long id,
         @Valid @RequestBody StoreRequestDTO storeRequestDTO) {
      StoreResponseDTO storeResponseDTO = storeService.updateStore(id, storeRequestDTO);

      return new ResponseEntity<StoreResponseDTO>(storeResponseDTO, HttpStatus.OK);
   }

   @DeleteMapping("/{id}")
   public ResponseEntity<Void> deleteStore(@PathVariable("id") Long id) {
      storeService.deleteStore(id);

      return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
   }

   @GetMapping("/search")
   public ResponseEntity<Page<StoreResponseDTO>> searchStores(
         @RequestParam(defaultValue = "") String name,
         @RequestParam(defaultValue = "0") Integer pageNumber,
         @RequestParam(defaultValue = "10") Integer pageSize,
         @RequestParam(defaultValue = "id_store") String orderBy,
         @RequestParam(defaultValue = "asc") String direction) {
      Page<StoreResponseDTO> storeResponseDTOs = storeService.searchStores(name, pageNumber, pageSize, orderBy,
            direction);

      return new ResponseEntity<Page<StoreResponseDTO>>(storeResponseDTOs, HttpStatus.OK);
   }
}
