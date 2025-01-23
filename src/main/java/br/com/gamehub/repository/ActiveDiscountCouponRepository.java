/**
 * ActiveDiscountCouponRepository
 *
 * <p>
 * Repository interface for handling data access for the ActiveDiscountCoupon entity.
 * Provides methods for searching and querying active discount coupons from the database.
 * </p>
 * 
 * @author Pedro Lucas
 * @since 2025-01-23
 */
package br.com.gamehub.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.repository.query.Param;

import br.com.gamehub.model.ActiveDiscountCoupon;

public interface ActiveDiscountCouponRepository extends JpaRepository<ActiveDiscountCoupon, Long> {

   /**
    * Searches for active discount coupons by name.
    * 
    * <p>
    * This method performs a case-insensitive search for active discount coupons
    * whose names
    * match the given partial name. The results are paginated and returned as a
    * {@link Page}.
    * </p>
    *
    * @param name     the partial name of the discount coupon to search for.
    * @param pageable pagination information including page number and page size.
    * @return a {@link Page} containing the matching {@link ActiveDiscountCoupon}
    *         entities.
    */
   @NativeQuery("SELECT ADC.* FROM GH_ACTIVE_DISCOUNT_COUPON ADC LEFT JOIN GH_DISCOUNT_COUPON DC ON ADC.id_discount_coupon = DC.id_discount_coupon WHERE DC.no_name ILIKE %:name%")
   Page<ActiveDiscountCoupon> search(@Param("name") String name, Pageable pageable);
}
