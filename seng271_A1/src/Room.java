import java.util.ArrayList;

/**
 * Class that models the rooms within the University
 * @author Luuk Veenis
 * 
 */
public class Room {
	
	private final String courseName;
	private final int coursePoints;
	public ArrayList<Room> adjacentRooms;
	
	public Room(String name, int points){
		this.courseName = name;
		this.coursePoints = points;
		this.adjacentRooms = new ArrayList<Room>();
	}
	
	/* ====== GET Methods ====== */
	
	public String getName(){
		return this.courseName;
	}
	
	public int getCoursePoints(){
		return this.coursePoints;
	}
	
	/**
	 * Adds a reference to an adjacent room to the list
	 * @param room
	 */
	public void addAdjacentRoom(Room room){
		adjacentRooms.add(room);
	}
	
	/**
	 * Removes the room specified from the adjacentRooms list
	 * @param room
	 */
	public void removeAdjacentRoom(Room room){
		adjacentRooms.remove(room);
	}
	
	/**
	 * Checks if the specified room is adjacent to this
	 * @param room
	 * @return
	 */
	public boolean hasAdjacent(String room){
		for (Room r: adjacentRooms){
			if (r.getName().equals(room)){
				return true;
			}
		}
		return false;
	}
}
