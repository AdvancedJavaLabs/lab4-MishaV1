package com.example.lab4.sorter;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

public class SalesComparator extends WritableComparator {

    protected SalesComparator() {
        super(Text.class, true);
    }

    @Override
    public int compare(WritableComparable a, WritableComparable b) {
        return ((Text) a).compareTo((Text) b)   ;
    }
}
