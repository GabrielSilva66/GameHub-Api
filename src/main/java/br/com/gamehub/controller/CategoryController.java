/**
 * CategoryController
 * 
 * <p>
 * Controller for Category resource, exposing endpoints for
 * searching, retrieving and listing categories.
 * </p>
 * 
 * @author Pedro Lucas
 * @since 2025-01-23
 */
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
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import jakarta.validation.Valid;

@Tag(name = "Category", description = "Endpoints for Category resource.")
@RestController
@RequestMapping("/categories")
@CrossOrigin
public class CategoryController {
   private final CategoryService categoryService;

   /**
    * Constructor for CategoryController.
    * 
    * @param categoryService the service for Category resource.
    */
   @Autowired
   public CategoryController(CategoryService categoryService) {
      this.categoryService = categoryService;
   }

   /**
    * Creates a new category.
    * 
    * @param categoryRequestDTO the request body containing the category data.
    * @return a ResponseEntity containing the created category.
    */
   @PostMapping
   @Operation(summary = "Create a new category", description = "Endpoint for creating a new category")
   @ApiResponse(responseCode = "201", description = "Category created successfully", content = @Content(schema = @Schema(implementation = CategoryResponseDTO.class)))
   public ResponseEntity<CategoryResponseDTO> createCategory(
         @Valid @RequestBody CategoryRequestDTO categoryRequestDTO) {
      CategoryResponseDTO categoryResponseDTO = categoryService.createCategory(categoryRequestDTO);

      return new ResponseEntity<>(categoryResponseDTO, HttpStatus.CREATED);
   }

   /**
    * Retrieves a category by its ID.
    * 
    * @param id the ID of the category to retrieve.
    * @return a ResponseEntity containing the retrieved category.
    */
   @GetMapping("/{id}")
   @Operation(summary = "Get category by ID", description = "Endpoint for retrieves a category by its ID")
   @ApiResponse(responseCode = "200", description = "Category found.", content = @Content(schema = @Schema(implementation = CategoryResponseDTO.class)))
   @ApiResponse(responseCode = "404", description = "Category not found.", content = @Content())
   public ResponseEntity<CategoryResponseDTO> getCategoryById(
         @Parameter(description = "Category ID", example = "1") @PathVariable("id") Long id) {
      CategoryResponseDTO categoryResponseDTO = categoryService.getCategoryById(id);

      return new ResponseEntity<>(categoryResponseDTO, HttpStatus.OK);
   }

   /**
    * Retrieves all categories.
    * 
    * @return a ResponseEntity containing a list of all categories.
    */
   @GetMapping
   @Operation(summary = "Get all categories", description = "Endpoint for retrieves all categories")
   @ApiResponse(responseCode = "200", description = "Categories found.", content = @Content(array = @ArraySchema(schema = @Schema(implementation = CategoryResponseDTO.class))))
   public ResponseEntity<List<CategoryResponseDTO>> getAllCategories() {
      List<CategoryResponseDTO> categoryResponseDTOs = categoryService.getAllCategories();

      return new ResponseEntity<>(categoryResponseDTOs, HttpStatus.OK);
   }

   /**
    * Updates an existing category.
    * 
    * @param id                 the ID of the category to update.
    * @param categoryRequestDTO the request body containing the updated category
    *                           data.
    * @return a ResponseEntity containing the updated category.
    */
   @PutMapping("/{id}")
   @Operation(summary = "Update a category", description = "Endpoint for updating a category")
   @ApiResponse(responseCode = "200", description = "Category updated successfully", content = @Content(schema = @Schema(implementation = CategoryResponseDTO.class)))
   @ApiResponse(responseCode = "404", description = "Category not found.", content = @Content())
   public ResponseEntity<CategoryResponseDTO> updateCategory(
         @Parameter(description = "Category ID", example = "1") @PathVariable("id") Long id,
         @RequestBody CategoryRequestDTO categoryRequestDTO) {
      CategoryResponseDTO categoryResponseDTO = categoryService.updateCategory(id, categoryRequestDTO);

      return new ResponseEntity<>(categoryResponseDTO, HttpStatus.OK);
   }

   /**
    * Deletes a category by its ID.
    * 
    * @param id the ID of the category to delete.
    * @return a ResponseEntity indicating the deletion status.
    */
   @DeleteMapping("/{id}")
   @Operation(summary = "Delete a category", description = "Endpoint for deleting a category")
   @ApiResponse(responseCode = "204", description = "Category deleted successfully", content = @Content())
   @ApiResponse(responseCode = "404", description = "Category not found.", content = @Content())
   public ResponseEntity<Void> deleteCategory(
         @Parameter(description = "Category ID", example = "1") @PathVariable("id") Long id) {
      categoryService.deleteCategory(id);

      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
   }

   /**
    * Searches for categories by name.
    *
    * @param name      the name of the category to search for.
    * @param page      the page number to retrieve.
    * @param size      the page size to retrieve.
    * @param orderBy   the field to order by.
    * @param direction the direction of the ordering.
    * @return
    */
   @GetMapping("/search")
   @Operation(summary = "Search categories by name", description = "Endpoint for searches for categories by name.")
   @ApiResponse(responseCode = "200", description = "Categories found.", content = @Content(schema = @Schema(implementation = Page.class)))
   public ResponseEntity<Page<CategoryResponseDTO>> searchCategories(
         @Parameter(description = "Category name", example = "Action") @RequestParam(defaultValue = "") String name,
         @Parameter(description = "Category page number", example = "0") @RequestParam(defaultValue = "0") Integer page,
         @Parameter(description = "Category page size", example = "10") @RequestParam(defaultValue = "10") Integer size,
         @Parameter(description = "Category order by", example = "id_category") @RequestParam(defaultValue = "id_category") String orderBy,
         @Parameter(description = "Category direction", example = "asc") @RequestParam(defaultValue = "asc") String direction) {
      Page<CategoryResponseDTO> categoryResponseDTOs = categoryService.searchCategories(name, page, size, orderBy,
            direction);

      return new ResponseEntity<>(categoryResponseDTOs, HttpStatus.OK);
   }

}
