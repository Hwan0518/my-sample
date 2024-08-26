package kdh.mongo_sample.sample.application;


import kdh.mongo_sample.sample.api.request.CreateBookRequestDto;
import kdh.mongo_sample.sample.api.request.CreateUserRequestDto;
import kdh.mongo_sample.sample.api.request.UpdateDescriptionRequestDto;
import kdh.mongo_sample.sample.domain.dto.BookDto;
import kdh.mongo_sample.sample.domain.dto.FriendDto;
import kdh.mongo_sample.sample.domain.dto.UserDto;
import kdh.mongo_sample.sample.domain.dto.UserListDto;
import kdh.mongo_sample.sample.infrastructure.entity.BookEntity;
import kdh.mongo_sample.sample.infrastructure.entity.UserEntity;
import kdh.mongo_sample.sample.infrastructure.mongo.BookMongoRepository;
import kdh.mongo_sample.sample.infrastructure.mongo.UserMongoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class MongoServiceImpl {

	private final UserMongoRepository userMongoRepository;
	private final BookMongoRepository bookMongoRepository;
	private final ModelMapper modelMapper;

	/**
	 * MongoService
	 * 1. 유저 저장
	 * 2. 책 저장
	 * 3. 유저 조회
	 * 4. 책 조회
	 * 5. 유저에 책 추가
	 * 6. 유저에 친구 추가
	 * 7. 유저 설명 변경
	 * 8. 친구 삭제
	 */

	// 1. 유저 저장
	@Transactional
	public UserDto creatUser(CreateUserRequestDto usersDto) {
		UserEntity userEntity = modelMapper.map(usersDto, UserEntity.class);
		userMongoRepository.save(userEntity);
		log.info("users : {}", userEntity);
		return modelMapper.map(userEntity, UserDto.class);
	}


	// 2. 책 저장
	@Transactional
	public BookDto createBook(CreateBookRequestDto bookDto) {
		BookEntity bookEntity = modelMapper.map(bookDto, BookEntity.class);
		bookMongoRepository.save(bookEntity);
		log.info("book : {}", bookEntity);
		return modelMapper.map(bookEntity, BookDto.class);
	}


	// 3. 모든 유저 조회
	@Transactional(readOnly = true)
	public UserListDto getAllUser() {
		List<UserEntity> allUserEntity = userMongoRepository.findAll();
		List<UserDto> userDtoList = allUserEntity.stream()
			.map(userEntity -> modelMapper.map(userEntity, UserDto.class))
			.toList();
		return new UserListDto(userDtoList);
	}


	// 5. 유저에 책 추가
	@Transactional
	public UserDto addBook(String userName, String bookName) {
		UserEntity userEntity = userMongoRepository.findByName(userName);
		BookEntity bookEntity = bookMongoRepository.findByTitle(bookName);
		userEntity.addBook(bookEntity);
		userMongoRepository.save(userEntity);
		return modelMapper.map(userEntity, UserDto.class);
	}


	// 6. 유저에 친구 추가
	@Transactional
	public UserDto addFriend(String userName, String friendName) {
		UserEntity userEntity = userMongoRepository.findByName(userName);
		UserEntity friendEntity = userMongoRepository.findByName(friendName);
		log.info("userEntity : {}", userEntity);
		log.info("friendEntity : {}", friendEntity);
		FriendDto userDto = modelMapper.map(userEntity, FriendDto.class);
		FriendDto friendDto = modelMapper.map(friendEntity, FriendDto.class);
		userEntity.addFriend(friendDto);
		friendEntity.addFriend(userDto);
		userMongoRepository.save(userEntity);
		userMongoRepository.save(friendEntity);
		return modelMapper.map(userEntity, UserDto.class);
	}


	// 7. 유저 설명 변경
	public UserDto updateDescription(UpdateDescriptionRequestDto requestDto) {
		UserEntity userEntity = userMongoRepository.findByName(requestDto.getUserName());
		userEntity.updateDescription(requestDto.getDescription());
		userMongoRepository.save(userEntity);
		return modelMapper.map(userEntity, UserDto.class);
	}


	// 8. 친구 삭제
	public UserDto deleteFriend(String userName, String friendName) {
		UserEntity userEntity = userMongoRepository.findByName(userName);
		UserEntity friendEntity = userMongoRepository.findByName(friendName);
		userEntity.getFriendList().removeIf(friendDto -> {
			if (friendDto != null) { return friendDto.getName().equals(friendName);}
			return false;
		});
		friendEntity.getFriendList().removeIf(friendDto -> {
			if (friendDto != null) { return friendDto.getName().equals(userName);}
			return false;
		});
		userMongoRepository.save(userEntity);
		userMongoRepository.save(friendEntity);
		return modelMapper.map(userEntity, UserDto.class);
	}

}
