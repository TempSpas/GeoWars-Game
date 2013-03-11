import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import info.gridworld.actor.Actor;
//import info.gridworld.actor.Bug;
import info.gridworld.grid.BoundedGrid;
//import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;
//import java.awt.Color;
import javax.swing.JOptionPane;
//import KeyboardReference.KeyBug;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class GameRunner
{
  public static void testMethod()
	{
		//ActorWorld world = new ActorWorld(new BoundedGrid<Actor>(10,10));
		KeyWorld world = new KeyWorld(new BoundedGrid<Actor>(15,20));
    	
    	PlayerShip player = new PlayerShip(world, 1, 3);
    	
    	Drone testDrone = new CowardDrone(world);
    	CentipedeDrone test2 = new CentipedeDrone(world);
    	world.add(new Location(9,0), new SpaceDebris());
    	world.add(new Location(4,1), test2);
    	world.add(new Location(1,1), testDrone);
        world.add(new Location (3, 5), new Invincibility());
        world.add(new Location(7, 8), player);
        world.show();
	}
	
	public static void pacifistRunner()
	{
		//http://images.hollywood.com/site/goslingposter.jpg
		KeyWorld world = new KeyWorld(new BoundedGrid<Actor>(10,15));
		world.add(new Location(5, 5), new PacifistShip(world, 1, 3));
		world.show();
	}
	
	public static int difficulty()
	{
		String[] options1 = {"Easy", "Medium", "Hard", "Suicidal"};
		int choice = JOptionPane.showOptionDialog(null,
				"Choose a difficult:","Difficulty",0,3,null,options1,null);
		return choice;
	}
	
	//***********LEADERBOARD
	public static void fill(ArrayList<LeaderboardEntry> leaderboard)
	{
		try{
			Scanner inFile = new Scanner(new File("leaderboard.txt"));
			
			String line;
			String[] data;
			
			
			while (inFile.hasNext())
			{
				LeaderboardEntry one = new LeaderboardEntry();
				
				line = inFile.nextLine();
				data = line.split(" ");
				one.setInitials(data[0]);
				one.setScore(Integer.parseInt(data[1]));
				
				leaderboard.add(one);
			}
			inFile.close();
		}catch (Exception e){
			System.out.println("error reading file: "+e);
		}
	}
	
	public static void print(ArrayList<LeaderboardEntry> leaderboard)
	{
		Collections.sort(leaderboard);
		JTextArea area = new JTextArea();
		area.append("\tInitials:\t\t\t\t\t\t\tScore:\n\n");
		for(int i=0; i<leaderboard.size(); i++)
		{
			area.append((i+1)+"\t"+leaderboard.get(i)+"\n");
		}
		area.setBackground(new Color(250,250,210));
		area.setForeground(new Color(0,0,0));
		area.setFont(new Font("Lucida Console", Font.PLAIN, 13));
		area.setRows(35);
		area.setColumns(100);
		JScrollPane pane = new JScrollPane(area);
		JOptionPane.showMessageDialog(null,pane);	

	}
	//***********END
	
	public static int menu()
	{
		String[] options1 = {"Retro Mode", "Pacfisim", "Instructions", "Leaderboard", "Exit"};
		int choice = JOptionPane.showOptionDialog(null,
				"Welcome to the game. Choose a mode:","Main Menu",0,3,null,options1,null);
		return choice;
	}
	
	public static void main(String[] args)
	{	
		//changeJOP();
		
		int choice = menu();
		//do
		//{
			/*choice = JOptionPane.showOptionDialog(null,
					"Welcome to the game. Choose a mode:","Main Menu",0,3,null,options1,null);*/
		switch(choice)
		{
		case 0: testMethod(); break;//code
		case 1: pacifistRunner(); break;
		case 2: JOptionPane.showMessageDialog(null, "Controls: Arrow keys control movement, " +
				"WASD control shooting. Bomb with space." + 
				"\n\nRetro Mode: Classic space shooter. Pilot your ship" +
				" and shoot multiple types of drones to obtain the high score.\n" +
				"\nPacifism: " +
				"Encounter only normal drones. You cannot fire bullets; your score " +
		"relies entirely on gates and bombs."); break;
		case 3:
		{
			ArrayList<LeaderboardEntry> lb = new ArrayList<LeaderboardEntry>();
			fill(lb);
			print(lb);
		} break;
		case 4: System.exit(0);
		}
		//}while(choice != 0 || choice != 1);
	}
}
