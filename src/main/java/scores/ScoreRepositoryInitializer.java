package scores;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScoreRepositoryInitializer {

	private final AtomicLong nextId = new AtomicLong(1L);
	private final ScoreRepository repo;

	@Autowired
	public ScoreRepositoryInitializer(ScoreRepository repo) {
		this.repo = repo;
		init();
	}

	// private helpers
	public void init() {
		List<String> teamPool = new ArrayList<String>(Arrays.asList(ALL_TEAMS));
		List<Game> games = new ArrayList<>();
		while(!teamPool.isEmpty()) {
			String homeTeam = teamPool.get((int)(Math.random() * teamPool.size()));
			teamPool.remove(homeTeam);
			String awayTeam = teamPool.get((int)(Math.random() * teamPool.size()));
			teamPool.remove(awayTeam);
			Game game = new Game(nextId.incrementAndGet(), homeTeam, awayTeam, 0, 0);
			games.add(game);
		}
		repo.save(games);
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
