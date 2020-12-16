package sort;


import java.util.Arrays;

/**
 * @author zbs
 * @date 2020/11/26
 */
public class TestSort {
    public static void main(String[] args) {
        int[] arr = { 49, 38, 65, 97, 23, 22, 76, 1, 5, 8, 2, 0, -1, 22 };
        sort(arr);
        System.out.println(Arrays.toString(arr));
    }
    public static void sort(int[] arr){
        for(int p=1;p<arr.length;p++){
            int t = arr[p],i=p;
            for(; i>0 && arr[i-1]<t; i--)
                arr[i] = arr[i-1];
            arr[i] = t;
        }
    }
    public static void quickSort(int[] arr,int i,int j){
        if(i<j){
            int mid = find(arr, i, j);
            quickSort(arr,i,mid);
            quickSort(arr,mid+1,j);
        }
    }

    private static void build(int[] arr, int i, int len) {
        int left = 2*i+1,right=2*i+2;
        int min = i;
        if(left < len && arr[min]>arr[left]) min=left;
        if(right<len && arr[min]>arr[right]) min=right;
        if(min != i){
            Util.swap(arr,min,i);
            build(arr,min,len);
        }
    }

    public static int find(int[] arr,int i,int j){
        int temp = arr[i];
        while (i<j){
            while (i<j && arr[j]<=temp) j--;
            arr[i] = arr[j];
            while (i<j && arr[i]>=temp) i++;
            arr[j] = arr[i];
        }
        arr[i] = temp;
        return i;
    }
}
