package com.ssurbar.db.repository.answer;

import com.ssurbar.db.entity.answer.FilterData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilterDataRepository extends JpaRepository<FilterData, String> {

}
