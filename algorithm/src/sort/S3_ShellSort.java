package sort;

import java.util.Arrays;

/**
 * 希尔排序
 * 关键在于增量序列
 * @author zbs
 * @since 2021/3/10
 */
public class S3_ShellSort {
    public static void main(String[] args) {
        int[] arr = {81,94,11,96,12,35,17,95,28,58,41,75,15};
        shellSort(arr);
        System.out.println(Arrays.toString(arr));
    }
    public static void shellSort(int[] arr){

        for(int d = arr.length/2 ; d>0; d/=2){//希尔增量序列
            for(int p = d; p<arr.length; p++){//插入排序
                int temp = arr[p];
                int i = p;
                for(; i>=d && arr[i-d]>temp; i-=d){
                    arr[i] = arr[i-d];
                }
                arr[i] = temp;
            }
        }
    }
}
