package com.timon.rhouse.repository;

import com.timon.rhouse.domain.File;
import com.timon.rhouse.domain.Flat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository  extends JpaRepository<File, Long> {

}
