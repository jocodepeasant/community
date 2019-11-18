import java.util.Date;
import java.util.Timer;

public class dada {
    public static void main(String[] args) {
        int[] nums=new int[]{2,0,2,1,1,0};
        int i=0,j=nums.length;
        Long l=System.currentTimeMillis();
        while(i<j){
            if(nums[i]==2){
                nums[i]=nums[j-1];
                nums[j-1]=2;
                j--;
            }
            if(nums[i]==0){
                i++;
            }
        }
        Long k=System.currentTimeMillis();
        System.out.println(k-l);
    }
}
