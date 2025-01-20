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

import br.com.gamehub.dto.request.DiscountCouponRequestDTO;
import br.com.gamehub.dto.response.DiscountCouponResponseDTO;
import br.com.gamehub.service.DiscountCouponService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/discount-coupon")
@CrossOrigin
public class DiscountCouponController {
   private final DiscountCouponService discountCouponService;

   @Autowired
   public DiscountCouponController(DiscountCouponService discountCouponService) {
      this.discountCouponService = discountCouponService;
   }

   @PostMapping
   public ResponseEntity<DiscountCouponResponseDTO> createDiscountCoupon(
         @Valid @RequestBody DiscountCouponRequestDTO discountCouponRequestDTO) {
      DiscountCouponResponseDTO discountCouponResponseDTO = discountCouponService
            .createDiscountCoupon(discountCouponRequestDTO);

      return new ResponseEntity<>(discountCouponResponseDTO, HttpStatus.CREATED);
   }

   @GetMapping("/{id}")
   public ResponseEntity<DiscountCouponResponseDTO> getDiscountCouponById(@PathVariable("id") Long id) {
      DiscountCouponResponseDTO discountCouponResponseDTO = discountCouponService.getDiscountCouponById(id);

      return new ResponseEntity<>(discountCouponResponseDTO, HttpStatus.OK);
   }

   @GetMapping
   public ResponseEntity<List<DiscountCouponResponseDTO>> getAllDiscountCoupons() {
      List<DiscountCouponResponseDTO> discountCoupons = discountCouponService.getAllDiscountCoupons();

      return new ResponseEntity<>(discountCoupons, HttpStatus.OK);
   }

   @PutMapping("/{id}")
   public ResponseEntity<DiscountCouponResponseDTO> updateDiscountCoupon(@PathVariable("id") Long id,
         @Valid @RequestBody DiscountCouponRequestDTO discountCouponRequestDTO) {
      DiscountCouponResponseDTO discountCouponResponseDTO = discountCouponService
            .updateDiscountCoupon(id, discountCouponRequestDTO);

      return new ResponseEntity<>(discountCouponResponseDTO, HttpStatus.OK);
   }

   @DeleteMapping("/{id}")
   public ResponseEntity<Void> deleteDiscountCoupon(@PathVariable("id") Long id) {
      discountCouponService.deleteDiscountCoupon(id);

      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
   }

   @GetMapping("/search")
   public ResponseEntity<Page<DiscountCouponResponseDTO>> searchDiscountCoupons(
         @RequestParam(defaultValue = "") String name,
         @RequestParam(defaultValue = "0") Integer pageNumber,
         @RequestParam(defaultValue = "10") Integer pageSize,
         @RequestParam(defaultValue = "id_discount_coupon") String orderBy,
         @RequestParam(defaultValue = "asc") String direction) {
      Page<DiscountCouponResponseDTO> discountCoupons = discountCouponService
            .searchDiscountCoupons(name, pageNumber, pageSize, orderBy, direction);

      return new ResponseEntity<>(discountCoupons, HttpStatus.OK);
   }
}
