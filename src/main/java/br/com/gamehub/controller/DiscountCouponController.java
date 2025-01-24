/**
 * DiscountCouponController
 * 
 * <p>
 * Controller for DiscountCoupon resource, exposing endpoints for
 * searching, retrieving and listing discount coupons.
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

import br.com.gamehub.dto.request.DiscountCouponRequestDTO;
import br.com.gamehub.dto.response.DiscountCouponResponseDTO;
import br.com.gamehub.service.DiscountCouponService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;

@Tag(name = "DiscountCoupon", description = "Endpoints for DiscountCoupon resource")
@RestController
@RequestMapping("/discount-coupons")
@CrossOrigin
public class DiscountCouponController {
   private final DiscountCouponService discountCouponService;

   /**
    * DiscountCouponController constructor
    * 
    * @param discountCouponService service for discount coupons
    */
   @Autowired
   public DiscountCouponController(DiscountCouponService discountCouponService) {
      this.discountCouponService = discountCouponService;
   }

   /**
    * Creates a new discount coupon.
    * 
    * @param discountCouponRequestDTO the request DTO containing the discount
    *                                 coupon
    *                                 information.
    * @return a ResponseEntity containing the created discount coupon.
    */
   @PostMapping
   @Operation(summary = "Create a new discount coupon", description = "Endpoint for creating a new discount coupon")
   @ApiResponse(responseCode = "201", description = "Discount coupon created.", content = @Content(schema = @Schema(implementation = DiscountCouponResponseDTO.class)))
   public ResponseEntity<DiscountCouponResponseDTO> createDiscountCoupon(
         @Valid @RequestBody DiscountCouponRequestDTO discountCouponRequestDTO) {
      DiscountCouponResponseDTO discountCouponResponseDTO = discountCouponService
            .createDiscountCoupon(discountCouponRequestDTO);

      return new ResponseEntity<>(discountCouponResponseDTO, HttpStatus.CREATED);
   }

   /**
    * Retrieves a discount coupon by its ID.
    * 
    * @param id the ID of the discount coupon.
    * @return a ResponseEntity containing the discount coupon.
    */
   @GetMapping("/{id}")
   @Operation(summary = "Get discount coupon by ID", description = "Endpoint for retrieving a discount coupon by its ID")
   @ApiResponse(responseCode = "200", description = "Discount coupon found.", content = @Content(schema = @Schema(implementation = DiscountCouponResponseDTO.class)))
   @ApiResponse(responseCode = "404", description = "Discount coupon not found.", content = @Content())
   public ResponseEntity<DiscountCouponResponseDTO> getDiscountCouponById(
         @Parameter(description = "Discount coupon ID", example = "1") @PathVariable("id") Long id) {
      DiscountCouponResponseDTO discountCouponResponseDTO = discountCouponService.getDiscountCouponById(id);

      return new ResponseEntity<>(discountCouponResponseDTO, HttpStatus.OK);
   }

   /**
    * Retrieves all discount coupons.
    * 
    * @return a ResponseEntity containing all discount coupons.
    */
   @GetMapping
   @Operation(summary = "Get all discount coupons", description = "Endpoint for retrieving all discount coupons")
   @ApiResponse(responseCode = "200", description = "Discount coupons found.", content = @Content(array = @ArraySchema(schema = @Schema(implementation = DiscountCouponResponseDTO.class))))
   public ResponseEntity<List<DiscountCouponResponseDTO>> getAllDiscountCoupons() {
      List<DiscountCouponResponseDTO> discountCoupons = discountCouponService.getAllDiscountCoupons();

      return new ResponseEntity<>(discountCoupons, HttpStatus.OK);
   }

   /**
    * Updates a discount coupon by its ID.
    * 
    * @param id the ID of the discount coupon.
    * @return a ResponseEntity containing the updated discount coupon.
    */
   @PutMapping("/{id}")
   @Operation(summary = "Update discount coupon by ID", description = "Endpoint for updating a discount coupon by its ID")
   @ApiResponse(responseCode = "200", description = "Discount coupon updated.", content = @Content(schema = @Schema(implementation = DiscountCouponResponseDTO.class)))
   @ApiResponse(responseCode = "404", description = "Discount coupon not found.", content = @Content())
   public ResponseEntity<DiscountCouponResponseDTO> updateDiscountCoupon(
         @Parameter(description = "Discount coupon ID", example = "1") @PathVariable("id") Long id,
         @Valid @RequestBody DiscountCouponRequestDTO discountCouponRequestDTO) {
      DiscountCouponResponseDTO discountCouponResponseDTO = discountCouponService
            .updateDiscountCoupon(id, discountCouponRequestDTO);

      return new ResponseEntity<>(discountCouponResponseDTO, HttpStatus.OK);
   }

   /**
    * Deletes a discount coupon by its ID.
    * 
    * @param id the ID of the discount coupon.
    * @return a ResponseEntity containing a NO_CONTENT status.
    */
   @DeleteMapping("/{id}")
   @Operation(summary = "Delete discount coupon by ID", description = "Endpoint for deleting a discount coupon by its ID")
   @ApiResponse(responseCode = "204", description = "Discount coupon deleted.", content = @Content())
   @ApiResponse(responseCode = "404", description = "Discount coupon not found.", content = @Content())
   public ResponseEntity<Void> deleteDiscountCoupon(
         @Parameter(description = "Discount coupon ID", example = "1") @PathVariable("id") Long id) {
      discountCouponService.deleteDiscountCoupon(id);

      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
   }

   /**
    * Searches for discount coupons by name.
    * 
    * @param name       the name of the discount coupon.
    * @param pageNumber the page number to be retrieved.
    * @param pageSize   the page size to be retrieved.
    * @param orderBy    the field to be ordered by.
    * @param direction  the direction of the ordering.
    * @return a ResponseEntity containing the searched discount coupons.
    */
   @GetMapping("/search")
   @Operation(summary = "Search discount coupons by name", description = "Endpoint for searches for discount coupons by name.")
   @ApiResponse(responseCode = "200", description = "Discount coupons found.", content = @Content(schema = @Schema(implementation = Page.class)))
   public ResponseEntity<Page<DiscountCouponResponseDTO>> searchDiscountCoupons(
         @Parameter(description = "Discount coupon name", example = "10% off") @RequestParam(defaultValue = "") String name,
         @Parameter(description = "Discount coupon page number", example = "0") @RequestParam(defaultValue = "0") Integer pageNumber,
         @Parameter(description = "Discount coupon page size", example = "10") @RequestParam(defaultValue = "10") Integer pageSize,
         @Parameter(description = "Discount coupon order by", example = "id_discount_coupon") @RequestParam(defaultValue = "id_discount_coupon") String orderBy,
         @Parameter(description = "Discount coupon direction", example = "asc") @RequestParam(defaultValue = "asc") String direction) {
      Page<DiscountCouponResponseDTO> discountCoupons = discountCouponService
            .searchDiscountCoupons(name, pageNumber, pageSize, orderBy, direction);

      return new ResponseEntity<>(discountCoupons, HttpStatus.OK);
   }
}
