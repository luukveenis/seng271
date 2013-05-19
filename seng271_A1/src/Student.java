/**
 * Class to model a student in the university
 * @author Luuk Veenis
 *
 */
import java.util.Stack;
import java.lang.StringBuilder;

public class Student {
	
	private final String name;
	private Room currentRoom;
	private Stack<Room> visitedRooms;
	private int motivationPoints;
	private int studyPoints;
	public boolean found;
	
	/**
	 * Constructor that creates a new student with name <name>
	 * @param String
	 */
	public Student(String name, int motivationPoints){
		this.name = name;
		this.found = false;
		this.motivationPoints = motivationPoints;
		this.studyPoints = 0;
		this.currentRoom = null;
		this.visitedRooms = new Stack<Room>();
	}
	
	/* ===============================
	 * ====== GET / SET Methods ====== */
	
	public String getName(){
		return this.name;
	}
	
	public Room getCurrentRoom(){
		return this.currentRoom;
	}
	
	public int getMotivationPoints(){
		return this.motivationPoints;
	}
	
	public void setMotivationPoints(int points){
		this.motivationPoints = points;
	}
	
	public int getStudyPoints(){
		return this.studyPoints;
	}
	
	public void setStudyPoints(int points){
		this.studyPoints = points;
	}
	
	public void pushVisitedRoom(Room room){
		this.visitedRooms.push(room);
	}
	
	public Room popVisitedRoom(){
		return this.visitedRooms.pop();
	}
	
	 /*======================================*/
	
	
	/**
	 * Sets the student's "current room" to room
	 * Simulating the student moving to a new classroom
	 * @param room
	 */
	public void switchRoom(Room room){
		this.currentRoom = room;
		this.setStudyPoints(getStudyPoints() + room.getCoursePoints());
		this.setMotivationPoints(getMotivationPoints() - room.getCoursePoints());
		this.pushVisitedRoom(room);
	}
	
	/**
	 * Resets the student's current room to the previous one
	 * Also resets the motivation and study points accordingly
	 */
	public void goBackRoom(){
		this.setMotivationPoints(getMotivationPoints() + currentRoom.getCoursePoints());
		this.setStudyPoints(getStudyPoints() - currentRoom.getCoursePoints());
		popVisitedRoom();
		currentRoom = visitedRooms.peek();
	}
	
	/**
	 * Returns a string representation of the rooms the student
	 * has visited, in sequential order.
	 * @return
	 */
	public String getVisitedRooms(){
		StringBuilder description = new StringBuilder("");
		while (!this.visitedRooms.isEmpty()){
			Room r = this.visitedRooms.pop();
			description.insert(0, r.getName());
			if (this.visitedRooms.size() > 0)
				description.insert(0, "->");
		}
		return description.toString();
	}
}
