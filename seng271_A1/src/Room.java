import java.util.ArrayList;

/**
 * Class that models the rooms within the University
 * @author Luuk
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
	
	public String getName(){
		return this.courseName;
	}
	
	public int getCoursePoints(){
		return this.coursePoints;
	}
	
	/**
	 * Add a reference to an adjacent room to the list.
	 * Simultaneously sets the reference in the adjacent room
	 * to point back at this room.
	 * @param room
	 */
	public void addAdjacentRoom(Room room){
		adjacentRooms.add(room);
	}
	
	/**
	 * Removes the room parameter from the adjacentRooms  list
	 * @param room
	 */
	public void removeAdjacentRoom(Room room){
		adjacentRooms.remove(room);
	}
	
	public boolean hasAdjacent(String room){
		for (Room r: adjacentRooms){
			if (r.getName().equals(room)){
				return true;
			}
		}
		return false;
	}
	
	public void printAdjacents(){
		for(Room r:adjacentRooms){
			System.out.print(r.getName() + " ");
		}
		System.out.println();
	}
}
