import java.awt.Color;
import info.gridworld.actor.Actor;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

public class CentipedeDrone extends Drone
{
 	public CentipedeDrone(ActorWorld w)
	{
		super(w, 100);
		setColor(Color.RED);
	}

	public void act()
	{
		//super.act();
		if(canMove())
			move();
		else
			turn();
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
