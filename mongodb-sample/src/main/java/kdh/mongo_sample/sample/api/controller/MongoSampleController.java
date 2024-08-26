package kdh.mongo_sample.sample.api.controller;


import io.swagger.v3.oas.annotations.Operation;
import kdh.mongo_sample.sample.api.request.CreateBookRequestDto;
import kdh.mongo_sample.sample.api.request.UpdateDescriptionRequestDto;
import kdh.mongo_sample.sample.domain.dto.BookDto;
import kdh.mongo_sample.sample.domain.dto.UserDto;
import kdh.mongo_sample.sample.domain.dto.UserListDto;
import lombok.RequiredArgsConstructor;
import kdh.mongo_sample.sample.api.request.CreateUserRequestDto;
import kdh.mongo_sample.sample.application.MongoServiceImpl;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/mongo")
@RequiredArgsConstructor
public class MongoSampleController {

	private final MongoServiceImpl mongoService;

	/**
	 * MongoController
	 * 1. 유저 저장
	 * 2. 책 저장
	 * 3. 전체 유저 리스트 조회
	 * 4. 책 조회
	 * 5. 유저에 책 추가
	 * 6. 유저에 친구 추가
	 * 7. 유저 설명 변경
	 * 8. 친구 삭제
	 */

	// 1. 유저 저장
	@Operation(summary = "유저 저장", description = "유저 저장", tags = { "User" })
	@PostMapping("/user")
	public UserDto saveUser(@RequestBody CreateUserRequestDto requestDto) {
		return mongoService.creatUser(requestDto);
	}

	// 2. 책 저장
	@Operation(summary = "책 저장", description = "책 저장", tags = { "Book" })
	@PostMapping("/book")
	public BookDto saveBook(@RequestBody CreateBookRequestDto requestDto) {
		return mongoService.createBook(requestDto);
	}

	// 3. 전체 유저 리스트 조회
	@Operation(summary = "전체 유저 조회", description = "전체 유저 조회", tags = { "User" })
	@GetMapping("/user")
	public UserListDto getAllUser() {
		return mongoService.getAllUser();
	}

	// 4. 책 조회


	// 5. 유저에 책 추가
	@Operation(summary = "유저에 책 추가", description = "유저에 책 추가", tags = { "User" })
	@PostMapping("/user/{userName}")
	public UserDto addBook(@PathVariable("userName") String userName, @RequestParam("bookName") String bookName) {
		return mongoService.addBook(userName, bookName);
	}


	// 6. 유저에 친구 추가
	@Operation(summary = "유저에 친구 추가", description = "유저에 친구 추가", tags = { "User" })
	@PostMapping("/{userName}/friend")
	public UserDto addFriend(@PathVariable("userName") String userName, @RequestParam("friendName") String friendName) {
		return mongoService.addFriend(userName, friendName);
	}


	// 7. 유저 설명 변경
	@Operation(summary = "유저 설명 변경", description = "유저 설명 변경", tags = { "User" })
	@PutMapping("/user")
	public UserDto updateDescription(@RequestBody UpdateDescriptionRequestDto requestDto) {
		return mongoService.updateDescription(requestDto);
	}


	// 8. 친구 삭제
	@Operation(summary = "친구 삭제", description = "친구 삭제", tags = { "User" })
	@DeleteMapping("/{userName}")
	public UserDto deleteFriend(@PathVariable("userName") String userName, @RequestParam("friendName") String friendName) {
		return mongoService.deleteFriend(userName, friendName);
	}
}
