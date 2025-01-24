/**
 * DiscountCouponRepository
 *
 * <p>
 * Repository interface for handling data access for the DiscountCoupon entity.
 * Provides methods for searching, retrieving active discount coupons, and querying
 * discount coupons from the database.
 * </p>
 * 
 * @author Pedro Lucas
 * @since 2025-01-23
 */
package br.com.gamehub.repository;

import br.com.gamehub.model.DiscountCoupon;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;

public interface DiscountCouponRepository extends JpaRepository<DiscountCoupon, Long> {
   
   /**
    * Searches for discount coupons by name.
    * 
    * <p>
    * This method performs a case-insensitive search for discount coupons whose names
    * match the given partial name. The results are paginated and returned as a {@link Page}.
    * </p>
    *
    * @param name the partial name of the discount coupon to search for.
    * @param pageable pagination information including page number and page size.
    * @return a {@link Page} containing the matching {@link DiscountCoupon} entities.
    */
   @NativeQuery("SELECT * FROM GH_DISCOUNT_COUPON WHERE no_name ILIKE %?1%")
   Page<DiscountCoupon> search(String name, Pageable pageable);

   /**
    * Retrieves all active discount coupons.
    * 
    * <p>
    * This method retrieves all discount coupons that are currently active. The coupons are
    * considered active if their initial date is less than or equal to the current time, and
    * their deadline is greater than the current time.
    * </p>
    *
    * @return a list of active {@link DiscountCoupon} entities.
    */
   @NativeQuery("SELECT * FROM GH_DISCOUNT_COUPON WHERE dt_initial <= now() AND dt_deadline > now()")
   List<DiscountCoupon> findActiveCoupons();
}
