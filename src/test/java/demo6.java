import org.junit.Test;

import java.util.Scanner;

public class demo6 {
    //选择排序将数组中的bag按权重排序
    public static void sort(Bag[] p) {
        Bag t;
        for (int i = 0; i < p.length; i++) {
            int max = i;
            t = p[i];
            for (int j = i; j < p.length; j++) {
                if (t.wi < p[j].wi) {
                    t = p[j];
                    max = j;
                }
            }
            t = p[i];
            p[i] = p[max];
            p[max] = t;

        }
    }

    //背包问题(贪心算法)
    public static void bq(Bag[] p, int k, int w, double v) {
        if (p[k].weight < w) {
            v = v + p[k].value;
            System.out.println(p[k].pid + "全部装入,当前背包价值为" + v);
            w = w - p[k].weight;
            bq(p, k + 1, w, v);
        } else {
            double a = w * p[k].wi;//当前价值
            v = v + a;
            System.out.println(p[k].pid + "装入了" + ((double) w / p[k].weight) + ",当前背包价值为" + v);
        }

    }

    public static void main(String args[]) {
        System.out.println("请输入背包的容量w和物品的个数n");
        Scanner reader = new Scanner(System.in);
        int w = reader.nextInt();//背包的容量
        int n = reader.nextInt();//物品的个数
        Bag[] p = new Bag[n];
        //10 10 a 10 10 b 10 15 c
        System.out.println("请依次输入各个物品的重量w和价值v和名称s");
        int weigth;
        int value;
        String pid;
        for (int i = 0; i < n; i++) {
            weigth = reader.nextInt();
            value = reader.nextInt();
            pid = reader.next();
            p[i] = new Bag(weigth, value, pid);
        }
        //System.out.println(z[1]+""+v[1]);
        sort(p);
        bq(p, 0, w, 0.0);
        for (int i = 0; i < n; i++) {
            System.out.println(p[i].wi + " " + p[i].pid);
        }

    }

    @Test
    public void test(){
        Bag[] p = new Bag[]{new Bag(20,140,"item1"),new Bag(30,180,"item2"),new Bag(40,200,"item3")};
        int w = 70;//背包容量为70
        int n = 3 ;//只有三件物品
        sort(p);
        bq(p, 0, w, 0.0);
        for (int i = 0; i < n; i++) {
            System.out.println(p[i].wi + " " + p[i].pid);
        }
    }

}

class Bag {
    public int weight;//重量
    public int value;//价值
    public double wi;//权重
    public String pid;//背包名称

    public Bag(int w, int v, String pid) {
        this.weight = w;
        this.value = v;
        this.pid = pid;
        this.wi = (double) value / weight;
    }
}
