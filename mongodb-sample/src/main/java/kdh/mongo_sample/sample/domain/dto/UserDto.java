package kdh.mongo_sample.sample.domain.dto;


import kdh.mongo_sample.sample.infrastructure.entity.BookEntity;
import lombok.Getter;

import java.util.List;

@Getter
public class UserDto {

	private String id;

	private String name;

	private String description;

	private List<FriendDto> friendList;

	private List<BookEntity> bookEntityList;

}
