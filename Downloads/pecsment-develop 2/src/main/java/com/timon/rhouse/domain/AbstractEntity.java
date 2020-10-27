package com.timon.rhouse.domain;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

@MappedSuperclass
public class AbstractEntity<ID extends Serializable> implements Serializable {


    public static final String FLD_ID="id";

    @Id
    @Column(name = FLD_ID, unique = true, updatable = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private ID id;

    @Version
    private Integer version;


    @CreationTimestamp
    private Date creationDate;


    @UpdateTimestamp
    private LocalDateTime updated;



    public AbstractEntity() {

    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public AbstractEntity(ID id){
        this.id=id;
    }


    public ID getId() {
        return id;
    }


    public void setId(ID id) {
        this.id = id;
    }


    public Integer getVersion() {
        return version;
    }


    public void setVersion(Integer version) {
        this.version = version;
    }



    public LocalDateTime getUpdated() {
        return updated;
    }


    public void setUpdated(LocalDateTime updated) {
        this.updated = updated;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractEntity)) return false;
        AbstractEntity<?> that = (AbstractEntity<?>) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(version, that.version) &&

                Objects.equals(updated, that.updated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, version, updated);
    }
}
