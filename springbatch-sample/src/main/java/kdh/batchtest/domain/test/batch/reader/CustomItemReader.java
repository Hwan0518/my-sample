package kdh.batchtest.domain.test.batch.reader;


import org.springframework.batch.item.*;
import org.springframework.stereotype.Component;

import java.util.HashMap;

// ItemStreamReader = ItemStream + ItemReader
@Component
public class CustomItemReader implements ItemStreamReader<String> {

	private final String CURRENT_ID_KEY = "current.call.id";
	private final String API_URL = "https://api/url?id=";
	private HashMap<String, String> urlRepo;
	private int currentId;


	public CustomItemReader() {
		this.urlRepo = new HashMap<>();
		this.currentId = 0;
	}


	// read: 배치 작업시 데이터를 읽기 위한 부분, 데이터(item) 하나를 읽을 때마다 read가 한 번 호출됨
	@Override
	public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		// currentId 증가
		currentId ++;
		// 비즈니스 로직
		String url = API_URL + currentId;
		String response = urlRepo.getOrDefault(url, null);
		if (response == null) {
			return null;
		}
		return response;
	}

	// open: Step에서 처음 reader를 호출했을 때 시작되는 부분, 초기화나 이미 실행했던 작업을 중단점까지 건너 뛰도록 설계하는 부분임
	// ExecutionContext: 배치 작업 처리시 기준점을 잡을 변수를 계속하여 트래킹하기 위한 저장소(작업을 몇 번째 item까지 진행했는지 저장한다 생각하면 됨)
	@Override
	public void open(ExecutionContext executionContext) throws ItemStreamException {
		// CURRENT_ID_KEY가 executionContext에 포함되어 있다면, 이를 currentId(현재 처리할 item)로 설정
		if (executionContext.containsKey(CURRENT_ID_KEY)) {
			currentId = executionContext.getInt(CURRENT_ID_KEY);
		}
	}

	// update: read()와 함께 호출되는 메소드, read() 다음 바로 호출되기 때문에 read에서 처리한 작업 단위를 기록하는 용도로 사용
	@Override
	public void update(ExecutionContext executionContext) throws ItemStreamException {
		ItemStreamReader.super.update(executionContext);
	}

	// close: 배치 작업이 끝나고 호출, 파일을 저장하거나 필드 변수를 초기화하는 메서드로 사용됨
	@Override
	public void close() throws ItemStreamException {
		ItemStreamReader.super.close();
	}

}
