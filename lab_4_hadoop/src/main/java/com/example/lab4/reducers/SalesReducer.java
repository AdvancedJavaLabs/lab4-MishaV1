package com.example.lab4.reducers;

import com.example.lab4.models.Sale;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Queue;

public class SalesReducer extends Reducer<Text, Text, Text, Text> {

    private Queue<Sale> result = new PriorityQueue<Sale>((a, b) -> {
        return a.sum >= b.sum ? 1 : -1;
    });

    @Override
    protected void reduce(Text key, Iterable<Text> values, Reducer<Text, Text, Text, Text>.Context context) throws IOException, InterruptedException {
        Iterator<Text> iterator = values.iterator();
        long count = 0;
        double sum = 0;
        while (iterator.hasNext()) {
            String[] value = iterator.next().toString().split(",");
            if (value.length != 2) {
                System.err.println("Invalid input data for key: " + key.toString() + " => " + String.join(",", value));
                continue;
            }

            try {
                long num = Long.parseLong(value[1]);
                count += num;

                double product = Double.parseDouble(value[0]) * num;
                sum += product;
            } catch (NumberFormatException e) {
                System.err.println("Error parsing numbers for key: " + key.toString() + " value: " + String.join(",", value));
            }

        }

        result.add(new Sale(key, sum, count));
    }

    @Override
    protected void cleanup(Reducer<Text, Text, Text, Text>.Context context) throws IOException, InterruptedException {
        while (!result.isEmpty()) {
            Sale value = result.poll();
            context.write(value.key, new Text(value.sum + " " + value.count));
        }
    }
}
