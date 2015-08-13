package scores;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface ScoreRepository extends CrudRepository<Game, Long> {

	List<Game> findAll();

}
