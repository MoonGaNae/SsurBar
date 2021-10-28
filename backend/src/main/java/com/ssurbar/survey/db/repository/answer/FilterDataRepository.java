package com.ssurbar.survey.db.repository.answer;


import com.ssurbar.survey.db.entity.answer.FilterData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FilterDataRepository extends JpaRepository<FilterData, String> {
    Optional<FilterData> findFilterDataByFilterDataId(String Id);

}

