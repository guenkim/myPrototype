package com.guen.common.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@ToString
@MappedSuperclass
public abstract class BaseDate implements Serializable {

    private static final long serialVersionID = 1L;

    @Column(name = "REGDT", updatable = false)
    protected LocalDateTime regdt;

    @Column(name = "MODDT")
    protected LocalDateTime moddt;

    @PrePersist
    public void prePersist() {
        this.regdt = LocalDateTime.now();
        this.moddt = this.regdt;
    }

    @PreUpdate
    public void preUpdate() {
        this.moddt = LocalDateTime.now();
    }

}

