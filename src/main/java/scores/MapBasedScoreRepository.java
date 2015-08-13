package scores;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.lang.SerializationUtils;
import org.springframework.stereotype.Repository;

@Repository
public class MapBasedScoreRepository implements ScoreRepository {

	private AtomicLong nextId = new AtomicLong(1L);
	
	private Map<Long, Game> games = new ConcurrentHashMap<Long, Game>();
	
	public MapBasedScoreRepository() {
		initializeGames();
	}
	
	public List<Game> findAll() {
		return deepCloneList(new ArrayList<Game>(games.values()));
	}

	public Game findOne(Long id) {
		return games.get(id);
	}

	public void save(List<Game> games) {
		for (Game game : games) {
			save(game);
		}
	}

	public Game save(Game game) {
		if (game.getId() == null) {
			game.setId(nextId.incrementAndGet());
		}
		
		games.put(game.getId(), game);
		
		return game;
	}

	// private helpers
	
	private void initializeGames() {
		List<String> teamPool = new ArrayList<String>(Arrays.asList(ALL_TEAMS));
		while(!teamPool.isEmpty()) {
			String homeTeam = teamPool.get((int)(Math.random() * teamPool.size()));
			teamPool.remove(homeTeam);
			String awayTeam = teamPool.get((int)(Math.random() * teamPool.size()));
			teamPool.remove(awayTeam);
			save(new Game(homeTeam, awayTeam));
		}
	}
	
	private List<Game> deepCloneList(List<Game> original) {
		List<Game> copy = new ArrayList<Game>(original.size());
		for(Game t : original) {
			copy.add((Game) SerializationUtils.clone((Serializable) t)); 
		}
		return copy;
	}
	
	private static final String[] ALL_TEAMS = {
		"FC Augsburg","Hertha BSC", "Werder Bremen",
		"SV Darmstadt 98", "Borussia Dortmund",
		"Eintracht Frankfurt",
		"Hamburger SV",
		"Hannover 96",
		"TSG 1899 Hoffenheim",
		"FC Ingolstadt 04",
		"1. FC Köln",
		"Bayer 04 Leverkusen",
		"1. FSV Mainz 05",
		"Borussia Mönchengladbach",
		"FC Bayern München",
		"FC Schalke 04",
		"VfB Stuttgart",
		"VfL Wolfsburg",
	};

}
