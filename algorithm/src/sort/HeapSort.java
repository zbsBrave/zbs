package sort;

import java.util.Arrays;

/**
 * 堆是一种数据结构，一种叫做完全二叉树的数据结构
 * 1，  对于大顶堆：arr[i] >= arr[2i + 1] && arr[i] >= arr[2i + 2] ，每个节点的值都大于或者等于它的左右子节点的值
 *    对于小顶堆：arr[i] <= arr[2i + 1] && arr[i] <= arr[2i + 2] ，每个节点的值都小于或者等于它的左右子节点的值
 * 2，  从上向下，从左向右：
 * 		第一个叶子节点= n/2
 * 		第一个非叶子节点 = n/2-1
 * 3，  对应节点i，其左节点=2*i+1，右节点=2*i+2
 *
 */
public class HeapSort {
    public static void main(String[] args) {
        int[] arr = {1,5,3,9,2,4,7};
        heapSort(arr);
        System.out.println(Arrays.toString(arr));
    }
    public static void heapSort(int[] arr){
        //1,首先构建大顶堆
        //从最后一个 非叶节点n/2-1 开始向前遍历，调整节点性质，使之成为大顶堆
        for(int i=arr.length/2-1; i>=0; i--) {//注意 =0 不能少
            buildHeap(arr,i,arr.length);
        }
        //2,交换堆顶和当前末尾的节点，重置大顶堆
        int len = arr.length;
        for(int i=len-1; i>0; i--){//堆顶：0  ，末尾节点：len-1
            Util.swap(arr,0,i);
            //重置大顶堆
            len--;
            buildHeap(arr,0,len);
        }
    }
    public static void buildHeap(int[] arr,int i,int len){
        //先根据堆性质，找出它左右节点的索引
        int left = 2*i + 1;
        int right = 2*i + 2;
        //默认当前节点为最大值
        int max = i;
        if(left < len && arr[left] > arr[max]) max = left;// 如果有左节点，并且左节点的值更大，更新最大值的索引
        if(right < len && arr[right] > arr[max]) max = right;
        if(max != i){//如果最大值不是当前非叶子节点的值，那么就把当前节点和最大值的子节点值互换
            Util.swap(arr,i,max);
            //因为互换之后，子节点的值变了，如果该子节点也有自己的子节点，仍需要再次调整
            buildHeap(arr,max,len);
        }
    }
}
