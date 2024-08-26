package kdh.mongo_sample.sample.infrastructure.entity;


import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;


@Document("book")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BookEntity {

	@Id
	private String id;

	private String title;

	private String author;

	@CreatedDate
	private Date createdAt;

	@LastModifiedDate
	private Date updatedAt;

}
