package sort;

import java.util.Arrays;

/**
 * 冒泡排序
 * 两两交换
 */
public class BubbleSort {
    public static void main(String[] args) {
        int[] arr = {6,1,3,2,10,13,5,7};
        bubbleSort(arr);
        System.out.println(Arrays.toString(arr));
    }
    //将 最大/小值 移动到最右边
    public static void bubbleSort(int[] arr){
        for(int i=0; i<arr.length-1; i++){//遍历的次数
            for(int j=0; j<arr.length-i-1; j++){//依次比较两个数的大小
                if(arr[j]<arr[j+1]) Util.swap(arr,j,j+1);
            }
        }
    }

    //优化：当遍历一遍都没有发生交换时，其实已经可以认为数组已经排序好了
    public static void bubbleSort1(int[] arr) {
        for(int i=0; i<arr.length-1; i++) {//遍历的次数
            boolean flag = true;
            for(int j=0; j<arr.length-1-i; j++) {//依次比较两个数的大小
                if(arr[j] < arr[j+1]) {
                    Util.swap(arr,j,j=1);
                    flag = false;//发生交换
                }
            }
            if(flag) return;//如果未发生交换，代表已经排序完，直接返回
        }
    }
}
