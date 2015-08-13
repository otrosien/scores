package scores;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class GameDayServiceImpl implements GameDayService {
	
	private Logger logger = LoggerFactory.getLogger(GameDayServiceImpl.class);

	private ScoreRepository scoreRepo;

	@Autowired
	public GameDayServiceImpl(ScoreRepository scoreRepo) {
		this.scoreRepo = scoreRepo;
	}
	
	@Override
	public List<Game> getScores() {
		return scoreRepo.findAll();
	}
	
	@Scheduled(fixedRate=15000)
	public void updateScores() {
		List<Game> scores = getScores(); // fetch data
		randomlyUpdate(scores);          // update data
		scoreRepo.save(scores);          // save data
	}
	
	// private helpers
	
	/*
	 * The code in this method was extracted so that updateScoresRandomly could focus on the basics of
	 * updating some data and saving it. This method is entirely focused on the task of updating the
	 * data and is largely filled with random number generation. Its function is only passively
	 * relevant to the concern of differential synchronization.
	 */
	private void randomlyUpdate(List<Game> scores) {
		// choose a game to update
		int gameIndex = (int)(Math.random() * scores.size());

		// choose either home or away team
		double homeOrAway = Math.random();

		// adjust score
		Game game = scores.get(gameIndex);
		if (homeOrAway > 0.5) {
			logger.info(game.getHomeTeam() + " scored a goal against " + game.getAwayTeam());
			game.incrementHomeTeamScore(1);
		} else {
			logger.info(game.getAwayTeam() + " scored a goal against " + game.getHomeTeam());
			game.incrementAwayTeamScore(1);
		}
	}

}
