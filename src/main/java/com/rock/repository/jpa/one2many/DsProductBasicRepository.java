package com.rock.repository.jpa.one2many;

import com.rock.model.jpa.DsProductBasic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DsProductBasicRepository extends JpaRepository<DsProductBasic, Long>, JpaSpecificationExecutor<DsProductBasic> {


}