import java.awt.Color;
import info.gridworld.actor.Actor;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

public class CentipedeDrone extends Drone
{
 	public CentipedeDrone(ActorWorld w)
	{
		super(w, 150);
		setColor(Color.RED);
	}

	public void act()
	{
		//super.act();
		int count = 0;
		if(canMove())
			move();
		/*else
			turn();
		else if(count = 0){
			turn()
			count++;
		}*/
		else switch(count)
		{
			case 0: turn(); count++; break;
			case 1: turn(); count++; break;
			case 2: move(); count++; break;
			case 3: turn(); count++; break;
			case 4: turn(); count = 0; break;
		}
	}

	public void turn()
	{
		setDirection(getDirection() + Location.HALF_RIGHT);
	}

	public void move()
	{
		Grid<Actor> gr = getGrid();
		if (gr == null)
			return;
		Location loc = getLocation();
		Location next = loc.getAdjacentLocation(getDirection());
		if (gr.isValid(next))
			moveTo(next);
		else
			removeSelfFromGrid();
		CentipedeTail tail = new CentipedeTail(world);
		tail.putSelfInGrid(gr, loc);
	}

	public boolean canMove()
	{
		Grid<Actor> gr = getGrid();
		if (gr == null)
			return false;
		Location loc = getLocation();
		Location next = loc.getAdjacentLocation(getDirection());
		if (!gr.isValid(next))
			return false;
		Actor neighbor = gr.get(next);
		return (neighbor == null)/* || (neighbor instanceof Flower)*/;
	}
}
