package com.rock.model.jpa;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
public abstract class AbstractEntity implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "id", length = 32, nullable = false, unique = true)
    private Long id;

    @Version
    //在JPA中，我们可以使用@Version在某个字段上进行乐观锁控制,当乐观锁更新失败的时候，会抛出异常
    //作用是防止丢失更新
    @Column(name = "version", length = 20, nullable = false)
    private Integer version;

    @CreatedDate
    @Column(name = "create_time",updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    @LastModifiedDate
    @Column(name = "update_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    //更新时候是否使用非null值的局部更新方式(该属性的json忽略输出必须在派生类上设置, 在基类上是无效的)
    @Transient
    private boolean updateSelective = false;

    @PrePersist
    //save之前被调用，帮助您在持久化之前自动填充实体属性
    public void prePersist() {
        createTime = new Date();
        updateTime = createTime;
        version = 0;
    }

    @PreUpdate
    //如果要每次更新实体时更新实体的属性，可以使用@PreUpdate注释
    public void preUpdate() {
        updateTime = new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public boolean isUpdateSelective() {
        return updateSelective;
    }

    public void setUpdateSelective(boolean updateSelective) {
        this.updateSelective = updateSelective;
    }
}
