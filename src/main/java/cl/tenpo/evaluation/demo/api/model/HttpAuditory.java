package cl.tenpo.evaluation.demo.api.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "auditoryId")
@SuperBuilder
@NoArgsConstructor
@DiscriminatorValue("HttpAuditory")
@JsonSerialize(as = HttpAuditory.class)
@Data
public class HttpAuditory extends Auditory{
    @Column(nullable = false)
    private Integer status;
    @Column(nullable = false)
    private String uri;
    private String request;
    private String response;
    private String headers;
}
