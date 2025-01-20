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
import br.com.gamehub.repository.DiscountCouponRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class DiscountCouponService {
   private final DiscountCouponRepository discountCouponRepository;

   @Autowired
   public DiscountCouponService(DiscountCouponRepository discountCouponRepository) {
      this.discountCouponRepository = discountCouponRepository;
   }

   public DiscountCouponResponseDTO createDiscountCoupon(DiscountCouponRequestDTO discountCouponRequestDTO) {
      DiscountCoupon discountCoupon = DiscountCouponMapper.toEntity(discountCouponRequestDTO);
      discountCoupon = discountCouponRepository.save(discountCoupon);

      return DiscountCouponMapper.toResponse(discountCoupon);
   }

   public DiscountCouponResponseDTO getDiscountCouponById(Long id) {
      DiscountCoupon discountCoupon = discountCouponRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("DiscountCoupon with id " + id + " not found"));

      return DiscountCouponMapper.toResponse(discountCoupon);
   }

   public List<DiscountCouponResponseDTO> getAllDiscountCoupons() {
      List<DiscountCoupon> discountCoupons = discountCouponRepository.findAll();

      return discountCoupons.stream().map(DiscountCouponMapper::toResponse).toList();
   }

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

   public void deleteDiscountCoupon(Long id) {
      DiscountCoupon discountCoupon = discountCouponRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("DiscountCoupon with id " + id + " not found"));

      discountCouponRepository.delete(discountCoupon);
   }

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
