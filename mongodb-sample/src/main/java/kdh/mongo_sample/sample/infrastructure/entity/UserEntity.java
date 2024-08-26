package kdh.mongo_sample.sample.infrastructure.entity;


import kdh.mongo_sample.sample.domain.dto.FriendDto;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Getter
@Builder
@Document(collection = "user")
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserEntity {

	@Id
	private String id;

	private String name;

	private String description;

	private String Gender;

	@CreatedDate
	private Date createdAt;

	@LastModifiedDate
	private Date updatedAt;

	private List<FriendDto> friendList = new ArrayList<>();

	// 연관관계 설정
	@DBRef
	private List<BookEntity> bookEntityList = new ArrayList<>();

	/**
	 * Users
	 * 1. 책 추가
	 * 2. 책 목록 수정
	 * 3. 친구 추가
	 * 4. 친구 목록 수정
	 * 5. 설명 업데이트
	 */

	// 1. 책 추가
	public void addBook(BookEntity bookEntity) {
		this.bookEntityList.add(bookEntity);
	}

	// 2. 책 목록 수정
	public void updateBookList(List<BookEntity> bookEntityList) {
		this.bookEntityList = bookEntityList;
	}

	// 3. 친구 추가
	public void addFriend(FriendDto friend) {
		this.friendList.add(friend);
	}

	// 4. 친구 목록 수정
	public void updateFriendList(List<FriendDto> friendList) {
		this.friendList = friendList;
	}

	// 5. 설명 업데이트
	public void updateDescription(String description) {
		this.description = description;
	}

}
