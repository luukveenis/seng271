/**
 * Class to model a student in the university
 * @author Luuk Veenis
 *
 */
public class Student {
	
	private final String name;
	private Room currentRoom;
	private int motivationPoints;
	private int studyPoints;
	
	/*
	 * Constructor that creates a new student with name <name>
	 * @param String
	 */
	public Student(String name){
		this.name = name;
		this.motivationPoints = 214;
		this.studyPoints = 0;
		this.currentRoom = null;
	}
	
	/**
	 * Returns the name of the student
	 * @return
	 */
	public String getName(){
		return this.name;
	}
	
	/**
	 * Returns the room the student is currently in
	 * @return
	 */
	public Room getCurrentRoom(){
		return this.currentRoom;
	}
	
	/**
	 * Sets the student's "current room" to room
	 * Simulating the student moving to a new classroom
	 * @param room
	 */
	public void moveToRoom(Room room){
		this.currentRoom = room;
	}

}
