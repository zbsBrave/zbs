package sort;

import java.util.Arrays;

/**
 * 选择排序
 * 找出数组最大的元素，和最后一位交换位置
 * 不稳定算法
 * 时间复杂度
 * @author zbs
 * @since  2020/11/23
 */
public class SelectionSort {
    public static void main(String[] args) {
        int[] arr = {1,5,3,9,2,4,7};
        selectionSort(arr);
        System.out.println(Arrays.toString(arr));
    }
    public static void selectionSort(int[] arr){
        for(int i=0;i<arr.length;i++){
            int max=i;// max记录当前趟最大值的角标,初始值是最左边
            for(int j=i+1; j<arr.length; j++){
                if(arr[j] > arr[max]) max=j;
            }
            Util.swap(arr,i,max);
        }
    }
}
