public class PacifistWorld extends KeyWorld
{
  	private void enemySpawn(Location loc)
  	{
		int chance = (int)(Math.random()*100+1)
		if(chance <= 99)
		{
			int randEnemy = (int)(Math.random()*100+1)
			if(randEnemy <= 80)
				this.add(loc, new Drone(this));
			else this.add(loc, new Gate());
		}
	}
}
