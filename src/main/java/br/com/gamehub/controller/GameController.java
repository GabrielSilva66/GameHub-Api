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

import br.com.gamehub.dto.request.GameCategoryRequestDTO;
import br.com.gamehub.dto.request.GamePlatformRequestDTO;
import br.com.gamehub.dto.request.GameRequestDTO;
import br.com.gamehub.dto.response.GameResponseDTO;
import br.com.gamehub.service.GameService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/games")
@CrossOrigin
public class GameController {
   private final GameService gameService;

   @Autowired
   public GameController(GameService gameService) {
      this.gameService = gameService;
   }

   @PostMapping
   public ResponseEntity<GameResponseDTO> createGame(@Valid @RequestBody GameRequestDTO gameRequestDTO) {
      GameResponseDTO gameResponseDTO = gameService.createGame(gameRequestDTO);

      return new ResponseEntity<GameResponseDTO>(gameResponseDTO, HttpStatus.CREATED);
   }

   @GetMapping("/{id}")
   public ResponseEntity<GameResponseDTO> getGameById(@PathVariable("id") Long id) {
      GameResponseDTO gameResponseDTO = gameService.getGameById(id);

      return new ResponseEntity<GameResponseDTO>(gameResponseDTO, HttpStatus.OK);
   }

   @GetMapping
   public ResponseEntity<List<GameResponseDTO>> getAllGames() {
      List<GameResponseDTO> gameResponseDTOs = gameService.getAllGames();

      return new ResponseEntity<List<GameResponseDTO>>(gameResponseDTOs, HttpStatus.OK);
   }

   @PutMapping("/{id}")
   public ResponseEntity<GameResponseDTO> updateGame(@PathVariable("id") Long id,
         @Valid @RequestBody GameRequestDTO gameRequestDTO) {
      GameResponseDTO gameResponseDTO = gameService.updateGame(id, gameRequestDTO);

      return new ResponseEntity<GameResponseDTO>(gameResponseDTO, HttpStatus.OK);
   }

   @DeleteMapping("/{id}")
   public ResponseEntity<Void> deleteGame(@PathVariable("id") Long id) {
      gameService.deleteGame(id);

      return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
   }

   @GetMapping("/search")
   public ResponseEntity<Page<GameResponseDTO>> searchGames(
         @RequestParam(defaultValue = "") String name,
         @RequestParam(defaultValue = "0") Integer page,
         @RequestParam(defaultValue = "10") Integer size,
         @RequestParam(defaultValue = "id_game") String orderBy,
         @RequestParam(defaultValue = "asc") String direction) {
      Page<GameResponseDTO> gameResponseDTOs = gameService.searchGames(name, page, size, orderBy,
            direction);

      return new ResponseEntity<>(gameResponseDTOs, HttpStatus.OK);
   }

   @PostMapping("/{id}/categories")
   public ResponseEntity<Void> addCategory(@PathVariable("id") Long id,
         @RequestBody GameCategoryRequestDTO gameCategoryRequestDTO) {
      gameService.addCategoriesToGame(id, gameCategoryRequestDTO);

      return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
   }

   @DeleteMapping("/{id}/categories")
   public ResponseEntity<Void> removeCategory(@PathVariable("id") Long id,
         @RequestBody GameCategoryRequestDTO gameCategoryRequestDTO) {
      gameService.removeCategoriesFromGame(id, gameCategoryRequestDTO);

      return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
   }

   @PostMapping("/{id}/platforms")
   public ResponseEntity<Void> addPlatform(@PathVariable("id") Long id, GamePlatformRequestDTO gamePlatformRequestDTO) {
      gameService.addPlatformsToGame(id, gamePlatformRequestDTO);

      return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
   }

   @DeleteMapping("/{id}/platforms")
   public ResponseEntity<Void> removePlatform(@PathVariable("id") Long id,
         GamePlatformRequestDTO gamePlatformRequestDTO) {
      gameService.removePlatformsFromGame(id, gamePlatformRequestDTO);

      return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
   }
}
