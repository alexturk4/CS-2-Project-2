//Anesh Turk att170630

package Tickets;

import java.io.*;
import java.util.*;



public class Main 
{
	
	public static void main(String[] args) 
	{
		
		String s = "h";
		
		//int rows, columns;
		int rowRequest=-1, startSeat=-1, adult=-1, child=-1, senior=-1;
		
		Auditorium room = new Auditorium();
		
		boolean loop = false;
		
		do 
		{
			Scanner input = new Scanner(System.in);
			
			if (!s.equals("")) //this will not prompt the menu if the user has not entered anything in
				System.out.println("1. Reserve Seats\n2. Exit");
			
			s = input.nextLine();
			
			if (s.equals("1"))
			{
				room.printAuditorium();
					
				do //request row number
				{
					try
					{
					System.out.println("Row number:");
					rowRequest = input.nextInt();
					}
					catch (Exception e)
					{
						System.out.println("Inalid row number, please enter a valid value:");
						input.next();
					}
				}
				while (rowRequest > room.getRows() || rowRequest<1); //loop while the user enters values for the row outside the range
				
				
				do //request seat letter
				{
					try
					{
						System.out.println("Seat letter:");
						
						//String line = input.nextLine();
						char seat = input.next().charAt(0); //reads the character which the user enters
						startSeat = (int)seat; //converts the character to its ascii value for input validation
					}
					catch (Exception e)
					{
						System.out.println("Inalid seat letter, please enter a valid value:");
						input.next();
					}
				}
				while (startSeat < 65 || startSeat > (64+room.getColumns())); //loop while the ascii value is out of range
				startSeat -= 65; //convert the ascii values to column values in the auditorium
				
				do //request number of adults
				{
					try
					{
					System.out.println("Number of adult tickets:");
					adult = input.nextInt();
					}
					catch (Exception e)
					{
						System.out.println("Inalid ticket amount, please enter a valid value.");
						input.next();
					}
				}
					
				while (adult<0); //loop while the number of tickets is less than 0
				
				do //request number of children
				{
					try
					{
					System.out.println("Number of child tickets");
					child = input.nextInt();
					}
					catch (Exception e)
					{
						System.out.println("Inalid ticket amount, please enter a valid value.");
						input.next();	
					}
				}
				while (child<0); //loop while the number of tickets is less than 0
				
				do //request number of senior tickets
				{
					try
					{
					System.out.println("Number of senior tickets");
					senior = input.nextInt();
					}
					catch (Exception e)
					{
						System.out.println("Inalid ticket amount, please enter a valid value.");
						input.next();	
					}
				}
				while (senior<0); //loop while the number of tickets is less than 0
				
				
				if (room.isAvailable(rowRequest, startSeat, adult,child,senior) == false) //if the seats requested are unavailable
				{
					System.out.print("Seats requested are unavailable\n");
					//Boolean isAvailable = room.bestAvailable(adult, child, senior, input);
					room.bestAvailable(adult, child, senior, input);
					/*if (isAvailable == false) //call the best available if no seats are available
						System.out.println("No seating arrangement avaialable"); */
				}
				else
					System.out.println("Requested seats available and were successfully reserved");
				 
				loop = true;
			}	
			else if (s.equals("2")) //stop prompting the menu if the user requests to exit
				loop = false;
			else if (s.equals("")) //if the user has not typed anything in loop back to the top
				loop = true;
			else //loop again if invalid input
			{
				System.out.println("Invalid Input");
				loop = true;
			}
				
		}
			while (loop == true);
		
		room.printResults(); //when the user exits the menu print the results
		room.writeFile(); //writes the linked list back out to the file

	}
		
	public static TheaterSeat readFile(int columns, int rows, TheaterSeat head) //reads the file into the linked list
	{
		Scanner file;
		
		try 
		{
			file = new Scanner(new File("A1.txt"));
		
			for (int i = 0; i< rows; i++) //reads down the rows
			{
				String line = file.nextLine(); //reads in the entire line
				
				boolean occupied = true;
				
				for (int k = 0; k<columns; k++) //reads across the columns
				{
					TheaterSeat node, ptr = head; //the pointer used to traverse the array is initialized to head
					
					if  (line.charAt(k) == '.') //if the character is a '.', then the seat is unoccupied
						occupied = false;
					
					node = new TheaterSeat(i, k, occupied, line.charAt(k)); //creates a new seat object
					
					if (head == null) //if the list is empty, it will point to the seat which was just created
						head = node;
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
		{System.out.println("File unable to be opened."); return null;}
		return head;
	}
	
}
