/* 
 * AP(r) Computer Science GridWorld Case Study:
 * Copyright(c) 2005-2006 Cay S. Horstmann (http://horstmann.com)
 *
 * This code is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * @author Cay Horstmann
 */



import info.gridworld.actor.Actor;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;
import info.gridworld.world.World;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 * An <code>ActorWorld</code> is occupied by actors. <br />
 * This class is not tested on the AP CS A and AB exams.
 */

public class ActorWorld extends World<Actor>
{    
    private static final String DEFAULT_MESSAGE = "Click on a grid location to construct or manipulate an actor.";
    
    /**
     * Constructs an actor world with a default grid.
     */
    public ActorWorld()
    {
    }

    /**
     * Constructs an actor world with a given grid.
     * @param grid the grid for this world.
     */
    public ActorWorld(Grid<Actor> grid)
    {
        super(grid);
    }

    public void show()
    {
        if (getMessage() == null)
            setMessage(DEFAULT_MESSAGE);
        super.show();
    }

    public void step()
    {
        Grid<Actor> gr = getGrid();
        ArrayList<Actor> actors = new ArrayList<Actor>();
        for (Location loc : gr.getOccupiedLocations())
            actors.add(gr.get(loc));

        for (Actor a : actors)
        {
            // only act if another actor hasn't removed a
            if (a.getGrid() == gr)
                a.act();
            /*if(a instanceof Laser)
            {
              ((Laser)(a)).act();
            	((Laser)(a)).act();
            }*/
        }
        //SPAWN SYSTEM
        Location randLoc = getRandomGoodLocation();
        
        int random = (int)(Math.random()*100+1);
        if(random <= 70)
        	this.add(randLoc, new Drone(this));
        else if(random > 70 && random <= 80)
        	this.add(randLoc, new CentipedeDrone(this));
        else if(random > 80 && random <= 85)
        	this.add(randLoc, new Invincibility());
        else this.add(randLoc, new Gate());
        //End Spawn System
    }
    
    private Location getRandomGoodLocation()
    {
    	Location randLoc = getRandomEmptyLocation();
    	ArrayList<Actor> safeSpawn = getGrid().getNeighbors(randLoc);
    	for(Actor a : safeSpawn)
    		if(a instanceof PacifistShip)
    			return getRandomGoodLocation();
    	return randLoc;
    }
    
    public void save()
    {
    	final JFileChooser fc = new JFileChooser();
    	//File dir = new File("Q:/workspace/GridWorldCode/GridWorldCode/projects/workspace/");
    	File dir = new File("H:/programming/GridWorldCode/projects");
    	fc.setCurrentDirectory(dir);
    	fc.showSaveDialog(null);
    	File sel = fc.getSelectedFile();
    	if(sel==null) 
    		System.exit(0);
    	String dirString = dir.toString();
    	String className = sel.toString();
		
    	className = className.substring(dirString.length()+1, className.length()-5);
		System.out.println(className);

    	File file = sel;
    	Grid<Actor> gr = getGrid();
    	ArrayList<Location> occLocations = new ArrayList<Location>();
    	occLocations = gr.getOccupiedLocations();
    	String code="";
    	try
		{
        	PrintWriter out = new PrintWriter(new FileOutputStream(file));

        	ArrayList<String> imports = new ArrayList<String>();
        	imports.add("import info.gridworld.actor.ActorWorld;");
        	imports.add("import info.gridworld.actor.Actor;");
        	imports.add("import info.gridworld.grid.Location;");
        	imports.add("import java.awt.Color;");
        	imports.add("import info.gridworld.grid.BoundedGrid;");
	    	for(int i = 0; i<occLocations.size(); i++)
	    	{
	    		Actor act = gr.get(occLocations.get(i));
	    		//if(!imports.contains("import "+act.getClass().getName()+";")&&act.getClass().getName().contains(".")){
	    		//	imports.add("import "+act.getClass().getName()+";");
	    		//}
	    		code += "\t\tActor nActor"+i+" = new "+act.getClass().getName()+"();\n";
	    		if (act.getDirection()!=0)code += "\t\tnActor"+i+".setDirection("+act.getDirection()+");\n";
	    		if(act.getColor()!=null)
	    			code += "\t\tnActor"+i+".setColor(new Color("+act.getColor().getRed()+","+act.getColor().getGreen()+","+act.getColor().getBlue()+"));\n";
	    		else code+= "\t\tnActor"+i+".setColor(null);\n";
	    		code += "\t\tworld.add(new Location"+act.getLocation()+", nActor"+i+");\n";

	    	}
	    	for (String s:imports){
	    		out.println(s);
	    	}
	    	out.println();
	    	out.println("/**");
	    	out.println(" * Generated  by SaveWorld");
	    	out.println(" * By Eric Rosenberg and Steven Miller.");
	    	out.println(" */");
        	out.println("public class "+className+"{");
        	out.println("\tpublic static void main(String[] args){");
        	out.println("\t\tActorWorld world = new ActorWorld(new BoundedGrid<Actor>("+getGrid().getNumRows()+","+getGrid().getNumCols()+"));");
			out.println(code);
	    	out.println("\t\tworld.show();\n\t}\n}");
	    	out.close();
		}catch(Exception e)
		{
			JOptionPane.showMessageDialog(null,"error writing to file"+e);
		}
		JOptionPane.showMessageDialog(null,"Done!\nFile saved as "+className+".java");
    }

    /**
     * Adds an actor to this world at a given location.
     * @param loc the location at which to add the actor
     * @param occupant the actor to add
     */
    public void add(Location loc, Actor occupant)
    {
        occupant.putSelfInGrid(getGrid(), loc);
    }

    /**
     * Adds an occupant at a random empty location.
     * @param occupant the occupant to add
     */
    public void add(Actor occupant)
    {
        Location loc = getRandomEmptyLocation();
        if (loc != null)
            add(loc, occupant);
    }

    /**
     * Removes an actor from this world.
     * @param loc the location from which to remove an actor
     * @return the removed actor, or null if there was no actor at the given
     * location.
     */
    public Actor remove(Location loc)
    {
        Actor occupant = getGrid().get(loc);
        if (occupant == null)
            return null;
        occupant.removeSelfFromGrid();
        return occupant;
    }
}
