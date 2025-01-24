package br.com.gamehub.controller;

import br.com.gamehub.dto.request.UserRequestDTO;
import br.com.gamehub.dto.response.UserResponseDTO;
import br.com.gamehub.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller responsible for managing user operations.
 * Provides endpoints to create, retrieve, and list users.
 */
@RestController
@RequestMapping("/usuarios")
@CrossOrigin
public class UserController {

    private final UserService userService;

    /**
     * Constructor for the UserController.
     *
     * @param userService The user service to be injected by Spring.
     */
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Creates a new user.
     *
     * This endpoint accepts user details in the request body and creates a new user in the system.
     *
     * @param userRequestDTO The data transfer object containing the user information to be created.
     * @return A response containing the created user's information, with status 201 (Created).
     */
    @PostMapping("/cadastrar")
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserRequestDTO userRequestDTO) {
        UserResponseDTO userResponseDTO = userService.createUser(userRequestDTO);
        return new ResponseEntity<>(userResponseDTO, HttpStatus.CREATED);
    }

    /**
     * Retrieves a list of all users.
     *
     * This endpoint returns all users in the system.
     *
     * @return A response containing a list of all users, with status 200 (OK).
     */
    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<UserResponseDTO> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    /**
     * Retrieves a user by their ID.
     *
     * This endpoint fetches a user's details based on their unique ID.
     *
     * @param id The ID of the user to retrieve.
     * @return A response containing the user details if found, or a 404 (Not Found) status if the user does not exist.
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable("id") Long id) {
        UserResponseDTO userResponseDTO = userService.getUserById(id);

        if(userResponseDTO != null){
            return new ResponseEntity<>(userResponseDTO, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // TODO: Add methods for updating and deleting users
}
