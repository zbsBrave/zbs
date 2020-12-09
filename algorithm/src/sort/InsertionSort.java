package sort;

import java.util.Arrays;

/**
 * 插入排序
 * 原理：通过构建有序序列，对于未排序数据，在已排序序列中从后向前扫描，找到相应位置并插入。
 * 从第一个元素开始，该元素可以认为已经被排序；
 * 取出下一个元素，在已经排序的元素序列中从后向前扫描；
 * 如果该元素（已排序）大于新元素，将该元素移到下一位置；
 * 重复步骤3，直到找到已排序的元素小于或者等于新元素的位置；
 * 将新元素插入到该位置后；
 * 重复步骤2~5。
 * @author zbs
 * @since  2020/11/27
 */
public class InsertionSort {
    public static void main(String[] args) {
        int[] arr = {1,5,3,9,2,4,7};
        insertionSort(arr);
        System.out.println(Arrays.toString(arr));
    }

    private static void insertionSort(int[] arr) {
        for(int p=1; p<arr.length ; p++){
            int temp = arr[p];//摸下一张牌
            int i = p;
            for(; i>0 && arr[i-1]>temp; i--)
                arr[i] = arr[i-1];//移出空位
            arr[i] = temp;//新牌落位
        }
    }
}
