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

import br.com.gamehub.dto.request.StoreGameRequestDTO;
import br.com.gamehub.dto.response.StoreGameResponseDTO;
import br.com.gamehub.service.StoreGameService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("store-games")
@CrossOrigin
public class StoreGameController {
   private final StoreGameService storeGameService;

   @Autowired
   public StoreGameController(StoreGameService storeGameService) {
      this.storeGameService = storeGameService;
   }

   @PostMapping
   public ResponseEntity<StoreGameResponseDTO> createStoreGame(
         @Valid @RequestBody StoreGameRequestDTO storeGameRequestDTO) {
      StoreGameResponseDTO storeGameResponseDTO = storeGameService.createStoreGame(storeGameRequestDTO);

      return new ResponseEntity<>(storeGameResponseDTO, HttpStatus.CREATED);
   }

   @GetMapping("/{storeId}/{gameId}")
   public ResponseEntity<StoreGameResponseDTO> getStoreGameById(@PathVariable("storeId") Long storeId,
         @PathVariable("gameId") Long gameId) {
      StoreGameResponseDTO storeGameResponseDTO = storeGameService.getStoreGameById(storeId, gameId);

      return new ResponseEntity<>(storeGameResponseDTO, HttpStatus.OK);
   }

   @GetMapping("/store/{storeId}")
   public ResponseEntity<List<StoreGameResponseDTO>> getStoreGamesByStoreId(@PathVariable("storeId") Long storeId) {
      List<StoreGameResponseDTO> storeGameResponseDTOs = storeGameService.getStoreGamesByStoreId(storeId);

      return new ResponseEntity<>(storeGameResponseDTOs, HttpStatus.OK);
   }

   @GetMapping("/game/{gameId}")
   public ResponseEntity<List<StoreGameResponseDTO>> getStoreGamesByGameId(@PathVariable("gameId") Long gameId) {
      List<StoreGameResponseDTO> storeGameResponseDTOs = storeGameService.getStoreGamesByGameId(gameId);

      return new ResponseEntity<>(storeGameResponseDTOs, HttpStatus.OK);
   }

   @GetMapping
   public ResponseEntity<List<StoreGameResponseDTO>> getAllStoreGames() {
      List<StoreGameResponseDTO> storeGameResponseDTOs = storeGameService.getAllStoreGames();

      return new ResponseEntity<>(storeGameResponseDTOs, HttpStatus.OK);
   }

   @PutMapping
   public ResponseEntity<StoreGameResponseDTO> updateStoreGame(
         @Valid @RequestBody StoreGameRequestDTO storeGameRequestDTO) {
      StoreGameResponseDTO storeGameResponseDTO = storeGameService.updateStoreGame(storeGameRequestDTO);

      return new ResponseEntity<>(storeGameResponseDTO, HttpStatus.OK);
   }

   @DeleteMapping("/{storeId}/{gameId}")
   public ResponseEntity<Void> deleteStoreGame(@PathVariable("storeId") Long storeId,
         @PathVariable("gameId") Long gameId) {
      storeGameService.deleteStoreGame(storeId, gameId);

      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
   }

   @GetMapping("/search")
   public ResponseEntity<Page<StoreGameResponseDTO>> searchStoreGames(
         @RequestParam(required = false) Long storeId,
         @RequestParam(required = false) Long gameId,
         @RequestParam(required = false) Double minPrice,
         @RequestParam(required = false) Double maxPrice,
         @RequestParam(defaultValue = "0") Integer pageNumber,
         @RequestParam(defaultValue = "10") Integer pageSize,
         @RequestParam(defaultValue = "id_game") String orderBy,
         @RequestParam(defaultValue = "asc") String direction) {
      Page<StoreGameResponseDTO> storeGameResponseDTOs = storeGameService.searchStoreGames(
            storeId, gameId, minPrice, maxPrice, pageNumber, pageSize, orderBy, direction);

      return new ResponseEntity<>(storeGameResponseDTOs, HttpStatus.OK);
   }
}
