package sort;

import java.util.Arrays;

/**
 * @author zbs
 * @since 2020/12/16
 * 归并排序
 *  nLog(n) 稳定算法
 */
public class MergeSort {
    public static void main(String[] args) {
        int[] arr = {1,5,3,9,2,4,7};
        int[] temp = new int[arr.length];
        mergeSort(arr,0,arr.length-1,temp);
        System.out.println(Arrays.toString(arr));
    }

    public static void mergeSort(int[] arr,int i,int j,int[] temp){
        if(i < j){
            int mid = i + (j-i)/2;//避免溢出
            mergeSort(arr,i,mid,temp);
            mergeSort(arr,mid+1,j,temp);
            merge(arr,i,mid,j,temp);
        }
    }
    public static void merge(int[] arr,int left,int mid,int right,int[] temp){
        int t=left,i=left,j=mid+1;
        while(i<=mid && j<=right){
            if(arr[i] > arr[j]){
                temp[t++] = arr[i++];
            }else {
                temp[t++] = arr[j++];
            }
        }

        while(i<=mid){
            temp[t++] = arr[i++];
        }
        while (j<=right){
            temp[t++] = arr[j++];
        }

        t=left;
        while(left <= right){
            arr[left++] = temp[t++];
        }
    }
}
