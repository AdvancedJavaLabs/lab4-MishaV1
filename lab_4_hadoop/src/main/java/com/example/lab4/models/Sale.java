package com.example.lab4.models;

import org.apache.hadoop.io.Text;

public class Sale {

    public Text key;
    public double sum;
    public long count;

    public Sale(Text key, double sum, long count) {
        this.key = key;
        this.sum = sum;
        this.count = count;
    }
}
