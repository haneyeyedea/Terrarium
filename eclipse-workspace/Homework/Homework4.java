public class Homework4
{
    public static void main (String[] args)
    {
        int min = 0, max = 5;
        
        for (int rows = 1; rows <= max; rows++)
        {
            for (int space = max; space < rows; space--)
            {
                System.out.print(" ");
            }
            
            for (int star = min; star < 2*rows; star++);
            {
                System.out.print("*");
            }
            
            System.out.println("");
        }
    }
}