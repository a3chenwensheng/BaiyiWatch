package com.baiyi.watch.utils.convexhull;

import java.util.Comparator;

public class Point{

	// 定义点的x，y坐标，之所以是int类型，是为了日后可以在计算机屏幕上进行可视化。
	private double x;
	private double y;

	// x,y的get方法
	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	// 定义点到屏幕边缘的距离
	private static double PADDING = 20;
	// 点在屏幕中的范围
	private static double POINT_RANGE = (800 - PADDING * 2);

	// 默认构造方法，产生随机点
	public Point() {
		this.x = (double) ((Math.random() * POINT_RANGE) + PADDING);
		this.y = (double) ((Math.random() * POINT_RANGE) + PADDING);
	}

	// 带参构造方法，可以实现手动输入固定点
	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}

	// 覆写hashCode()和equals()方法，实现比较和Hash
	// @Override
	// public int hashCode() {
	// final int prime = 31;
	// int result = 1;
	// result = prime * result + x;
	// result = prime * result + y;
	// return result;
	// }

	@Override
	public boolean equals(Object obj) {
		Point other = (Point) obj;
		if ((x == other.x) && (y == other.y))
			return true;

		return false;
	}

}
