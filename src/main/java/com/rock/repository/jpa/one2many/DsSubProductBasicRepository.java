package com.rock.repository.jpa.one2many;

import com.rock.model.jpa.DsClasses;
import com.rock.model.jpa.DsSubProductBasic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface DsSubProductBasicRepository extends JpaRepository<DsSubProductBasic, Long>, JpaSpecificationExecutor<DsSubProductBasic> {


}