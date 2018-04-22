package com.rock.repository.base;

import com.rock.model.base.ActionLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * 处理用户操作行为记录
 */
@Repository
public interface ActionLogRepository extends JpaRepository<ActionLog, Integer>, JpaSpecificationExecutor<ActionLog> {


}
