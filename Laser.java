import info.gridworld.actor.Actor;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

public class Laser extends Actor
{
  	private int direction;
	
	public Laser()
	{
		
	}
	
	public Laser(int d)
	{
		direction = d;
		setDirection(direction);
	}
	
	public void move()
    {
        Grid<Actor> gr = getGrid();
        if (gr == null)
            return;
        Location loc = getLocation();
        Location next = loc.getAdjacentLocation(getDirection());
        if (gr.isValid(next) && (gr.get(next) instanceof Drone))
        {
            ((Drone)(gr.get(next))).die();
        	moveTo(next);
            removeSelfFromGrid();
        }
        else if (gr.isValid(next))
            moveTo(next);
        else
            removeSelfFromGrid();
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
        return (neighbor == null) || (neighbor instanceof Drone);
    }
	
	public void act()
    {
        //boolean doAgain = false;
		if (canMove())
            move();
        else removeSelfFromGrid();
        //doAgain = !doAgain;
		//if (doAgain)
        	//act();
    }
}
