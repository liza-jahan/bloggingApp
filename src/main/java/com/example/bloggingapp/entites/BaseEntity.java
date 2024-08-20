package com.example.bloggingapp.entites;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
//    @Column(columnDefinition = "VARCHAR(36)")
   // @JdbcTypeCode(SqlTypes.VARCHAR)     //convert integer to varchar
    private Integer id;
    private Date createdTime;
    private Date lastUpdateTime;
    private Date lastUpdatedBy;
}