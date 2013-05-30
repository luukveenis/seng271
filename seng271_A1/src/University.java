import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.StringBuilder;
/**
 * Description:
 * Class to model the university
 * Contains the main or "driver" method
 * Requires a properly formatted textfile as input to the program (see readme)
 * @author Luuk
 * 
 *@TODO Replace the main method with proper JUnit test
 */

public class University {
	
	private String name;
	private ArrayList<Room> roomsInUniversity;
	
	/* The default name will be changed to something more descriptive
	   taken from the text file provided as input to the program */
	public University(){
		this.name = "default";
		this.roomsInUniversity = new ArrayList<Room>();
	}
	
	public University(String name){
		this.name = name;
		this.roomsInUniversity = new ArrayList<Room>();
	}
	
	/* ===============================
	 * ====== GET / SET Methods ====== */
	
	public String getName(){
		return this.name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public void addRoom(Room room){
		this.roomsInUniversity.add(room);
	}
	
	public void removeRoom(Room room){
		this.roomsInUniversity.remove(room);
	}
	
	/* ================================ */
	
	/**
	 * Searches the University for a room by name
	 * @param roomName
	 * @return room
	 */
	public Room findRoom(String roomName){
		for (Room room: roomsInUniversity){
			if(roomName.equals(room.getName())){
				return room;
			}
		}
		return null;
	}
	
	/**
	 * Input string: room-to-add:study-points
	 * Processes these lines to add the rooms (vertices) to the University
	 * @param roomString
	 */
	private void processRooms(String roomString){
		String[] rooms = roomString.split(":"); // separate room name and study point value 
		Room newRoom = new Room(rooms[0], Integer.parseInt(rooms[1]));
		addRoom(newRoom); // add classroom to university
	}
	
	/**
	 * Input string: base-room:adjacent-room:adjacent-room: ...etc. 
	 * Processes input string to build room adjacencies (edges) in the University
	 * @param dependencies
	 */
	private void processDependencies(String dependencies){
		String[] adjacents = dependencies.split(":");
		Room baseRoom = findRoom(adjacents[0]);
		for(int i=1; i<adjacents.length; i++){
			baseRoom.addAdjacentRoom(findRoom(adjacents[i]));
		}
	}
	
	/**
	 * Adds classrooms contained in the input file to the University
	 * Then creates all the references (doors) between different rooms
	 * @param inFileName
	 */
	private void buildUniversity(String inFileName){
		File inFile = new File(inFileName); // open file for reading
		Scanner reader;
		try {
			reader = new Scanner(inFile);
			String name = reader.nextLine().trim(); // get uni name and remove newline
			this.name = name;
			while(reader.hasNext()){ // get classroom names
				String roomString = reader.nextLine().trim();
				if(roomString.equals("===")){ // indicated end of classroom names
					break;
				}
				processRooms(roomString); // adds the classrooms to the university
			}
			while(reader.hasNext()){ // get adjacency information
				String dependencyString = reader.nextLine().trim();
				processDependencies(dependencyString); // adds references to adjacent classrooms
			}
			reader.close();
		} catch (FileNotFoundException e){
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Simulates the student making their way through the University. 
	 * Uses recursion to test every possible path until a valid one is found.
	 * The full "valid" path will be contained in the student object's "visitedRooms" stack. 
	 * Original call is made with required study points and "entrance" room as parameters. 
	 * @param student
	 * @param studyPoints
	 * @param startRoom
	 */
	private void teachStudent(Student student, int studyPoints, Room startRoom){
		student.switchRoom(startRoom); // "enter" the next room and update points
		if (student.getCurrentRoom().hasAdjacent("exam") // check if done?
				&& student.getStudyPoints() == studyPoints){
			student.pushVisitedRoom(findRoom("exam"));
			student.found = true;
			return;
		} else if (student.getMotivationPoints() <= 0){
			student.goBackRoom(); // if no motivation return to previous rooom
			return;
		}
		
		for (Room room: student.getCurrentRoom().adjacentRooms){
			if(room.getName().equals("exam")){
				continue; // don't need to enter exam room since it has 0 points
			} else if (student.found){
				return; // if the path has been found, exit recursive calls
			} else {
				teachStudent(student, studyPoints, room); // recursive call
			}
		}
		if (student.found)
			return; // exit call if path has been found
		student.goBackRoom(); // if loop completes with no valid path, go to previous room
	}
	
	/**
	 * Provides a better string description of the University
	 */
	@Override
	public String toString(){
		StringBuilder description = new StringBuilder("");
		description.append(this.name);
		description.append("\n");
		for (Room r: roomsInUniversity){
			description.append(r.getName());
			description.append(" ");
		}
		return description.toString();
	}
	
	public static void main(String[] args){
		University myUni = new University();
		myUni.buildUniversity(args[0]);
		System.out.println(myUni + "\n");
		
		Student me = new Student("Luuk", 214);
		Room begin = myUni.findRoom("math");	// "entrance" to unitversity
		myUni.teachStudent(me, 214, begin);
		System.out.println(me.getVisitedRooms());
	}

}
