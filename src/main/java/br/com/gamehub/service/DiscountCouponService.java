/**
 * Service for managing discount coupons in the application.
 * <p>
 * This service provides methods to create, retrieve, update, delete, and search discount coupons.
 * It interacts with the {@link DiscountCouponRepository} and performs necessary operations on {@link DiscountCoupon}.
 * </p>
 * <p>
 * Discount coupons are associated with stores, and this service ensures the store exists before
 * creating or updating a discount coupon.
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

import br.com.gamehub.dto.request.DiscountCouponRequestDTO;
import br.com.gamehub.dto.response.DiscountCouponResponseDTO;
import br.com.gamehub.mapper.DiscountCouponMapper;
import br.com.gamehub.model.DiscountCoupon;
import br.com.gamehub.model.Store;
import br.com.gamehub.repository.DiscountCouponRepository;
import br.com.gamehub.repository.StoreRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class DiscountCouponService {

   private final DiscountCouponRepository discountCouponRepository;
   private final StoreRepository storeRepository;

   /**
    * Constructor for initializing the service with the necessary repositories.
    *
    * @param discountCouponRepository the repository for discount coupons.
    * @param storeRepository          the repository for stores.
    */
   @Autowired
   public DiscountCouponService(DiscountCouponRepository discountCouponRepository, StoreRepository storeRepository) {
      this.discountCouponRepository = discountCouponRepository;
      this.storeRepository = storeRepository;
   }

   /**
    * Creates a new discount coupon using the provided request DTO.
    * <p>
    * Validates the existence of the store associated with the discount coupon
    * before saving it.
    * </p>
    *
    * @param discountCouponRequestDTO the discount coupon request DTO containing
    *                                 the coupon details.
    * @return the response DTO of the created discount coupon.
    * @throws EntityNotFoundException if the store associated with the discount
    *                                 coupon does not exist.
    */
   public DiscountCouponResponseDTO createDiscountCoupon(DiscountCouponRequestDTO discountCouponRequestDTO) {
      Long storeId = discountCouponRequestDTO.storeId();
      Store store = storeRepository.findById(storeId).orElseThrow(
            () -> new EntityNotFoundException("Store with id " + storeId + " not found"));

      DiscountCoupon discountCoupon = DiscountCouponMapper.toEntity(discountCouponRequestDTO, store);
      discountCoupon = discountCouponRepository.save(discountCoupon);

      return DiscountCouponMapper.toResponse(discountCoupon);
   }

   /**
    * Retrieves a discount coupon by its ID.
    *
    * @param id the ID of the discount coupon to retrieve.
    * @return the response DTO of the discount coupon.
    * @throws EntityNotFoundException if no discount coupon with the provided ID is
    *                                 found.
    */
   public DiscountCouponResponseDTO getDiscountCouponById(Long id) {
      DiscountCoupon discountCoupon = discountCouponRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("DiscountCoupon with id " + id + " not found"));

      return DiscountCouponMapper.toResponse(discountCoupon);
   }

   /**
    * Retrieves all discount coupons in the system.
    *
    * @return a list of response DTOs of all discount coupons.
    */
   public List<DiscountCouponResponseDTO> getAllDiscountCoupons() {
      List<DiscountCoupon> discountCoupons = discountCouponRepository.findAll();

      return discountCoupons.stream().map(DiscountCouponMapper::toResponse).toList();
   }

   /**
    * Updates an existing discount coupon based on the provided ID and request DTO.
    *
    * @param id                       the ID of the discount coupon to update.
    * @param discountCouponRequestDTO the discount coupon request DTO containing
    *                                 the updated details.
    * @return the response DTO of the updated discount coupon.
    * @throws EntityNotFoundException if no discount coupon with the provided ID is
    *                                 found.
    */
   public DiscountCouponResponseDTO updateDiscountCoupon(Long id, DiscountCouponRequestDTO discountCouponRequestDTO) {
      DiscountCoupon discountCoupon = discountCouponRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("DiscountCoupon with id " + id + " not found"));

      discountCoupon.setName(discountCouponRequestDTO.name());
      discountCoupon.setCouponType(discountCouponRequestDTO.couponType());
      discountCoupon.setValue(discountCouponRequestDTO.value());
      discountCoupon.setInitialDate(discountCouponRequestDTO.initialDate());
      discountCoupon.setDeadline(discountCouponRequestDTO.deadline());
      discountCoupon.setMinPriceToUse(discountCouponRequestDTO.minPriceToUse());

      discountCoupon = discountCouponRepository.save(discountCoupon);

      return DiscountCouponMapper.toResponse(discountCoupon);
   }

   /**
    * Deletes a discount coupon by its ID.
    *
    * @param id the ID of the discount coupon to delete.
    * @throws EntityNotFoundException if no discount coupon with the provided ID is
    *                                 found.
    */
   public void deleteDiscountCoupon(Long id) {
      DiscountCoupon discountCoupon = discountCouponRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("DiscountCoupon with id " + id + " not found"));

      discountCouponRepository.delete(discountCoupon);
   }

   /**
    * Searches for discount coupons based on the provided parameters, including
    * pagination and sorting.
    *
    * @param name       the name filter for the search (can be partial).
    * @param pageNumber the page number for pagination.
    * @param pageSize   the size of each page for pagination.
    * @param orderBy    the field to order by.
    * @param direction  the direction of sorting (asc or desc).
    * @return a page of response DTOs for the matching discount coupons.
    */
   public Page<DiscountCouponResponseDTO> searchDiscountCoupons(
         String name,
         Integer pageNumber,
         Integer pageSize,
         String orderBy,
         String direction) {
      Sort sort = Sort.by(Sort.Direction.fromString(direction), orderBy);
      Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

      Page<DiscountCoupon> discountCoupons = discountCouponRepository.search(name, pageable);

      return discountCoupons.map(DiscountCouponMapper::toResponse);
   }
}
