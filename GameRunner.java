import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import info.gridworld.actor.Actor;
import info.gridworld.grid.BoundedGrid;
import info.gridworld.grid.Location;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

public class GameRunner {
	
	/*public static void testMethod() {
		// ActorWorld world = new ActorWorld(new BoundedGrid<Actor>(10,10));
		KeyWorld world = new KeyWorld(new BoundedGrid<Actor>(15, 20));

		PlayerShip player = new PlayerShip(world, 1, 3);

		Drone testDrone = new CowardDrone(world);
		CentipedeDrone test2 = new CentipedeDrone(world);
		world.add(new Location(9, 0), new SpaceDebris());
		world.add(new Location(4, 1), test2);
		world.add(new Location(1, 1), testDrone);
		world.add(new Location(3, 5), new Invincibility());
		world.add(new Location(7, 8), player);
		world.show();
	}*/

	public static void pacifistRunner() {
		PacifistWorld world = new PacifistWorld(new BoundedGrid<Actor>(10, 15));
		world.add(new Location(5, 5), new PacifistShip(world, 1, 3));
		world.show();
	}

	public static void easy() {
		KeyWorld world = new KeyWorld(new BoundedGrid<Actor>(15, 20));
		PlayerShip player = new PlayerShip(world, 5, 3);
		world.add(new Location(7, 8), player);
		world.show();
	}

	public static void medium() {
		MediumKeyWorld world = new MediumKeyWorld(new BoundedGrid<Actor>(15, 20));
		
		PlayerShip player = new PlayerShip(world, 3, 2);
		world.add(new Location(7, 8), player);
		world.show();
	}

	public static void hard() {
		DifficultKeyWorld world = new DifficultKeyWorld(new BoundedGrid<Actor>(15, 20));
		world.add(new Location(7, 12), new SpaceDebris());
		world.add(new Location(7, 13), new SpaceDebris());
		world.add(new Location(8, 12), new SpaceDebris());
		world.add(new Location(8, 13), new SpaceDebris());
		world.add(new Location(7, 4), new SpaceDebris());
		world.add(new Location(7, 3), new SpaceDebris());
		world.add(new Location(8, 4), new SpaceDebris());
		world.add(new Location(8, 3), new SpaceDebris());
		
		
		PlayerShip player = new PlayerShip(world, 1, 1);
		world.add(new Location(7, 8), player);
		world.show();
	}

	public static void suicide() {
		DifficultKeyWorld world = new DifficultKeyWorld(new BoundedGrid<Actor>(15, 20));
		world.add(new Location(7, 12), new SpaceDebris());
		world.add(new Location(7, 13), new SpaceDebris());
		world.add(new Location(8, 12), new SpaceDebris());
		world.add(new Location(8, 13), new SpaceDebris());
		world.add(new Location(7, 4), new SpaceDebris());
		world.add(new Location(7, 3), new SpaceDebris());
		world.add(new Location(8, 4), new SpaceDebris());
		world.add(new Location(8, 3), new SpaceDebris());
		
		PlayerShip player = new PlayerShip(world, 0, 0);
		world.add(new Location(7, 8), player);
		world.show();
	}

	public static void difficulty() {
		String[] options1 = { "Easy", "Medium", "Hard", "Suicidal" };
		int choice = JOptionPane.showOptionDialog(null, "Choose a difficulty:",
				"Difficulty", 0, 3, null, options1, null);
		switch (choice) {
		case 0: easy(); break;
		case 1: medium(); break;
		case 2: hard(); break;
		case 3: suicide(); break;
		}
	}

	// ***********LEADERBOARD
	public static void fill(ArrayList<LeaderboardEntry> leaderboard) {
		try {
			Scanner inFile = new Scanner(new File("leaderboard.txt"));

			String line;
			String[] data;

			while (inFile.hasNext()) {
				LeaderboardEntry one = new LeaderboardEntry();

				line = inFile.nextLine();
				data = line.split(" ");
				one.setInitials(data[0]);
				one.setScore(Integer.parseInt(data[1]));

				leaderboard.add(one);
			}
			inFile.close();
		} catch (Exception e) {
			System.out.println("error reading file: " + e);
		}
	}

	public static void print(ArrayList<LeaderboardEntry> leaderboard) {
		Collections.sort(leaderboard);
		JTextArea area = new JTextArea();
		area.setFocusable(false);
		area.append("\tInitials:\t\t\t\t\t\t\tScore:\n\n");
		for (int i = 0; i < leaderboard.size(); i++) {
			area.append((i + 1) + "\t" + leaderboard.get(i) + "\n");
		}
		area.setBackground(new Color(250, 250, 210));
		area.setForeground(new Color(0, 0, 0));
		area.setFont(new Font("Lucida Console", Font.PLAIN, 13));
		area.setRows(35);
		area.setColumns(100);
		JScrollPane pane = new JScrollPane(area);
		JOptionPane.showMessageDialog(null, pane);
	}

	// ***********END

	public static int menu() {
		String[] options1 = { "Retro Mode", "Pacfisim", "Instructions",
				"Leaderboard", "Exit" };
		int choice = JOptionPane.showOptionDialog(null,
				"Welcome to the game. Choose a mode:", "Main Menu", 0, 3, null,
				options1, null);
		return choice;
	}

	public static void instructions() {
		JOptionPane.showMessageDialog(null,"Controls: Arrow keys control movement, "
				+ "WASD control shooting. Bomb with the space bar.\n\n"
				+ "Drone Types:\n\t-Blue: Tracks your movements and follows"
				+ "\n\t-Red: Cenitpede; traverses columns"
				+ "\n\t-Gree: Coward: Evades lasers unless point blank"
				+ "\n\nPower-Ups:\n"
				+ "\t-Star: Grants 10 turns of invinciblity, albeit for no points"
				+ "\n\t-Extra Bomb/Points/Life: Self-explanatory ~_~"
				+ "\n\t-Gate: Small portal that removes all enemies within a radius larger than" +
					" the normal bomb's as well as\n\tall enemies in the row behind you."
				+ "\n\nRetro Mode: Classic space shooter. Pilot your ship"
				+ " and shoot multiple types of drones to obtain the high score.\n"
				+ "\nPacifism: "
				+ "Encounter only blue drones. You cannot fire bullets; your score "
				+ "relies entirely on gates and bombs.");
		//menu();
	}
	
	public static void changeJOP()
	{
		UIManager.put("Label.font", new FontUIResource
						(new Font("Impact", Font.PLAIN, 26)));
		UIManager.put("OptionPane.messageForeground",new Color(147,112,219));
		UIManager.put("Panel.background",new Color(240,255,255));
		UIManager.put("OptionPane.background",new Color(46,139,87));
		UIManager.put("Button.background",new Color(32,178,170));
		UIManager.put("Button.foreground", new Color(0,0,128));
		UIManager.put("Button.font", new FontUIResource
						(new Font("Impact", Font.PLAIN, 14)));
	}

	public static void main(String[] args) {
		
		changeJOP();
		int choice;
		do{
			choice = menu();

			switch (choice) {
			case 0: difficulty(); break;
			case 1: pacifistRunner(); break;
			case 2: instructions(); break;
			case 3: {
				ArrayList<LeaderboardEntry> lb = new ArrayList<LeaderboardEntry>();
				fill(lb);
				print(lb);
			} break;
			case 4: System.exit(0);
			}
		}while(choice == 2 || choice == 3);
	}
}
