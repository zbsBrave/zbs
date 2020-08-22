package sort;

import java.util.Arrays;

public class BubbleSort {
    public static void bubbleSort(int[] arr){
        for(int i=0;i<arr.length-1;i++){
            for(int j=0;j<arr.length-i-1;j++){
                if(arr[j]>arr[j+1]) Util.swap(arr,j,j+1);
            }
        }
    }

    public static void main(String[] args) {
        int[] arr = {6,1,3,2,10,13,5,7};
        bubbleSort(arr);
        System.out.println(Arrays.toString(arr));
    }
}
