package Models;

import java.util.ArrayList;

/**
 * Created by thiba on 29/08/2016.
 */
public class Result<T> {
    private int total;

    public int getTotal() { return this.total; }

    public void setTotal(int total) { this.total = total; }

    private ArrayList<T> data;

    public ArrayList<T> getData() { return this.data; }

    public void setData(ArrayList<T> data) { this.data = data; }

}


