/**
 * ActiveDiscountCouponController
 *
 * <p>
 * Controller for ActiveDiscountCoupon resource, exposing endpoints for
 * searching, retrieving and listing active discount coupons.
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.gamehub.dto.response.DiscountCouponResponseDTO;
import br.com.gamehub.service.ActiveDiscountCouponService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Active Discount Coupons", description = "Endpoints for searching, retrieving and listing active discount coupons.")
@RestController
@RequestMapping("/active-discount-coupons")
@CrossOrigin
public class ActiveDiscountCouponController {
   private final ActiveDiscountCouponService activeDiscountCouponService;

   /**
    * Constructor for ActiveDiscountCouponController.
    *
    * @param activeDiscountCouponService the service for ActiveDiscountCoupon
    *                                    resource.
    */
   @Autowired
   public ActiveDiscountCouponController(ActiveDiscountCouponService activeDiscountCouponService) {
      this.activeDiscountCouponService = activeDiscountCouponService;
   }

   /**
    * Retrieves an active discount coupon by its ID.
    *
    * @param id the ID of the active discount coupon.
    * @return a ResponseEntity containing the active discount coupon.
    */
   @GetMapping("/{id}")
   @Operation(summary = "Get active discount coupon by ID", description = "Endpoint for retrieves an active discount coupon by its ID.")
   @ApiResponse(responseCode = "200", description = "Active discount coupon found.", content = @Content(schema = @Schema(implementation = DiscountCouponResponseDTO.class)))
   @ApiResponse(responseCode = "404", description = "Active discount coupon not found.", content = @Content())
   public ResponseEntity<DiscountCouponResponseDTO> getActiveDiscountCouponById(
         @Parameter(description = "Active discount coupon ID", example = "1") @PathVariable("id") Long id) {
      DiscountCouponResponseDTO discountCouponResponseDTO = activeDiscountCouponService.getActiveDiscountCouponById(id);

      return new ResponseEntity<>(discountCouponResponseDTO, HttpStatus.OK);
   }

   /**
    * Retrieves all active discount coupons.
    *
    * @return a ResponseEntity containing all active discount coupons.
    */
   @GetMapping
   @Operation(summary = "Get all active discount coupons", description = "Endpoint for get all active discount coupons.")
   @ApiResponse(responseCode = "200", description = "Active discount coupons found.", content = @Content(array = @ArraySchema(schema = @Schema(implementation = DiscountCouponResponseDTO.class))))
   public ResponseEntity<List<DiscountCouponResponseDTO>> getAllActiveDiscountCoupons() {
      List<DiscountCouponResponseDTO> discountCoupons = activeDiscountCouponService.getAllActiveDiscountCoupons();

      return new ResponseEntity<>(discountCoupons, HttpStatus.OK);
   }

   /**
    * Searches for active discount coupons by name.
    *
    * @param name       the name of the active discount coupon.
    * @param pageNumber the page number to be retrieved.
    * @param pageSize   the page size to be retrieved.
    * @param orderBy    the field to be ordered by.
    * @param direction  the direction of the ordering.
    * @return a ResponseEntity containing the searched active discount coupons.
    */
   @GetMapping("/search")
   @Operation(summary = "Search active discount coupons by name", description = "Endpoint for searches for active discount coupons by name.")
   @ApiResponse(responseCode = "200", description = "Active discount coupons found.", content = @Content(schema = @Schema(implementation = Page.class)))
   public ResponseEntity<Page<DiscountCouponResponseDTO>> searchActiveDiscountCoupons(
         @Parameter(description = "Active discount coupon name", example = "10% off") @RequestParam(defaultValue = "") String name,
         @Parameter(description = "Active discount coupon page number", example = "0") @RequestParam(defaultValue = "0") Integer pageNumber,
         @Parameter(description = "Active discount coupon page size", example = "10") @RequestParam(defaultValue = "10") Integer pageSize,
         @Parameter(description = "Active discount coupon order by", example = "id_discount_coupon") @RequestParam(defaultValue = "id_discount_coupon") String orderBy,
         @Parameter(description = "Active discount coupon direction", example = "asc") @RequestParam(defaultValue = "asc") String direction) {
      Page<DiscountCouponResponseDTO> discountCoupons = activeDiscountCouponService
            .searchActiveDiscountCoupons(name, pageNumber, pageSize, orderBy, direction);

      return new ResponseEntity<>(discountCoupons, HttpStatus.OK);
   }
}
