package kdh.mongo_sample.sample.infrastructure.mongo;


import kdh.mongo_sample.sample.infrastructure.entity.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

// MongoRepository<Entity, Id Type> 을 상속받아 사용
public interface UserMongoRepository extends MongoRepository<UserEntity, String> {

	UserEntity findByName(String userName);

}
