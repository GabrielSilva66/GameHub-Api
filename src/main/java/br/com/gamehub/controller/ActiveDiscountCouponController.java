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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.gamehub.dto.response.DiscountCouponResponseDTO;
import br.com.gamehub.service.ActiveDiscountCouponService;

@RestController
@RequestMapping("/active-discount-coupons")
@CrossOrigin
public class ActiveDiscountCouponController {
   private final ActiveDiscountCouponService activeDiscountCouponService;

   @Autowired
   public ActiveDiscountCouponController(ActiveDiscountCouponService activeDiscountCouponService) {
      this.activeDiscountCouponService = activeDiscountCouponService;
   }

   @GetMapping("/{id}")
   public ResponseEntity<DiscountCouponResponseDTO> getActiveDiscountCouponById(@PathVariable("id") Long id) {
      DiscountCouponResponseDTO discountCouponResponseDTO = activeDiscountCouponService.getActiveDiscountCouponById(id);

      return new ResponseEntity<>(discountCouponResponseDTO, HttpStatus.OK);
   }

   @GetMapping
   public ResponseEntity<List<DiscountCouponResponseDTO>> getAllActiveDiscountCoupons() {
      List<DiscountCouponResponseDTO> discountCoupons = activeDiscountCouponService.getAllActiveDiscountCoupons();

      return new ResponseEntity<>(discountCoupons, HttpStatus.OK);
   }

   @GetMapping("/search")
   public ResponseEntity<Page<DiscountCouponResponseDTO>> searchActiveDiscountCoupons(
         @RequestParam(defaultValue = "") String name,
         @RequestParam(defaultValue = "0") Integer pageNumber,
         @RequestParam(defaultValue = "10") Integer pageSize,
         @RequestParam(defaultValue = "id_discount_coupon") String orderBy,
         @RequestParam(defaultValue = "asc") String direction) {
      Page<DiscountCouponResponseDTO> discountCoupons = activeDiscountCouponService
            .searchActiveDiscountCoupons(name, pageNumber, pageSize, orderBy, direction);

      return new ResponseEntity<>(discountCoupons, HttpStatus.OK);
   }
}
