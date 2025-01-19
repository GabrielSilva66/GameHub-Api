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

   @Autowired
   public CategoryService(CategoryRepository categoryRepository) {
      this.categoryRepository = categoryRepository;
   }

   public CategoryResponseDTO createCategory(CategoryRequestDTO categoryRequestDTO) {
      Category category = CategoryMapper.toEntity(categoryRequestDTO);
      category = categoryRepository.save(category);

      return CategoryMapper.toResponse(category);
   }

   public CategoryResponseDTO getCategoryById(Long id) {
      Category category = categoryRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Category with id " + id + " not found"));

      return CategoryMapper.toResponse(category);
   }

   public List<CategoryResponseDTO> getAllCategories() {
      List<Category> categories = categoryRepository.findAll();
      return categories.stream().map(CategoryMapper::toResponse).toList();
   }

   public CategoryResponseDTO updateCategory(Long id, CategoryRequestDTO categoryRequestDTO) {
      Category category = categoryRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Category with id " + id + " not found"));

      category.setName(categoryRequestDTO.name());

      category = categoryRepository.save(category);

      return CategoryMapper.toResponse(category);
   }

   public void deleteCategory(Long id) {
      categoryRepository.deleteById(id);
   }

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
