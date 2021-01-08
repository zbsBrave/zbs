package sort;

import java.util.Arrays;

/**
 * 快速排序
 * 通过一趟排序将待排记录分隔成独立的两部分，
 * 其中一部分记录的关键字均比另一部分的关键字小，则可分别对这两部分记录继续进行排序，以达到整个序列有序。
 * @author zbs
 * @since 2020/11/26
 */
public class QuickSort {
    public static void main(String[] args) {
        int[] arr = { 49, 38, 65, 97, 23, 22, 76, 1, 5, 8, 2, 0, -1, 22 };
        quickSort(arr,0,arr.length-1);
        System.out.println(Arrays.toString(arr));
    }

    private static void quickSort(int[] arr, int left, int right) {
        //todo 待修改
        if(left < right){
            int mid = findMid(arr, left, right);//找出基准值

            quickSort(arr,left,mid);
            quickSort(arr,mid+1,right);
        }
    }
    public static int findMid(int[] arr, int left , int right){
        int temp = arr[left];//基准值
        while(left < right){
            //当队尾元素大于基准值时，向前移动right
            while(left<right && arr[right] >= temp) right-- ;
            arr[left] = arr[right]; //当队尾元素小于基准值，将队尾元素赋值给队首
            while(left<right && arr[left] <= temp) left++ ;
            arr[right] = arr[left];
        }
        //跳出循环时，left=right，此时left或者right所在的位置即tmp的正确索引位置
        //left位置的值并不是tmp,所以需要将tmp赋值给arr[left]
        arr[left] = temp;
        return left;
    }


}
