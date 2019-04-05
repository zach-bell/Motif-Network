package map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.mapreduce.Job;

public class MotifMapper {

	public static void main(String[] args) throws Exception {
		// None of this garbage has java documentation
		// reeeeeeee
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf, "MotifMapping");
		
		// I'm following this from the word count tutorial they have.
		job.setJarByClass(MotifMapper.class);
		
		
	}
}
