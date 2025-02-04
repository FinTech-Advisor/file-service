
package org.advisor.file.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.advisor.global.entities.BaseMemberEntity;

import java.io.Serializable;

@Data
@Entity
public class FileInfo extends BaseMemberEntity implements Serializable {
    @Id @GeneratedValue
    private Long seq;

    @Column(length=45, nullable = false)
    private String gid;

    @Column(length=45)
    private String location;

    @Column(length=100, nullable = false)
    private String fileName;

    @Column(length=65)
    private String contentType;

    @Column(length = 10)
    private String extension;

    @Transient
    private String FilePath;

    @Transient
    private String FileUrl;

    @Transient
    private String thumbUrl;

    private boolean done;

    private boolean selected;

    private long listOrder;
}
