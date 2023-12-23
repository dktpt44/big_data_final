import java.io.IOException;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class CleanMapper extends Mapper<LongWritable, Text, Text, Text> {

  public void map(LongWritable key, Text value, Context context)
    throws IOException, InterruptedException {
    String line = value.toString();

    String[] fields = line.split(",");
    
    // discard the header row in each file
    if (fields[0].equals("FIPS")) return; 

    // make sure all fields are present
    if(fields.length < 13) return;

    // keep data only if it from the united states
    if(!fields[3].equals("US")) return;
    
    // verify 7, 8 and 9 are numbers and verify last two are doubles
    try {
      Integer.parseInt(fields[7]);
      Integer.parseInt(fields[8]);
      Integer.parseInt(fields[9]);
      Double.parseDouble(fields[fields.length - 2]);
      Double.parseDouble(fields[fields.length - 1]);
    } catch (NumberFormatException e) {
      return;
    }

    context.write(
      new Text(fields[2]+","+fields[4]),
      new Text(fields[7] + "," + fields[8] + "," + fields[9] + "," + fields[fields.length - 2] + "," + fields[fields.length - 1])
    );
  }
}
