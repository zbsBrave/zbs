package sort;

import java.util.Arrays;

/**
 * 二分查找 又名 折半查找
 *
 * @author zbs
 * @since  2020/11/25
 */
public class BinarySearch {
    public static void main(String[] args) {
        int[] arr = {-1, 0, 1, 3, 9, 12, 15, 16, 21, 25, 38, 42, 50, 51, 59 };
        int target = 59;//要查找的目标
        int index = binarySearch(arr,target,0,arr.length-1);
        System.out.println(index);
    }
    public static int binarySearch(int[] arr,int target,int left,int right){
        while (left <= right){
            //mid=(left+right)/2，可能会导致溢出
            int mid = left + (right-left)/2;
            if(arr[mid] == target){
                return mid;
            }else if(arr[mid] < target){
                left = mid + 1;
            }else if(arr[mid] > target){
                right = mid - 1;
            }
        }

        //没有找到返回 -1
        return -1;
    }
}
