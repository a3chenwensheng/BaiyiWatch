package com.baiyi.watch.utils.convexhull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class JarvisMarch {

    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    private static int MAX_ANGLE = 4;
    private double currentMinAngle = 0;
    private List<Point> hullPointList;
    private List<Integer> indexList;
    private PointFactory pf;
    private Point[] ps;  

    public Point[] getPs() {
        return ps;
    }  

    private int firstIndex;

    public int getFirstIndex() {
        return firstIndex;
    }

    public JarvisMarch() {
        this(10);
    }

    public JarvisMarch(int count) {
        pf = PointFactory.getInstance(count);
        initialize();
    }

    public JarvisMarch(double[] x, double[] y) {
        pf = PointFactory.getInstance(x, y);
        initialize();
    }

    /**
     * @author dongzl
     * @param list
     * 
     */
    public JarvisMarch(ArrayList<Point> list) {
        pf = PointFactory.getInstance(list);
        initialize();
    }

    private void initialize() {
        hullPointList = new LinkedList<Point>();
        indexList = new LinkedList<Integer>();
        firstIndex = pf.getFirstIndex();
        ps = pf.getPoints();
        addToHull(firstIndex);
    }
    
    private void addToHull(int index) {
        indexList.add(index);
        hullPointList.add(ps[index]);
    }

//    public int calculateHull() {
//        for (int i = getNextIndex(firstIndex); i != firstIndex; i = getNextIndex(i)) {
//            addToHull(i);
//        }
//        showHullPoints();
//        return 0;
//    }

    private void showHullPoints() {
        Iterator<Point> itPoint = hullPointList.iterator();
        Iterator<Integer> itIndex = indexList.iterator();
        Point p;
        int i;
        int index = 0;
        System.out.println("The hull points is: -> ");
        while (itPoint.hasNext()) {
            i = itIndex.next();
            p = itPoint.next();
            System.out.print(i + ":(" + p.getX() + ", " + p.getY() + ")  ");
            index++;
            if (index % 10 == 0)
                System.out.println();
        }
        System.out.println();
        System.out.println("****************************************************************");
        System.out.println("The count of all hull points is " + index);
    }

    public int getNextIndex(int currentIndex) {
        double minAngle = MAX_ANGLE;
        double pseudoAngle;
        int minIndex = 0;
        for (int i = 0; i < ps.length; i++) {
            if (i != currentIndex) {
                pseudoAngle = getPseudoAngle(ps[i].getX() - ps[currentIndex].getX(), 
                                             ps[i].getY() - ps[currentIndex].getY());
                if (pseudoAngle >= currentMinAngle && pseudoAngle < minAngle) {
                    minAngle = pseudoAngle;
                    minIndex = i;
                } else if (pseudoAngle == minAngle){
                        if((Math.abs(ps[i].getX() - ps[currentIndex].getX()) > 
                        Math.abs(ps[minIndex].getX() - ps[currentIndex].getX()))
                            || (Math.abs(ps[i].getY() - ps[currentIndex].getY()) > 
                            Math.abs(ps[minIndex].getY() - ps[currentIndex].getY()))){
                            minIndex = i;
                        }
                }
            }

        }
        currentMinAngle = minAngle;
        return minIndex;
    }

    public double getPseudoAngle(double dx, double dy) {
        if (dx > 0 && dy >= 0)
            return dy / (dx + dy);
        if (dx <= 0 && dy > 0)
            return 1 + (Math.abs(dx) / (Math.abs(dx) + dy));
        if (dx < 0 && dy <= 0)
            return 2 + (dy / (dx + dy));
        if (dx >= 0 && dy < 0)
            return 3 + (dx / (dx + Math.abs(dy)));
        throw new Error("Impossible");
    }

    
    
    /**
     * @author dongzl
     * @return
     */
	public ArrayList<Point> calculateHull_return_list() {
		// TODO Auto-generated method stub
        for (int i = getNextIndex(firstIndex); i != firstIndex; i = getNextIndex(i)) {
            addToHull(i);
        }
        return showHullPoints_return_list();
	}

	/**
	 * @author dongzl
	 * @return
	 */
	private ArrayList<Point> showHullPoints_return_list() {
		// TODO Auto-generated method stub
		
		ArrayList<Point> pointslist = new ArrayList<Point>();
		
        Iterator<Point> itPoint = hullPointList.iterator();
        Point p;
        while (itPoint.hasNext()) {
            p = itPoint.next();
            Point point = new Point(p.getX(), p.getY());
            pointslist.add(point);
        }
		return pointslist;
	}
    

}
