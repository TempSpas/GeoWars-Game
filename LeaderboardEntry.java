public class LeaderboardEntry implements Comparable<LeaderboardEntry>
{
  	private String initials;
	private int score;
	
	public LeaderboardEntry()
	{
		initials = "";
		score = 0;
	}
	
	public LeaderboardEntry(String i, int s)
	{
		initials = i;
		score = s;
	}
	
	public int compareTo(LeaderboardEntry l)
	{
		if(this.getScore() < l.getScore())
			return 1;
		else if(this.getScore() > l.getScore())
			return -1;
		return 0;
	}
	
	public String getInitials()
	{
		return initials;
	}
	
	public void setInitials(String s)
	{
		initials = s;
	}
	
	public int getScore()
	{
		return score;
	}
	
	public void setScore(int s)
	{
		score = s;
	}
	
	public String toString()
	{
		return initials+"\t\t\t\t\t\t\t\t"+score;
	}
}
