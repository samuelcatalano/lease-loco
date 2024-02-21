package co.uk.leaseloco.leasingnormaliser.repository;

import co.uk.leaseloco.leasingnormaliser.domain.LeaseOffer;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface LeaseOfferRepository extends JpaRepository<LeaseOffer, Long>, JpaSpecificationExecutor<LeaseOffer> {

  /**
   * Searches for {@link LeaseOffer}s based on the given parameters.
   *
   * @param name     the name of the lease offer
   * @param type     the type of the lease offer
   * @param mileage  the mileage of the lease offer
   * @param term     the term of the lease offer
   * @param minPrice the minimum price of the lease offer
   * @param maxPrice the maximum price of the lease offer
   * @return a list of lease offers that match the given parameters
   */
  default List<LeaseOffer> findByParameters(
      final String name, final String type, final Long mileage, final Integer term,
      final Double minPrice, final Double maxPrice
  ) {
    return findAll((root, query, cb) -> {
      final List<Predicate> predicates = new ArrayList<>();

      predicates.add(StringUtils.hasLength(name) ? cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%") : cb.and());
      predicates.add(StringUtils.hasLength(type) ? cb.like(cb.lower(root.get("type")), "%" + type.toLowerCase() + "%") : cb.and());

      predicates.add(mileage != null ? cb.or(
          cb.between(root.get("mileage"), mileage - 1, mileage + 1),
          cb.isNull(root.get("mileage"))
      ) : cb.and());

      predicates.add(term != null ? cb.equal(root.get("term"), term) : cb.and());

      if (minPrice != null || maxPrice != null) {
        if (minPrice != null && maxPrice != null) {
          predicates.add(cb.between(root.get("price"), minPrice, maxPrice));
        } else if (minPrice != null) {
          predicates.add(cb.greaterThanOrEqualTo(root.get("price"), minPrice));
        } else {
          predicates.add(cb.lessThanOrEqualTo(root.get("price"), maxPrice));
        }
      }
      return cb.and(predicates.toArray(new Predicate[0]));
    });
  }
}
