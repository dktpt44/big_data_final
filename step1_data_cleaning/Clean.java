import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class Clean {

  public static void main(String[] args) throws Exception {
    if (args.length != 2) {
      System.err.println("Usage: Program <input path> <output path>");
      System.exit(-1);
    }
    Configuration conf = new Configuration();
    conf.set("mapreduce.output.textoutputformat.separator", ",");

    Job job = Job.getInstance(conf, "Clean records");
    job.setJarByClass(Clean.class);
    job.setJobName("Clean records");

    FileInputFormat.addInputPath(job, new Path(args[0]));
    FileOutputFormat.setOutputPath(job, new Path(args[1]));

    job.setMapperClass(CleanMapper.class);
    job.setReducerClass(CleanReducer.class);

    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(Text.class);
    job.setNumReduceTasks(1);

    System.exit(job.waitForCompletion(true) ? 0 : 1);
  }
}
