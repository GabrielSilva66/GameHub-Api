package br.com.gamehub.repository;

import br.com.gamehub.model.LowestPrice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LowestPriceRepository extends JpaRepository<LowestPrice, Long> {
}
