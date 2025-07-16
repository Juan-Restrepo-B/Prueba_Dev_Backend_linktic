package com.inventario.repository;

import com.inventario.entity.MovimientosStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovimientosStockRepository extends JpaRepository<MovimientosStock, Long> {}