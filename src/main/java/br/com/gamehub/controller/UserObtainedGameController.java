package br.com.gamehub.controller;

import br.com.gamehub.dto.request.UserObtainedGameRequestDTO;
import br.com.gamehub.dto.response.UserObtainedGameResponseDTO;
import br.com.gamehub.exception.EntityNotFoundException;
import br.com.gamehub.model.UserObtainedGame;
import br.com.gamehub.service.UserObtainedGameService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UserObtainedGameController {
     private final UserObtainedGameService obtainedGameService;

     @Autowired
     public UserObtainedGameController(UserObtainedGameService obtainedGameService) {
          this.obtainedGameService = obtainedGameService;
     }

     @PostMapping("/{userid}/jogos-obtidos")
     public ResponseEntity<UserObtainedGameResponseDTO> createObtainedGame(@PathVariable Long userid, @Valid  @RequestBody UserObtainedGameRequestDTO dto) {
         UserObtainedGameResponseDTO gameResponseDTO = obtainedGameService.createObtainedGame(userid, dto);
         return new ResponseEntity<>(gameResponseDTO, HttpStatus.CREATED);
     }

     @GetMapping("/{userid}/jogos-obtidos/{obtainedGameId}")
     public ResponseEntity<UserObtainedGameResponseDTO> getObtainedGameById(@PathVariable Long userid, @PathVariable Long obtainedGameId) {
        UserObtainedGameResponseDTO gameResponseDTO = obtainedGameService.getObtainedGameById(userid, obtainedGameId);

        if (gameResponseDTO != null){
            return new ResponseEntity<>(gameResponseDTO, HttpStatus.OK );
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
     }

     @GetMapping("/{userid}/jogos-obtidos/")
     public ResponseEntity<List<UserObtainedGameResponseDTO>> getAllObtainedGames(@PathVariable Long userid) {
         List<UserObtainedGameResponseDTO> obtainedGameList = obtainedGameService.getAllObtainedGames(userid);
         return new ResponseEntity<>(obtainedGameList, HttpStatus.OK);
     }



    @DeleteMapping("/{userid}/jogos-obtidos/{obtainedGameId}")
    public ResponseEntity<Void> deleteObtainedGame(@PathVariable Long userid, @PathVariable Long obtainedGameId) {
        try {
            obtainedGameService.deleteObtainedGame(userid, obtainedGameId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Sucesso, sem conteúdo retornado
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Caso o jogo não seja encontrado
        }
    }

}
