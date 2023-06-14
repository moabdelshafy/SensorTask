package com.sensor.entity;

import java.time.LocalDateTime;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.context.SecurityContextHolder;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners({ AuditingEntityListener.class })
@Embeddable
public class Audit {

	@CreatedBy
	private String createdBy;

	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
	@CreatedDate
	private LocalDateTime createdDate;

	@LastModifiedBy
	private String lastModifiedBy;

	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
	@LastModifiedDate
	private LocalDateTime lastModifiedDate;

	@PrePersist
	protected void onCreate() {
		this.setCreatedBy(getUserName());
		this.setCreatedDate(LocalDateTime.now());
		this.setLastModifiedBy(getUserName());
		this.setLastModifiedDate(LocalDateTime.now());
	}

	@PreUpdate
	protected void onUpdate() {
		if (this.getCreatedBy() == null) {
			this.setCreatedBy(getUserName());
		}

		if (this.getCreatedDate() == null) {
			this.setCreatedDate(LocalDateTime.now());
		}
	}

	private String getUserName() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return principal.toString();
	}

}
