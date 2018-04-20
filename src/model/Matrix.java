package model;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.function.*;

/**
 * Matrix to represent playing field filled with fractions
 * @author Tobias Fetzer 198318, Simon Stratemeier 199067
 * @version 4.0 19/04/2018
 */
public class Matrix<T> implements Serializable{
    T[][] data;


    //Konstruktor
    public Matrix(int rows, int columns, T initalFieldValue) {
        data =  (T[][])new Object[rows][columns];

        for(int i = 1; i <= data.length; i++) {
            for(int y = 1; y <= data[i-1].length; y++) {
                setValue(i, y, initalFieldValue);
            }
        }
    }
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();

        for(T[] dataX : data) {
            for (T dataY : dataX) {
                s.append(" | ")
                        .append(dataY.toString())
                        .append(" | ");
            }
            s.append("\n");
        }

        return s.toString();
    }

    public void setValue(int i, int j, T x) {
        data[i - 1][j - 1] = x;
    }

    public T getValue(int i, int j) {
        return data[i-1][j-1];
    }

    public int getHeight() {
        return Array.getLength(data);
    }

    public int getWidth() {
        return (getHeight() > 0) ? Array.getLength(data[0]) : 0;
    }


// plotter
    public Matrix<T> map(Supplier<T> func) {
        for(int x = 1; x <= getWidth(); x++) {
            for(int y = 1; y <= getHeight(); y++) {
                setValue(x, y, func.get());
            }
        }

        return this;
    }


// sum of elements
    public T reduce(BiFunction<T, T, T> func, T initalValue) {
        T accumulator = initalValue;

        for(int x = 1; x <= getWidth(); x++) {
            for(int y = 1; y <= getWidth(); y++) {
                accumulator = func.apply(accumulator, getValue(x,y));
            }
        }

        return accumulator;
    }
}