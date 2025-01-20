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

      @Autowired
      public ActiveDiscountCouponService(ActiveDiscountCouponRepository activeDiscountCouponRepository,
                  DiscountCouponRepository discountCouponRepository) {
            this.activeDiscountCouponRepository = activeDiscountCouponRepository;
            this.discountCouponRepository = discountCouponRepository;
      }

      public DiscountCouponResponseDTO createActiveDiscountCoupon(Long discountCouponId) {
            DiscountCoupon discountCoupon = discountCouponRepository.findById(discountCouponId)
                        .orElseThrow(
                                    () -> new EntityNotFoundException(
                                                "DiscountCoupon with id " + discountCouponId + " not found"));

            ActiveDiscountCoupon activeDiscountCoupon = ActiveDiscountCoupon.builder().discountCoupon(discountCoupon)
                        .build();
            activeDiscountCoupon = activeDiscountCouponRepository.save(activeDiscountCoupon);

            return DiscountCouponMapper.toResponse(activeDiscountCoupon.getDiscountCoupon());
      }

      public DiscountCouponResponseDTO getActiveDiscountCouponById(Long discountCouponId) {
            ActiveDiscountCoupon activeDiscountCoupon = activeDiscountCouponRepository.findById(discountCouponId)
                        .orElseThrow(
                                    () -> new EntityNotFoundException(
                                                "ActiveDiscountCoupon with discountCouponId " + discountCouponId
                                                            + " not found"));

            return DiscountCouponMapper.toResponse(activeDiscountCoupon.getDiscountCoupon());
      }

      public List<DiscountCouponResponseDTO> getAllActiveDiscountCoupons() {
            List<ActiveDiscountCoupon> activeDiscountCoupons = activeDiscountCouponRepository.findAll();

            return activeDiscountCoupons.stream()
                        .map(ActiveDiscountCoupon::getDiscountCoupon).map(DiscountCouponMapper::toResponse).toList();
      }

      public void deleteActiveDiscountCoupon(Long discountCouponId) {
            ActiveDiscountCoupon activeDiscountCoupon = activeDiscountCouponRepository.findById(discountCouponId)
                        .orElseThrow(
                                    () -> new EntityNotFoundException(
                                                "ActiveDiscountCoupon with discountCouponId " + discountCouponId
                                                            + " not found"));

            activeDiscountCouponRepository.delete(activeDiscountCoupon);
      }

      public Page<DiscountCouponResponseDTO> searchActiveDiscountCoupons(String name, Integer pageNumber,
                  Integer pageSize, String orderBy, String direction) {
            Sort sort = Sort.by(Sort.Direction.fromString(direction), orderBy);
            Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

            Page<ActiveDiscountCoupon> activeDiscountCoupons = activeDiscountCouponRepository
                        .search(name, pageable);

            return activeDiscountCoupons.map(ActiveDiscountCoupon::getDiscountCoupon)
                        .map(DiscountCouponMapper::toResponse);
      }

      @Transactional
      public void updateActiveDiscountCoupons() {
            List<ActiveDiscountCoupon> activeDiscountCoupons = activeDiscountCouponRepository.findAll();

            for (ActiveDiscountCoupon activeDiscountCoupon : activeDiscountCoupons) {
                  DiscountCoupon discountCoupon = activeDiscountCoupon.getDiscountCoupon();

                  if (discountCoupon.getDeadline().isBefore(LocalDateTime.now())) {
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
