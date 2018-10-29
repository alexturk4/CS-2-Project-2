//Anesh Turk att170630
package Tickets;

public class TheaterSeat extends BaseNode{
	
	TheaterSeat up, down, left, right, next;  //pointers for the seats around it

	public TheaterSeat(int r, int s, boolean re, char t) //calls the superclass constructor 
	{
		super(r,s,re,t);
	}
	public void setNext(TheaterSeat u) {next = u;}
	public void setUp(TheaterSeat u) {up = u;}
	public void setDown(TheaterSeat u) {down = u;}
	public void setLeft(TheaterSeat u) {left = u;}
	public void setRight(TheaterSeat u) {right = u;}
	
	public TheaterSeat getUp() {return up;}
	public TheaterSeat getDown() {return down;}
	public TheaterSeat getLeft() {return left;}
	public TheaterSeat getRight() {return right;}
	public TheaterSeat getNext() {return next;}

}
