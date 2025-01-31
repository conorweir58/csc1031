import java.util.Arrays;
import java.util.Scanner;

public class PrimeNumber {
    public static void main(String[] args) {

        long cnt = 0;
            
		Scanner scanner = new Scanner(System.in);
        int limit = scanner.nextInt();
       
        boolean[] table = new boolean[limit+1];
        
        Arrays.fill(table, true);
        
        for(int i = 2; i <= limit; i++){
            if(table[i]){
                
                for(int j = i*2; j <= limit; j+=i)
                    table[j] = false;
                
                cnt++;
            }

        }
    System.out.println(cnt);

    scanner.close();

    }

}