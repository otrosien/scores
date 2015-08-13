package scores;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.keyvalue.annotation.KeySpace;

@KeySpace("game")
public class Game implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private final Long id;
	private final String homeTeam;
	private final String awayTeam;
	private final int homeTeamScore;
	private final int awayTeamScore;

	public Game(long id, String homeTeam, String awayTeam, int homeTeamScore, int awayTeamScore) {
		this.id = id;
		this.homeTeam = homeTeam;
		this.awayTeam = awayTeam;
		this.homeTeamScore = homeTeamScore;
		this.awayTeamScore = awayTeamScore;
	}

	public Long getId() {
		return id;
	}

	public String getHomeTeam() {
		return homeTeam;
	}

	public int getHomeTeamScore() {
		return homeTeamScore;
	}

	public Game incrementHomeTeamScore(int points) {
		return new Game(this.id, this.homeTeam, this.awayTeam, homeTeamScore + points, awayTeamScore);
	}
	
	public String getAwayTeam() {
		return awayTeam;
	}
	
	public int getAwayTeamScore() {
		return awayTeamScore;
	}

	public Game incrementAwayTeamScore(int points) {
		return new Game(this.id, this.homeTeam, this.awayTeam, awayTeamScore + points, homeTeamScore);
	}

}
