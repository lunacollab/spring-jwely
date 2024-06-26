package com.example.demo.Repository;
import com.example.demo.Entity.MaterialPriceList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface MaterialPriceListRepository extends JpaRepository<MaterialPriceList, Integer> {
	 List<MaterialPriceList> findByMaterialID(int materialID);
}

