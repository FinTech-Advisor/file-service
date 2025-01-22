
package org.advisor.file.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import org.advisor.global.entities.BaseMemberEntity;
import org.springframework.util.StringUtils;

import java.io.Serializable;

@Data
@Entity
public class FileInfo extends BaseMemberEntity implements Serializable {
    @Id @GeneratedValue
    private Long seq;

    @Column(length=45, nullable = false)
    private String gid;

    @Column(length=45)
    private String location;  // 그룹 내에서 위치

    @Column(length=100, nullable = false)
    private String fileName;

    @Column(length=30)
    private String extension;

    @Column(length=65)
    private String contentType;

    @Transient
    private String fileUrl;

    @Transient
    private String filePath;

    @Transient
    private String thumbUrl;

    private boolean done;

    private boolean selected;

}
