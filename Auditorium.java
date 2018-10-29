//Anesh Turk att170630
package Tickets;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.Scanner;

public class Auditorium {
	
	TheaterSeat first; 
	int rows, columns;
	
	
	
	public Auditorium() //this builds the auditorium from the file 
	{
	
		Scanner file = null;
		
		try 
		{
			file = new Scanner(new File("A1.txt"));
			
			while (file.hasNext()) //counts how many lines are in the file
			{
				//String line = file.nextLine();
				file.nextLine();
				rows++;
			}
		}
		catch(FileNotFoundException e)
		{System.out.println("File unable to be opened.");}
		
		file.close();
		
		first = null;
		
		try 
		{
			file = new Scanner(new File("A1.txt"));
		
			for (int i = 0; i< rows; i++) //reads down the rows
			{
				String line = file.nextLine(); //reads in the entire line
				
				if (i == 0) //checks the length of the line the first time the loop runs
				columns = line.length(); //finds how many characters are in the line
				
				
				for (int k = 0; k<columns; k++) //reads across the columns
				{
					TheaterSeat node, ptr = first; //the pointer used to traverse the array is initialized to head
					
					boolean occupied = true;
					
					if  (line.charAt(k) == '.') //if the character is a '.', then the seat is unoccupied
						occupied = false;
					
					node = new TheaterSeat(i, k, occupied, line.charAt(k)); //creates a new seat object
					
					if (first == null) //if the list is empty, it will point to the seat which was just created
						first = node;
					else
					{
						while (ptr.getNext() != null) //move the pointer to the end of the list
							ptr = ptr.getNext();
						
						ptr.setNext(node); //set the last node next to point at the newly created node
						
					}		
			
				}
				
			}
			file.close();
		}
		catch(Exception e)
		{System.out.println("File unable to be opened.");}
		
		//testPrint();
		connectNodes();
		
	}
	
	public void printAuditorium()
	{
		System.out.print("  ");
		for (int i=65;i<columns+65;i++) //prints out the alphabet header on the first line
			System.out.print((char)i);
		
		TheaterSeat ptr = first;
		
		for (int i = 0; i< rows; i++) //reads down the rows
		{
			System.out.print("\n" + (i+1) + " "); //prints the row number
			
			for (int k = 0; k<columns; k++) //reads across the columns
			{
				
				if (((BaseNode)ptr).ticType() != '.') //if the seat is occupied, print out a '#' symbol
					System.out.print("#");
				else
					System.out.print("."); //otherwise we can print a '.' symbol
				
				ptr = ptr.getNext();
			}
		}
		System.out.print("\n"); 
		
		//***** testing purposes only
		/*System.out.print("  ");
		for (int i=65;i<columns+65;i++) //prints out the alphabet header on the first line
			System.out.print((char)i);
		
		System.out.print("\n  ");
		for (int i=0;i<columns;i++) //prints out the alphabet header on the first line
			System.out.print(i % 10);
		
		TheaterSeat ptr = first;
		
		for (int i = 0; i< rows; i++) //reads down the rows
		{
			System.out.print("\n" + (i+1) + " "); //prints the row number
			
			for (int k = 0; k<columns; k++) //reads across the columns
			{
				
				System.out.print(((BaseNode)ptr).ticType()); 
				
				ptr = ptr.getNext();
			}
		}
		System.out.print("\n"); */
		//********************
	}
	
	public void connectNodes() //connect all the nodes together in the up/down and left/right directions
	{
		
		//WRITE FUNCTION TO WORK WITH ROOMS THAT ONLY HAVE 1 ROW/COLUMN
		
		TheaterSeat ptr1 = first;
		TheaterSeat ptr2 = first.getNext(); //make another pointer which is 1 further down the list
		
		int listPos = 1; 
		
		while (ptr2 != null) //this connects all the nodes in the left and right direction
		{
			if (listPos == 1) //if ptr1 is in the leftmost column, the left pointer points at null
				ptr1.setLeft(null);
			
			
			
			if ((listPos + 1) % columns != 0) //if ptr2 is not in the rightmost column
			{
				ptr1.setRight(ptr2);
				ptr2.setLeft(ptr1);
				
				ptr1 = ptr2; //move ptr1 to ptr2's current position
				ptr2 = ptr2.getNext(); //move ptr2 down the list
				
				listPos++;
			}
			else //if ptr2 is in the rightmost column
			{
				ptr1.setRight(ptr2);
				ptr2.setLeft(ptr1);
				ptr2.setRight(null); //right pointer points at null
				
				if (ptr2.getNext() != null) //if we are not at the end of the list, keep moving it down
				{
					ptr1 = ptr2.getNext(); //this moves ptr1 2 nodes down the list to the first node of the next row
					ptr2 = ptr1.getNext(); //this moves ptr2 to the second node of the next row
					
					//ptr1.setLeft(null);
				}
				
				listPos += 2; //we increment position by two, as we moved ptr1 two nodes down the list
			}
			
			
		}
		
		//now that all the nodes have been connected in the left/right directions, we must connect them in the up/down directions
		
		ptr1 = first; //move ptr1 and ptr2 back to the top left corner of the grid
		ptr2 = first;
		
		for (int i=0; i<columns; i++) //this moves ptr2 to the next row directly below ptr1 
			ptr2 = ptr2.getNext();
			
		//now ptr1 is pointing at the top left node, and ptr2 is at the leftmost column in the row below ptr1
		//these two pointers will move together in unison from left to right and down the rows
		
		listPos = 1; //reset the position back to 1, this keeps track of where ptr1 is in the 1d list
		
		while (ptr2 != null) //connects all nodes in the up/down directions
		{
			if (listPos <= columns) //if ptr1 is in the first row, the up pointer is null
				ptr1.setUp(null);
			
			//this formula is to check if ptr2 is on the last row
			if (listPos >= (rows * columns - 2 * columns + 1)) 
			{
				ptr1.setDown(ptr2); //makes the two nodes point at each other
				ptr2.setUp(ptr1);
				
				ptr2.setDown(null); //if ptr2 is in the last row, its down pointer is at null
				
				ptr1 = ptr1.getNext(); //move both nodes together across the columns 
				ptr2 = ptr2.getNext(); //also moves nodes down the columns	
				
			}
			else
			{
				ptr1.setDown(ptr2); //makes the two nodes point at each other
				ptr2.setUp(ptr1);
				
				ptr1 = ptr1.getNext(); //move both nodes together across the columns 
				ptr2 = ptr2.getNext(); //also moves nodes down the columns
			}
			
			listPos++;
		}
		
		//testPrint();
		
	}
	
	public void testPrint()
	{
		TheaterSeat ptr = first;
		
		while (ptr != null)
		{
			System.out.println("Seat name: ~" + ptr.ticType());
			
			if (ptr.getUp() != null)
				System.out.println("Up: " + ptr.getUp().ticType());
			
			if (ptr.getDown() != null)
			System.out.println("Down: " + ptr.getDown().ticType());
			
			if (ptr.getLeft() != null)
			System.out.println("Left: " + ptr.getLeft().ticType());
			
			if (ptr.getRight() != null)
			System.out.println("Right: " + ptr.getRight().ticType());
			
			System.out.println("Reserved: " + ptr.isReserved());
			
			System.out.println("\n");
			ptr = ptr.getNext();
		}
	}
	public int getRows() {return rows;}
	public int getColumns() {return columns;}
	
	public boolean bestAvailable(int adult, int child, int senior, Scanner input)
	{
		int totalTicket = adult+child+senior;
		double audiCenterRow = (rows-1)/2.0; //stores the position of the center of the auditorium
		double audiCenterColumn = (columns-1)/2.0;
		double smallestDistance = 100;
		
		int startCol=0, startRow=0; //stores the position of the starting column and starting row of the seat 
				
		TheaterSeat ptr = first;
		TheaterSeat firstSeat, bestSeat = null; //this holds the position of the first seat 
		//in the group and the best seating arrangement in the auditorium
		
					
		for (int k=0; k<rows; k++) //this moves the pointer down the rows
		{
			TheaterSeat rowPtr = ptr; 
			
			for (int i=0; i<columns-totalTicket+1; i++) //this moves the pointer across the columns
			{
				firstSeat = ptr; //this holds the position of the first seat in the group
				
				boolean openSeats = true; //boolean to flag if there are n sequential seats open
				
				for (int j=0; j<totalTicket; j++)//checks if n sequential seats are open
					{
						
						if (ptr.isReserved() == true) //if the seat is occupied, break the inner loop 
						{
							openSeats = false;
							break;
						}
					
						ptr = ptr.getRight(); //this moves the pointer across the columns to the right
					}
				
				if (openSeats) //if there are n seats open, calculate distance from the middle of the row
				{
					double xPoint, yPoint;
					
					xPoint = (2*i+totalTicket-1)/2.0; //calculates the midpoint of column the seats that are open
					yPoint = k; //sets the y point as the row number it is on
					
					//this formula uses pythagorean theorem to calculate the distance of the center of the seating arrangement to that of the auditorium

					double distance = Math.pow((Math.pow(xPoint - audiCenterColumn, 2) + Math.pow(yPoint - audiCenterRow, 2)), .50);
					
				
					if (distance < smallestDistance) //if the current distance is less than the smallest distance measured
					{
						startCol = i; //set the starting seat at the current position
						startRow = k; //set the position of the starting row
						smallestDistance = distance; //set the new smallest distance as the current distance
						bestSeat = firstSeat; //this holds the location in memory of where the best seat is
					}
					else if (distance == smallestDistance)//if the new distance is equal to the previous shortest distance
					{
						//if there is a tie for distance, pick the one with the row closest to the middle row
						if (Math.abs(k-audiCenterRow) < Math.abs(startRow-audiCenterRow))
							{
								startCol = i; //set the starting seat at the current position
								startRow = k; //set the position of the starting row
								smallestDistance = distance; //set the new smallest distance as the current distance
								bestSeat = firstSeat; //this holds the location in memory of where the best seat is
							}
						else if (Math.abs(k-audiCenterRow) == Math.abs(startRow-audiCenterRow)) //if the rows are both equally close
							{
								if (k < startRow) //pick the smaller row number 
								{
									startCol = i; //set the starting seat at the current position
									startRow = k; //set the position of the starting row
									smallestDistance = distance; //set the new smallest distance as the current distance
									bestSeat = firstSeat; //this holds the location in memory of where the best seat is
								}
							}
					}
				}
				
				ptr = firstSeat; 
				ptr = ptr.getRight();
			}
			ptr = rowPtr; //moves the pointer back to the first seat in the row
			ptr = ptr.getDown(); //this moves the pointer to the next row below the current row
		}
		
		if (smallestDistance == 100) //if the smallest distance stayed at 100, there were no available seats
		{
			System.out.println("No seating arrangement available in theater room for seats requested");
			return false;
		}
		
		if (totalTicket > 1)
			System.out.println("The best available arrangement is from seat: " + (char)(startCol+65) + " through " + (char)(startCol+64+totalTicket) + 
				  " on row " + (startRow + 1) + "\nWould you like to reserve, Y/N?");
		else if (totalTicket == 1) //don't display range of seats if only 1 seat is requested
			System.out.println("The best available arrangement  at seat: " + (char)(startCol+65) +
					  " on row " + (startRow + 1) + "\nWould you like to reserve, Y/N?");
		
		
		String line; //used to store the character which the user enters
		
		Boolean valid; //flag used to validate if user input is correct
		do 
		{
			line = input.nextLine();
			
			if (line.equals(""))
				line = input.nextLine();
			
			valid = true;
			
			if (line.length() != 1) //if the user tries to enter multiple characters, it is invalid
				valid = false;
			
			if (line.charAt(0) != 'Y' && line.charAt(0) != 'N') //if the character is not either Y or N, it is invalid
				valid = false;
			
		}
		while (valid == false); //loop until the user enters proper input
		
		if (line.charAt(0) == 'Y') //if the user wants to reserve the seats
		{
			ptr = bestSeat; //this moves ptr to the location of the first seat in the group of seats closest to the middle
			
			for (int i=0; i<(adult); i++) //reserves all of the adult seats
			{
				ptr.changeSeat('A');
				ptr.setReserved();
				ptr = ptr.getRight();
			}
			
			for (int i=0; i<(child); i++) //reserves all of the children seats
			{
				ptr.changeSeat('C');
				ptr.setReserved();
				ptr = ptr.getRight();
			}
			
			for (int i=0; i<(senior); i++) //reserves all of the senior seats
			{
				ptr.changeSeat('S');
				ptr.setReserved();
				ptr = ptr.getRight();
			}
		}
		
		return true;
	}
	
	public boolean isAvailable(int rowNum, int columnNum, int adult, int child, int senior)
	{
		
		TheaterSeat ptr = first;
		boolean isAvailable = true;
		
		for (int i=0; i<(rowNum-1); i++) //this moves the pointer down to the proper row number
			ptr = ptr.getDown();
		
		for (int i=0; i<columnNum; i++) //this moves the pointer across the columns to the correct starting seat
			ptr = ptr.getRight();
		
		TheaterSeat startSeat = ptr; //this pointer will be used to hold the position of the starting seat
		
		
		for (int i=0; i<(adult+child+senior); i++) //this checks if the 'n' seats to the right of the starting seat are occupied
		{
			if (ptr.isReserved() == true)
				isAvailable = false;
			
			ptr = ptr.getRight(); 
			
			if (ptr == null && adult+child+senior != 1) //if the pointer becomes null, we know that the selection extends past the end of row
			{
				return false; 
			}
		}
		
		if (isAvailable == true) //if the seats were all unoccupied
		{
			ptr = startSeat; //move the pointer back to the first seat requested
			
			for (int i=0; i<(adult); i++) //reserves all of the adult seats
			{
				ptr.changeSeat('A');
				ptr.setReserved();
				ptr = ptr.getRight();
			}
			
			for (int i=0; i<(child); i++) //reserves all of the children seats
			{
				ptr.changeSeat('C');
				ptr.setReserved();
				ptr = ptr.getRight();
			}
			
			for (int i=0; i<(senior); i++) //reserves all of the senior seats
			{
				ptr.changeSeat('S');
				ptr.setReserved();
				ptr = ptr.getRight();
			}
			
			return true; //true if seats were available and were reserved
		}
		else //false if the seats were not available to be reserved
			return false;
	}

	public void printResults() //this prints the earnings and tickets sold of the auditorium
	{
		System.out.println("Total Seats in Auditorium: " + rows*columns);
		
		int child=0, adult=0, senior=0, total;
		double sales;
		
		TheaterSeat ptr = first;
		
		for (int i=0;i<rows;i++) //this nested loop counts the number of each ticket type
		{
			for (int k=0;k<columns;k++)
			{
				if (ptr.ticType() == 'A')
					adult++;
				if (ptr.ticType() == 'C')
					child++;
				if (ptr.ticType() == 'S')
					senior++;
				
				ptr = ptr.getNext(); //moves the pointer down the list
			}
		}
		
		total = adult+child+senior;
		sales = (adult * 10) + (child * 5) + (senior * 7.5);
		
		DecimalFormat decimal2 = new DecimalFormat(".00");
		
		
		System.out.println("Total Tickets Sold:        " + total);
		System.out.println("Adult Tickets Sold:        " + adult);
		System.out.println("Child Tickets Sold:        " + child);
		System.out.println("Senior Tickets Sold:       " + senior);
		System.out.println("Total Ticket Sales:       $" + decimal2.format(sales));
	}
	
	public void writeFile() //this function writes the linked list back to the file
	{
		try
		{
			PrintWriter output = new PrintWriter("A1.txt");
			
			TheaterSeat ptr = first; 
			
			for (int i=0;i<rows;i++) //this nested loop prints out the array to the file
			{
				String line = Character.toString(ptr.ticType()); //the string starts with the first character in the first node
				
				for (int k=1;k<columns;k++)
				{
					ptr = ptr.getNext(); //move the pointer down the list
					line += Character.toString(ptr.ticType()); //this appends characters to the end of the string down the row
				}
					
				output.println(line); //this prints out the string to a line in the file
				ptr = ptr.getNext(); //move the pointer another node to start on the next row
			}
			output.close();
		
		}
		catch(Exception e)
		{System.out.println("File unable to be opened."); return;}
	}
}

	
	