import java.io.IOException;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class CleanReducer extends Reducer<Text, Text, Text, Text> {

  public void reduce(Text key, Iterable<Text> values, Context context)
    throws IOException, InterruptedException {
    int sum1 = 0, sum2 = 0, sum3 = 0;
    double sum4 = 0.0, sum5 = 0.0;
    int count = 0;

    for (Text value : values) {
      String[] fields = value.toString().split(",");
      sum1 += Integer.parseInt(fields[0]);
      sum2 += Integer.parseInt(fields[1]);
      sum3 += Integer.parseInt(fields[2]);

      sum4 += Double.parseDouble(fields[3]);
      sum5 += Double.parseDouble(fields[4]);
      count++;
    }
    // extract date from datetime
    String[] parts = key.toString().split(",");
    String key2 = parts[0] + "," + parts[1].split(" ")[0];
    context.write(new Text(key2), new Text(sum1 + "," + sum2 + "," + sum3 + "," + (sum4 / count) + "," + (sum5 / count)));

  }
}
