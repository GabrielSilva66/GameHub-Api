/**
 * Service for managing categories in the application.
 * <p>
 * This service provides methods to create, retrieve, update, delete, and search categories.
 * It interacts with the {@link CategoryRepository} and performs necessary operations on {@link Category}.
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

import br.com.gamehub.dto.request.CategoryRequestDTO;
import br.com.gamehub.dto.response.CategoryResponseDTO;
import br.com.gamehub.mapper.CategoryMapper;
import br.com.gamehub.model.Category;
import br.com.gamehub.repository.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class CategoryService {

   private final CategoryRepository categoryRepository;

   /**
    * Constructor for initializing the service with the necessary repository.
    *
    * @param categoryRepository the repository for categories.
    */
   @Autowired
   public CategoryService(CategoryRepository categoryRepository) {
      this.categoryRepository = categoryRepository;
   }

   /**
    * Creates a new category using the provided request DTO.
    *
    * @param categoryRequestDTO the category request DTO containing the category
    *                           details.
    * @return the response DTO of the created category.
    */
   public CategoryResponseDTO createCategory(CategoryRequestDTO categoryRequestDTO) {
      Category category = CategoryMapper.toEntity(categoryRequestDTO);
      category = categoryRepository.save(category);

      return CategoryMapper.toResponse(category);
   }

   /**
    * Retrieves a category by its ID.
    *
    * @param id the ID of the category to retrieve.
    * @return the response DTO of the category.
    * @throws EntityNotFoundException if no category with the provided ID is found.
    */
   public CategoryResponseDTO getCategoryById(Long id) {
      Category category = categoryRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Category with id " + id + " not found"));

      return CategoryMapper.toResponse(category);
   }

   /**
    * Retrieves all categories in the system.
    *
    * @return a list of response DTOs of all categories.
    */
   public List<CategoryResponseDTO> getAllCategories() {
      List<Category> categories = categoryRepository.findAll();
      return categories.stream().map(CategoryMapper::toResponse).toList();
   }

   /**
    * Updates an existing category based on the provided ID and request DTO.
    *
    * @param id                 the ID of the category to update.
    * @param categoryRequestDTO the category request DTO containing the updated
    *                           details.
    * @return the response DTO of the updated category.
    * @throws EntityNotFoundException if no category with the provided ID is found.
    */
   public CategoryResponseDTO updateCategory(Long id, CategoryRequestDTO categoryRequestDTO) {
      Category category = categoryRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Category with id " + id + " not found"));

      category.setName(categoryRequestDTO.name());

      category = categoryRepository.save(category);

      return CategoryMapper.toResponse(category);
   }

   /**
    * Deletes a category by its ID.
    *
    * @param id the ID of the category to delete.
    * @throws EntityNotFoundException if no category with the provided ID is found.
    */
   public void deleteCategory(Long id) {
      categoryRepository.deleteById(id);
   }

   /**
    * Searches for categories based on the provided parameters, including
    * pagination and sorting.
    *
    * @param name      the name filter for the search (can be partial).
    * @param page      the page number for pagination.
    * @param size      the size of each page for pagination.
    * @param orderBy   the field to order by.
    * @param direction the direction of sorting (asc or desc).
    * @return a page of response DTOs for the matching categories.
    */
   public Page<CategoryResponseDTO> searchCategories(String name,
         Integer page,
         Integer size,
         String orderBy,
         String direction) {
      Sort sort = Sort.by(Sort.Direction.fromString(direction), orderBy);
      Pageable pageable = PageRequest.of(page, size, sort);

      Page<Category> categories = categoryRepository.search(name, pageable);
      return categories.map(CategoryMapper::toResponse);
   }
}
