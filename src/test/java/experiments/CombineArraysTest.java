package experiments;

import org.apache.commons.lang3.ArrayUtils;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import java.util.LinkedList;
import java.util.List;


public class CombineArraysTest {
    /**
     * Problem:
     * There are 2 arrays of data providers.
     * - array[][] and array[]
     * - array[][] and array[][]
     * .
     * Need to combine values automatically.
     * Example:
     * [0][1][2]    [6]  |  [0][1][2]  [6][ 9]
     * [3][4][5]    [7]  |  [3][4][5]  [7][10]
     *              [8]  |             [8][11]
     *              [9]  |
     * Result should be:
     * [0][1][2][6]      |  [0][1][2][6][ 9]
     * [0][1][2][7]      |  [0][1][2][7][10]
     * [0][1][2][8]      |  [0][1][2][8][11]
     * [0][1][2][9]      |  [3][4][5][6][ 9]
     * [3][4][5][6]      |  [3][4][5][7][10]
     * [3][4][5][7]      |  [3][4][5][8][11]
     * [3][4][5][8]      |
     * [3][4][5][9]      |
     */

    @Ignore
    @Test
    public void checkArraysCombination() {
        Object[][] a1 = {{0, 1, 2}, {3, 4, 5}};
        Object[][] a2 = {{6, 9}, {7, 10}, {8, 11}};
        Object[] a3 = {6, 7, 8, 9};

        Object[][] result1 = combine(a1, a3);
        for (Object[] objects : result1) {
            for (Object object : objects)
                System.out.print(object + " ");
            System.out.println();
        }

        System.out.println();
        Object[][] result2 = combine(a1, a2);
        for (Object[] objects : result2) {
            for (Object object : objects)
                System.out.print(object + " ");
            System.out.println();
        }
    }

    private Object[][] combine(Object[][] a, Object[][] b) {
        List<Object[]> combinedArraysList = new LinkedList<>();
        for (Object[] aItem : a)
            for (Object[] bItem : b)
                combinedArraysList.add(ArrayUtils.addAll(aItem, bItem));
        return combinedArraysList.toArray(new Object[0][0]);
    }

    private Object[][] combine(Object[][] a, Object[] b) {
        List<Object[]> combinedArraysList = new LinkedList<>();
        for (Object[] aItem : a)
            for (Object bItem : b)
                combinedArraysList.add(ArrayUtils.addAll(aItem, bItem));
        return combinedArraysList.toArray(new Object[0][0]);
    }
}
