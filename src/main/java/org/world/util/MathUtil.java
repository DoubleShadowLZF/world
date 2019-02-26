package org.world.util;

import com.alibaba.fastjson.JSONObject;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 */
public class MathUtil {
	public static List<Integer> factor(Long num){
		List<Integer> result =new ArrayList<>();
		for(int i = 2; i <= (int) Math.sqrt(num); i++){
			if( num % i == 0){
				result.add(i);
				num /= i;
				i--;
			}
		}
		result.add(Integer.valueOf(String.valueOf(num)));
		return result;
	}

	public static BigInteger[] factorB(BigInteger num){
		List<BigInteger> result =new ArrayList<>();
		int i1 = num.intValue();
		for(int i = 2; i <= (int) Math.sqrt(i1); i++){
			if( i1 % i == 0){
				result.add(new BigInteger(String.valueOf(i)));
				i1 /= i;
				i--;
			}
		}
		result.add(new BigInteger(String.valueOf(i1)));
		BigInteger[] b = new BigInteger[result.size()];
		result.toArray(b);
		return b;
	}

	public static List<BigInteger> factor(BigInteger num){
		List<BigInteger> result =new ArrayList<>();

		Double sqrt = Math.sqrt(num.intValue());

		for(int i = 2; i <= (int) Math.sqrt(sqrt); i++){
			if( sqrt % i == 0){
				result.add(BigInteger.valueOf(Long.valueOf(String.valueOf(i))));
				sqrt /= i;
				i--;
			}
		}
		result.add(BigInteger.valueOf(Long.valueOf(String.valueOf(sqrt.intValue()))));

		return result;
	}

	/**
	 * 中位数
	 * @param args
	 */
	public static int median(int []nums){
		if(nums.length==0)
			return 0;
		int start=0;
		int end=nums.length-1;
		int index=partition(nums, start, end);
		if(nums.length%2==0){
			while(index!=nums.length/2-1){
				if(index>nums.length/2-1){
					index=partition(nums, start, index-1);
				}else{
					index=partition(nums, index+1, end);
				}
			}
		}else{
			while(index!=nums.length/2){
				if(index>nums.length/2){
					index=partition(nums, start, index-1);
				}else{
					index=partition(nums, index+1, end);
				}
			}
		}
		return nums[index];
	}
	private static int partition(int nums[], int start, int end){
		int left=start;
		int right=end;
		int pivot=nums[left];
		while(left<right){
			while(left<right&&nums[right]>=pivot){
				right--;
			}
			if(left<right){
				nums[left]=nums[right];
				left++;
			}
			while(left<right&&nums[left]<=pivot){
				left++;
			}
			if(left<right){
				nums[right]=nums[left];
				right--;
			}
		}
		nums[left]=pivot;
		return left;
	}

	public static void main(String[] args) {
		BigInteger bigInteger = new BigInteger(String.valueOf(90));
		BigInteger[] bigIntegers = MathUtil.factorB(bigInteger);
		System.out.println(JSONObject.toJSON(bigIntegers));
	}
}
