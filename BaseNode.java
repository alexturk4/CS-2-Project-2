//Anesh Turk att170630
package Tickets;

public abstract class BaseNode {
	
	int row, seat;
	boolean reserved;
	char ticketType; //this denotes whether the seat is adult, child or senior

	public BaseNode(int r, int s, boolean res, char t) {
		row = r;
		seat = s;
		reserved = res;
		ticketType = t;
		
		// TODO Auto-generated constructor stub
	}
	
	public void setRow(int r) {row =r;}
	public void setSeat(int s) {seat = s;}
	public void changeSeat(char c) {ticketType = c;}
	public void setReserved() {reserved = true;}
	
	public int getRow() {return row;}
	public int getSeat() {return seat;}
	public boolean isReserved() {return reserved;}
	public char ticType() {return ticketType;}
	
}

