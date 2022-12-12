import java.util.Random;

public class Driver {

	public static void main(String[] args)  
	{
		Random rand = new Random();
		OA table = new OA();
		String input;
		String value;
		
		//----------------------------------------------------------------------------------------------------------------
		for(int i = 0; i < 10000 ; i++)
		{
			input = Integer.toString(rand.nextInt(100000));
			value = input;
			try 
			{
				table.add(input, value);
			} 
			catch (Exception e) 
			{
				System.out.println(e);
			}	
		}
		System.out.println("--------------------------------------------------------");
		
		
		//-----------------------------------------------------------------------------------------------------------------
		long timeBefore = System.currentTimeMillis();
		for(int i = 0; i < 100; i++)
		{
			input = Integer.toString(rand.nextInt(table.Size));
			value = input;
			System.out.println("Index: " + table.GetHash(value) + " Node: " + table.Get(input) + " Bucket State: " + table.buckets[table.GetHash(input)].State);
		}
		long timeAfter = System.currentTimeMillis() - timeBefore;
		System.out.println("Time to search: " + timeAfter + " milliseconds");
	}
	
	

}
