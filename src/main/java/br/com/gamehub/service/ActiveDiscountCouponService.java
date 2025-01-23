/**
 * Service for managing active discount coupons.
 * <p>
 * This service provides methods to create, retrieve, search, delete, and update active discount coupons.
 * It interacts with the {@link ActiveDiscountCouponRepository} and {@link DiscountCouponRepository}.
 * </p>
 * 
 * @author Pedro Lucas
 * @since 2025-01-23
 */
package br.com.gamehub.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.gamehub.dto.response.DiscountCouponResponseDTO;
import br.com.gamehub.mapper.DiscountCouponMapper;
import br.com.gamehub.model.ActiveDiscountCoupon;
import br.com.gamehub.model.DiscountCoupon;
import br.com.gamehub.repository.ActiveDiscountCouponRepository;
import br.com.gamehub.repository.DiscountCouponRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class ActiveDiscountCouponService {

      private final ActiveDiscountCouponRepository activeDiscountCouponRepository;
      private final DiscountCouponRepository discountCouponRepository;

      /**
       * Constructor for initializing the service with the necessary repositories.
       *
       * @param activeDiscountCouponRepository the repository for active discount
       *                                       coupons.
       * @param discountCouponRepository       the repository for discount coupons.
       */
      @Autowired
      public ActiveDiscountCouponService(ActiveDiscountCouponRepository activeDiscountCouponRepository,
                  DiscountCouponRepository discountCouponRepository) {
            this.activeDiscountCouponRepository = activeDiscountCouponRepository;
            this.discountCouponRepository = discountCouponRepository;
      }

      /**
       * Creates an active discount coupon for the given discount coupon ID.
       *
       * @param discountCouponId the ID of the discount coupon.
       * @return the response DTO for the created active discount coupon.
       * @throws EntityNotFoundException if the discount coupon with the provided ID
       *                                 is not found.
       */
      public DiscountCouponResponseDTO createActiveDiscountCoupon(Long discountCouponId) {
            DiscountCoupon discountCoupon = discountCouponRepository.findById(discountCouponId)
                        .orElseThrow(() -> new EntityNotFoundException(
                                    "DiscountCoupon with id " + discountCouponId + " not found"));

            ActiveDiscountCoupon activeDiscountCoupon = ActiveDiscountCoupon.builder()
                        .discountCoupon(discountCoupon).build();
            activeDiscountCoupon = activeDiscountCouponRepository.save(activeDiscountCoupon);

            return DiscountCouponMapper.toResponse(activeDiscountCoupon.getDiscountCoupon());
      }

      /**
       * Retrieves the active discount coupon by its ID.
       *
       * @param discountCouponId the ID of the active discount coupon.
       * @return the response DTO for the active discount coupon.
       * @throws EntityNotFoundException if the active discount coupon with the
       *                                 provided ID is not found.
       */
      public DiscountCouponResponseDTO getActiveDiscountCouponById(Long discountCouponId) {
            ActiveDiscountCoupon activeDiscountCoupon = activeDiscountCouponRepository.findById(discountCouponId)
                        .orElseThrow(() -> new EntityNotFoundException(
                                    "ActiveDiscountCoupon with discountCouponId " + discountCouponId + " not found"));

            return DiscountCouponMapper.toResponse(activeDiscountCoupon.getDiscountCoupon());
      }

      /**
       * Retrieves all active discount coupons.
       *
       * @return a list of response DTOs for all active discount coupons.
       */
      public List<DiscountCouponResponseDTO> getAllActiveDiscountCoupons() {
            List<ActiveDiscountCoupon> activeDiscountCoupons = activeDiscountCouponRepository.findAll();

            return activeDiscountCoupons.stream()
                        .map(ActiveDiscountCoupon::getDiscountCoupon)
                        .map(DiscountCouponMapper::toResponse)
                        .toList();
      }

      /**
       * Deletes the active discount coupon by its ID.
       *
       * @param discountCouponId the ID of the active discount coupon to delete.
       * @throws EntityNotFoundException if the active discount coupon with the
       *                                 provided ID is not found.
       */
      public void deleteActiveDiscountCoupon(Long discountCouponId) {
            ActiveDiscountCoupon activeDiscountCoupon = activeDiscountCouponRepository.findById(discountCouponId)
                        .orElseThrow(() -> new EntityNotFoundException(
                                    "ActiveDiscountCoupon with discountCouponId " + discountCouponId + " not found"));

            activeDiscountCouponRepository.delete(activeDiscountCoupon);
      }

      /**
       * Searches for active discount coupons based on the provided parameters.
       *
       * @param name       the name filter for the search (can be partial).
       * @param pageNumber the page number for pagination.
       * @param pageSize   the size of each page for pagination.
       * @param orderBy    the field to order by.
       * @param direction  the direction of sorting (asc or desc).
       * @return a page of response DTOs for the matching active discount coupons.
       */
      public Page<DiscountCouponResponseDTO> searchActiveDiscountCoupons(String name, Integer pageNumber,
                  Integer pageSize, String orderBy, String direction) {
            Sort sort = Sort.by(Sort.Direction.fromString(direction), orderBy);
            Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

            Page<ActiveDiscountCoupon> activeDiscountCoupons = activeDiscountCouponRepository.search(name, pageable);

            return activeDiscountCoupons.map(ActiveDiscountCoupon::getDiscountCoupon)
                        .map(DiscountCouponMapper::toResponse);
      }

      /**
       * Updates the active discount coupons by removing expired ones and adding new
       * active ones.
       * This method is transactional, ensuring consistency during the process.
       */
      @Transactional
      public void updateActiveDiscountCoupons() {
            List<ActiveDiscountCoupon> activeDiscountCoupons = activeDiscountCouponRepository.findAll();

            for (ActiveDiscountCoupon activeDiscountCoupon : activeDiscountCoupons) {
                  DiscountCoupon discountCoupon = activeDiscountCoupon.getDiscountCoupon();

                  if (discountCoupon.getDeadline() != null
                              && discountCoupon.getDeadline().isBefore(LocalDateTime.now())) {
                        activeDiscountCouponRepository.delete(activeDiscountCoupon);
                  }
            }

            List<DiscountCoupon> discountCoupons = discountCouponRepository.findActiveCoupons();

            for (DiscountCoupon discountCoupon : discountCoupons) {
                  ActiveDiscountCoupon activeDiscountCoupon = ActiveDiscountCoupon.builder()
                              .discountCoupon(discountCoupon).build();
                  activeDiscountCouponRepository.save(activeDiscountCoupon);
            }
      }
}
