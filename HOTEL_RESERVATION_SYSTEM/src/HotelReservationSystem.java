import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Scanner;

public class HotelReservationSystem {
	private static final String RoomNumber = null;
	static String dbdriver="com.mysql.jdbc.Driver";   // driver 
	static String dburl="jdbc:mysql://localhost:3306/ hotel_db";
	static String username="root";
	static String password="";
	 
	public static void main(String[]args) throws ClassNotFoundException, SQLException {
		
			try {
				Class.forName(dbdriver);
				
			} catch (ClassNotFoundException e) {
				System.out.println(e.getMessage());
			}
			try {
				Connection connection = DriverManager.getConnection(dburl,username,password);
				while(true){
					System.out.println();
					System.out.println("HOTEL MANAGEMENT SYSTEM");
					Scanner sc=new Scanner (System.in);
					System.out.println("1.reserve a room");
					System.out.println("2.view reservation");
					System.out.println("3.get room number");
					System.out.println("4.update reservation");
					System.out.println("5.delete reservation");
					System.out.println("0.exit");
					int choice = sc.nextInt();
					switch(choice) {
							case 1:
						ReserveRoom(connection,sc);
						break;
							case 2:
								ViewReservation(connection);
								break;
							case 3:
								GetRoomNumber(connection,sc);
								break;
							case 4:
								updateReservation(connection,sc);
								break;
							case 5:
								deletereservation(connection,sc);
								break;
							case 6:
								exit();
								sc.close();
								return;
								default :
									System.out.println("Invalid choice");
						
							
					}
					}
					
			}
			catch(SQLException e) {
				System.out.println(e.getMessage());
				}
			catch(InterruptedException e) {
				throw new RuntimeException(e);
			}
	}
	  private static void ReserveRoom(Connection connection,Scanner sc) {
		  try {
			  System.out.println("enter guest name");
			  String guestname=sc.next();
			  sc.nextLine();
			  System.out.println("Enter room number:");
			  int roomNumber=sc.nextInt();
			  System.out.println("enter contact number");
			  String contactNumber=sc.next();
			  
			  String sql = "INSERT INTO reservations (guest_name,room_number,contact_number)"+
			  "VALUES("+ guestname + " , " + RoomNumber + ",'"+ contactNumber +"')";
		  
		  try(Statement statement = connection.createStatement()){
			  int affectedRows=statement.executeUpdate(sql);
			   if (affectedRows>0) {
				   System.out.println("reservation sucessfull");
				   
			   }
			   else {
				   System.out.println("reservation failed");
			   }
		  }}
		  catch(SQLException e) {
			  e.printStackTrace();
		  }
		  
		  
			  
	  }
		  private static void ViewReservation(Connection connection )throws SQLException{
		String sql ="SELECT reservation_id,guest_name,room_number,contact_number,reservation_date FROM";
		try (Statement statement = connection.createStatement();
				ResultSet resultSet= statement.executeQuery(sql)) { // resultSet is an instance
			while(resultSet.next()) { // result set works as a pointer on the table and next is like boolean true
				int reservationId=resultSet.getInt("reservation_id");
				String guestname=resultSet.getString("guest_name");
				int roomnumber =resultSet.getInt("room_number");
				String contactNumber=resultSet.getString("contact_number");
				String reservationDate=resultSet.getTimestamp("reservation_date").toString();
				   
				
			
			}}}
			private static void GetRoomNumber(Connection connection,Scanner sc) {
				try {
					System.out.println("enter reservation ID");
					int reservationId=sc.nextInt();
					System.out.println("enter guest name:");
					String guestName=sc.next();
					String sql= "SELECT room_number FROM reservation"+ 
					"WHERE reservation_id= "+reservationId + "AND guest_name ="+guestName;
					try(Statement statement = connection.createStatement();
							ResultSet resultSet = statement.executeQuery(sql)){
						if(resultSet.next()) {
							int roomNumber=resultSet.getInt("room_number");
							System.out.println("room id for reservation id "+reservationId +"and "+ guestName +"is"+roomNumber);
						}
					}
				}
				catch(SQLException e) {
					e.printStackTrace();
				}
			}
	private static void updateReservation(Connection connection,Scanner sc) {	
		try {
			System.out.println("enter reservation id to update:");
			int reservationId=sc.nextInt();
			sc.nextLine();
			
			if(!reservationExists(connection,reservationId)) {
				System.out.println("reservation not found");
				return;
			}
			System.out.println("enter new guest name");
			String newGuest=sc.nextLine();
			System.out.println("enter new room number");
			int newRoomNo=sc.nextInt();
			System.out.println("enter new contact number");
			String newcontactno=sc.nextLine();
			String sql="UPDATE reservation SET guest_name=" + newGuest + 
					"room_number="+ newRoomNo +"contact no" + 
					newcontactno + "WHERE reservation_id="	+ reservationId;
			try 
				(Statement statement=connection.createStatement()){
					int affectedrows=statement.executeUpdate(sql);
					
					if(affectedrows>0) {
						System.out.println("reservation updated sucessfully");
						
					}
					else {
						System.out.println("reservation failed");
					}
				}
			}
			catch(SQLException e) {
				e.printStackTrace();
			}}
				   public static void deletereservation(Connection connection,Scanner sc) {
					   try {
					System.out.println("entre reservation id to delete");
					int reservationId=sc.nextInt();
					if(!reservationExists(connection,reservationId)) {
						System.out.println("reservation not found");
						return;
					   }
					String sql="DELETE FROM reservationId WHERE reservation_Id="+reservationId;
					try(Statement statement =connection.createStatement()){
						int affectedRows = statement.executeUpdate(sql);
						if(affectedRows>0) {
							System.out.println("reservation deleted sucessfully");
							
						}
						else {
							System.out.println("not deleted");
						}
					}
					   
				   }
					   catch(SQLException e) {
						   e.printStackTrace();
					   }
	
	
		  }
	   private static boolean reservationExists(Connection connection ,int reservationId) {
					   try {
						  
						String sql="SELECT reservation_id FROM reservations WHERE reservation_id="+ reservationId;
						   try(Statement statement=connection.createStatement();
								   ResultSet resultSet=statement.executeQuery(sql)) {
							   return resultSet.next();
						   }
								   
					   }
					   catch(SQLException e) {
						   e.printStackTrace();
						   return false;
						   
					   }
				   }
			   public static void exit()throws InterruptedException{
				   System.out.println("exiting system");
				   int i=5;
				   while(i!=0) {
					   System.out.println(".");
					   Thread.sleep(450);
					   i--;
				   }
				   System.out.println();
				   System.out.println("thankyou");
			   }

}

