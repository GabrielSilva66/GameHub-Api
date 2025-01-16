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

import br.com.gamehub.dto.request.CategoryRequestDTO;
import br.com.gamehub.dto.response.CategoryResponseDTO;
import br.com.gamehub.service.CategoryService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/categories")
@CrossOrigin
public class CategoryController {
   private final CategoryService categoryService;

   @Autowired
   public CategoryController(CategoryService categoryService) {
      this.categoryService = categoryService;
   }

   @PostMapping
   public ResponseEntity<CategoryResponseDTO> createCategory(
         @Valid @RequestBody CategoryRequestDTO categoryRequestDTO) {
      CategoryResponseDTO categoryResponseDTO = categoryService.createCategory(categoryRequestDTO);

      return new ResponseEntity<>(categoryResponseDTO, HttpStatus.CREATED);
   }

   @GetMapping("/{id}")
   public ResponseEntity<CategoryResponseDTO> getCategoryById(@PathVariable("id") Long id) {
      CategoryResponseDTO categoryResponseDTO = categoryService.getCategoryById(id);

      return new ResponseEntity<>(categoryResponseDTO, HttpStatus.OK);
   }

   @GetMapping
   public ResponseEntity<List<CategoryResponseDTO>> getAllCategories() {
      List<CategoryResponseDTO> categoryResponseDTOs = categoryService.getAllCategories();

      return new ResponseEntity<>(categoryResponseDTOs, HttpStatus.OK);
   }

   @PutMapping("/{id}")
   public ResponseEntity<CategoryResponseDTO> updateCategory(@PathVariable("id") Long id,
         @RequestBody CategoryRequestDTO categoryRequestDTO) {
      CategoryResponseDTO categoryResponseDTO = categoryService.updateCategory(id, categoryRequestDTO);

      return new ResponseEntity<>(categoryResponseDTO, HttpStatus.OK);
   }

   @DeleteMapping("/{id}")
   public ResponseEntity<Void> deleteCategory(@PathVariable("id") Long id) {
      categoryService.deleteCategory(id);

      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
   }

   @GetMapping("/search")
   public ResponseEntity<Page<CategoryResponseDTO>> searchCategories(
         @RequestParam(defaultValue = "") String name,
         @RequestParam(defaultValue = "0") Integer page,
         @RequestParam(defaultValue = "10") Integer size,
         @RequestParam(defaultValue = "id_category") String orderBy,
         @RequestParam(defaultValue = "asc") String direction) {
      Page<CategoryResponseDTO> categoryResponseDTOs = categoryService.searchCategories(name, page, size, orderBy,
            direction);

      return new ResponseEntity<>(categoryResponseDTOs, HttpStatus.OK);
   }

}
