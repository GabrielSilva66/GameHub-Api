package br.com.gamehub.controller;

import br.com.gamehub.dto.request.UserWishlistGameRequestDTO;
import br.com.gamehub.dto.response.UserWishlistGameResponseDTO;
import br.com.gamehub.exception.EntityNotFoundException;
import br.com.gamehub.service.UserWishlistGameService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UserWishlistGameController {
     private final UserWishlistGameService wishlistGameService;

     @Autowired
     public UserWishlistGameController(UserWishlistGameService wishlistGameService) {
          this.wishlistGameService = wishlistGameService;
     }

     @PostMapping("/{userid}/jogos-desejados")
     public ResponseEntity<UserWishlistGameResponseDTO> createWishlistGame(@PathVariable Long userid, @Valid  @RequestBody UserWishlistGameRequestDTO dto) {
         UserWishlistGameResponseDTO gameResponseDTO = wishlistGameService.createWishlistGame(userid, dto);
         return new ResponseEntity<>(gameResponseDTO, HttpStatus.CREATED);
     }

     @GetMapping("/{userid}/jogos-desejados/{wishlistGameId}")
     public ResponseEntity<UserWishlistGameResponseDTO> getWishlistGameById(@PathVariable Long userid, @PathVariable Long wishlistGameId) {
        UserWishlistGameResponseDTO gameResponseDTO = wishlistGameService.getWishlistGameById(userid, wishlistGameId);

        if (gameResponseDTO != null){
            return new ResponseEntity<>(gameResponseDTO, HttpStatus.OK );
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
     }

     @GetMapping("/{userid}/jogos-desejados")
     public ResponseEntity<List<UserWishlistGameResponseDTO>> getAllWishlistGames(@PathVariable Long userid) {
         List<UserWishlistGameResponseDTO> wishlistGameList = wishlistGameService.getAllWishlistGames(userid);
         return new ResponseEntity<>(wishlistGameList, HttpStatus.OK);
     }



    @DeleteMapping("/{userid}/jogos-desejados/{wishlistGameId}")
    public ResponseEntity<Void> deleteWishlistGame(@PathVariable Long userid, @PathVariable Long wishlistGameId) {
        try {
            wishlistGameService.deleteWishlistGame(userid, wishlistGameId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Sucesso, sem conteúdo retornado
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Caso o jogo não seja encontrado
        }
    }

}
