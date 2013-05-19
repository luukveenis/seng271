import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
/**
 * Class to model the university
 * @author Luuk
 *
 */

public class University {
	
	private String name;
	private ArrayList<Room> roomsInUniversity;
	
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
	 * Input string: room-to-add:adjacent-room:adjacent-room: ...etc. 
	 * Processes these lines to add the rooms (vertices) to the University
	 * @param roomString
	 */
	private void processRooms(String roomString){
		String[] rooms = roomString.split(":");
		Room newRoom = new Room(rooms[0], Integer.parseInt(rooms[1]));
		addRoom(newRoom);
	}
	
	/**
	 * Input string: base-room:adjacent-room:adjacent-room: ...etc. 
	 * Processes input string to build room adjacencies (edges) in the University
	 * @param
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
		File inFile = new File(inFileName);
		Scanner reader;
		try {
			reader = new Scanner(inFile);
			String name = reader.nextLine().trim();
			this.name = name;
			while(reader.hasNext()){
				String roomString = reader.nextLine().trim();
				if(roomString.equals("===")){
					break;
				}
				processRooms(roomString);
			}
			while(reader.hasNext()){
				String dependencyString = reader.nextLine().trim();
				processDependencies(dependencyString);
			}
			reader.close();
		} catch (FileNotFoundException e){
			e.printStackTrace();
		}
	}
	
	private void teachStudent(Student student, int studyPoints/*, Room start*/){
		//student.switchRoom(start);
		
		for (Room room: student.getCurrentRoom().adjacentRooms){
			/*if (student.getCurrentRoom().hasAdjacent("exam") 
					&& student.getStudyPoints() == studyPoints){
				System.out.println("**** FOUND ITTTT ****");
				student.found = true;
				return;
			}*/
			if(room.getName().equals("exam")){
				continue;
			}
			student.switchRoom(room);
			System.out.println("current room: " + student.getCurrentRoom().getName() + " study: " + student.getStudyPoints());
			if (student.getCurrentRoom().hasAdjacent("exam") 
					&& student.getStudyPoints() == studyPoints){
				System.out.println("**** FOUND ITTTT ****");
				student.found = true;
				return;
			} else if (student.getMotivationPoints() <= 0){
				System.out.println("Unmotivated");
				student.goBackRoom();
				System.out.println(student.getCurrentRoom().getName());
				continue;
				//break;
			} else {
				System.out.println("studying");
				//teachStudent(student, studyPoints, room);
				teachStudent(student, studyPoints);
			}
		}
		if (student.found)
			return;
		student.goBackRoom();
	}
	
	private void teachStudent2(Student student, int studyPoints, Room startRoom){
		student.switchRoom(startRoom);
		if (student.getCurrentRoom().hasAdjacent("exam") 
				&& student.getStudyPoints() == studyPoints){
			student.found = true;
			return;
		} else if (student.getMotivationPoints() <= 0){
			student.goBackRoom();
			return;
		}
		
		for (Room room: student.getCurrentRoom().adjacentRooms){
			if(room.getName().equals("exam")){
				continue;
			} else if (student.found){
				return;
			} else {
				teachStudent2(student, studyPoints, room);
			}
		}
		if (student.found)
			return;
		student.goBackRoom();
	}
	
	public String toString(){
		String description = "";
		description += this.name + "\n";
		for (Room r: roomsInUniversity){
			description += r.getName() + " ";
		}
		return description;
	}
	
	public static void main(String[] args){
		University myUni = new University();
		myUni.buildUniversity(args[0]);
		System.out.println(myUni + "\n");
		
		Student me = new Student("Luuk", 214);
		Room begin = myUni.findRoom("math");
		myUni.teachStudent2(me, 214, begin);
		System.out.println(me.getVisitedRooms());
	}

}
