package com.baiyi.watch.utils.convexhull;

import java.util.ArrayList;

public class PointFactory {

/**
 * 单例模式，大批量产生Point，也可以手动产生Point
 */
    private Point[] points = null;
    private int newIndex;
    private int firstIndex = 0;

    public Point[] getPoints() {
        return points;
    }

    public int getFirstIndex() {
        return firstIndex;
    }

    public static PointFactory getInstance() {
        return new PointFactory();
    }
    
	public static PointFactory getInstance(int count) {
		// TODO Auto-generated method stub
        return new PointFactory(count);
	}
	
    public static PointFactory getInstance(double[] x, double[] y) {
        return new PointFactory(x, y);
    }

	
	/**
	 * 
	 * @author dongzl
	 * @param list
	 * @return
	 */
	public static PointFactory getInstance(ArrayList<Point> list) {
		// TODO Auto-generated method stub
        return new PointFactory(list);
	}
	
    private PointFactory() {
        this(10);
    }
	
    private PointFactory(int count) {
        points = new Point[count];
        for (int i = 0; i < count; i++) {
            points[i] = new Point();
            newIndex = i;
            validatePoints();
        }
        firstIndex = getFirstPoint();
    }
    
    public PointFactory(double[] x, double[] y) {
        points = new Point[y.length];
        for (int i = 0; i < y.length; i++) {
            points[i] = new Point(x[i], y[i]);
        }
        firstIndex = getFirstPoint();
    }


    /**
     * @author dongzl
     * @param list
     */
    private PointFactory(ArrayList<Point> list) {
    	int count = list.size();
        points = new Point[count];
        for (int i = 0; i < count; i++) {
            points[i] = list.get(i);
 //           newIndex = i;
 //           validatePoints();    //检查重复点。触屏产生的点似乎没有必要
        }
        firstIndex = getFirstPoint();
    }
    
    
    
    private void validatePoints() {
        for(int i = 0; i < newIndex; i++) {
                if(points[i].equals(points[newIndex])) {
                    points[newIndex] = new Point();
                    validatePoints();
                }
            }
    }


    public int getFirstPoint() {
        int minIndex = 0;
        for (int i = 1; i < points.length; i++) {
            if (points[i].getY() < points[minIndex].getY()) {
                minIndex = i;
            } else if ((points[i].getY() == points[minIndex].getY())
                    && (points[i].getX() < points[minIndex].getX())) {
                minIndex = i;
            }
        }
        return minIndex;
    }


}
