package kdh.mongo_sample.sample.infrastructure.mongo;


import kdh.mongo_sample.sample.infrastructure.entity.BookEntity;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface BookMongoRepository extends MongoRepository<BookEntity, String> {

	BookEntity findByTitle(String bookTitle);

}
